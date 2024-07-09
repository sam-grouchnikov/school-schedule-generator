package org.appchallenge2024.schedule.plugins

import data.School
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2024.schedule.sqldelight.data.Database

public suspend fun PipelineContext<Unit, ApplicationCall>.adminPage(
    database: Database
) {
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "cssAdminPage")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet"
            )
        }
        val school = call.parameters["school"]!!
        val solution = convertStudentToCourseView(database.schedulesQueries.selectAllFromSchool(school).executeAsList())


        body(classes = "adminpage-background-dark poppinsfont") {
            div(classes = "topbar-dark yellowredpurplegradient") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader-dark") {
                        +"Schedwiz"
                    }
                }
                h1(classes = "headercontainer") {
                    +school
                }
            }
            div(classes = "flex-outer") {
                div(classes = "table-container white")
                {
                    if (solution.isEmpty()) {
                        div(classes = "whitetext") {
                            h1(classes = "textaligncenter") {
                                +"No Schedule Created"
                            }
                            form(
                                action = "/step1?school=${school}",
                                classes = "textaligncenter",
                                method = FormMethod.get
                            ) {
                                unsafe {
                                    raw(
                                        "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                                    )
                                }
                                button(type = ButtonType.submit, classes = "adminpage-button") {
                                    +"Create Schedule"
                                }
                            }
                        }
                    } else {

                        val courseView = call.parameters["courseView"]
                        val studentToExpand = call.parameters["toExpand"]
                        if (courseView == null || courseView == "yes") {
                            table(classes = "sp-table") {
                                tr(classes = "schedulepage-td-th") {
                                    th { +"Course Name" }
                                    th { +"Teacher Name" }
                                    th { +"Period" }
                                    th { +"Students (Click to expand)" }
                                }
                                for (i in solution.indices) {
                                    tr(classes = "schedulepage-td-th") {
                                        td {
                                            +database.coursesQueries.selectNameForCourseID(solution[i].courseID, school)
                                                .executeAsOne()
                                        }
                                        td { database.teachersQueries.selectNameForID(solution[i].teacherID, school).executeAsOne() }
                                        td { +"${solution[i].period}" }
                                        if (studentToExpand != null && studentToExpand != i.toString()) {
                                            td {
                                                a(href = "/adminPage?courseView=yes&toExpand=${i}&school=${school}") {
                                                    +"Expand Students"
                                                }
                                            }
                                        } else {
                                            td {
                                                solution[i].students.forEach {
                                                    +"$it"
                                                    if (i != solution.size - 1) {
                                                        +", "
                                                    }
                                                }
                                                a(href = "/adminPage?courseView=yes&toExpand=none&school=${school}") {
                                                    +"Collapse Students"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (courseView == "no") {
                            table(classes = "sp-table") {
                                tr(classes = "schedulepage-td-th") {
                                    th { +"Student ID" }
                                    th { +"Student Name" }
                                    th { +"Course 1" }
                                    th { +"Course 2" }
                                    th { +"Course 3" }
                                    th { +"Course 4" }
                                }

                                database.requestsQueries.selectAllFromSchool(school).executeAsList().forEach {
                                    val schedule = getStudentScheduleFromSolution(solution, it.student_id)
                                    tr(classes = "schedulepage-td-th") {
                                        td { +it.student_id }
                                        td { +it.student_name }
                                        td {
                                            +schedule.c1
                                            +" - "
                                            +schedule.t1
                                        }
                                        td {
                                            +schedule.c2
                                            +" - "
                                            +schedule.t2
                                        }
                                        td {
                                            +schedule.c3
                                            +" - "
                                            +schedule.t3
                                        }
                                        td {
                                            +schedule.c4
                                            +" - "
                                            +schedule.t4
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class CourseSet(
    var c1: String, var c2: String, var c3: String, var c4: String,
    var t1: String, var t2: String, var t3: String, var t4: String
)

fun getStudentScheduleFromSolution(solution: List<Classroom>, target: String): CourseSet {
    val result = CourseSet("", "", "", "", "", "", "", "")
    solution.forEach {
        if (it.students.contains(target)) {
            if (it.period == 1) {
                result.c1 = it.courseID
                result.t1 = it.teacherID
            }
            if (it.period == 2) {
                result.c2 = it.courseID
                result.t2 = it.teacherID
            }
            if (it.period == 3) {
                result.c3 = it.courseID
                result.t3 = it.teacherID
            }
            if (it.period == 4) {
                result.c4 = it.courseID
                result.t4 = it.teacherID
            }

        }
    }
    return result
}

fun convertStudentToCourseView(schedule: List<data.Schedule>): List<Classroom> {
    val result = arrayListOf<Classroom>()
    schedule.forEach {
        val student = it.student_id
        var i = 1
        val indexOfClass = findClass(result, it.course, it.teacher)
        if (indexOfClass == -1) {
            result.add(Classroom(it.course, it.teacher, 1, i, arrayListOf(student)))
        } else {
            result[indexOfClass].students.add(student)
        }
        i++

    }
    return result
}

fun findClass(classes: List<Classroom>, course: String, teacher: String): Int {
    for (i in classes.indices) {
        if (classes[i].courseID == course && classes[i].teacherID == teacher) {
            return i
        }
    }
    return -1
}

data class Room(val course: String, val teacher: String)
