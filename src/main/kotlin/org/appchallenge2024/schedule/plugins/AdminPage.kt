package org.appchallenge2024.schedule.plugins

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import data.School
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2024.schedule.sqldelight.data.Database

fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:schedule.db")
    Database.Schema.create(driver)
    val database = Database(driver)
    database.requestsQueries.deleteAll()
    database.coursesQueries.deleteAll()
    database.teachersQueries.deleteAll()
    database.schedulesQueries.deleteAll()
}

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
        val view = call.parameters["courseView"]!!


        body(classes = "adminpage-background-dark poppinsfont") {
            div(classes = "topbar-dark") {
                h1(classes = "schedwiz-header") {
                    div() {
                        +"Schedwiz"
                    }
                }
                div (classes = "topbar-buttons") {
                    div (classes = "lp-getstarted-container-dark") {
                        form(action = "/about", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"About"
                            }
                        }
                    }
                    div (classes = "lp-getstarted-container-dark") {
                        form(action = "/guide", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"Guide"
                            }
                        }
                    }
                    div (classes = "lp-getstarted-container-dark") {
                        form(action = "https://github.com/sam-grouchnikov/school-schedule-generator", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"GitHub"
                            }
                        }
                    }
                    div {
                        form(action = "/signInLanding", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"Sign Out"
                            }
                        }
                    }
                }

            }
            val school = call.parameters["school"]!!
            div(classes = "font32") {
                +school
            }
            div(classes = "flex-outer-1") {

                div(classes = "table-container white") {
                    form(action = "/adminPage", classes = "textaligncenter", method = FormMethod.get) {
                        unsafe {
                            raw(
                                "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                            )
                        }
                        if (call.parameters["courseView"]!! == "yes") {
                            unsafe {
                                raw(
                                    "<input type=\"hidden\" name=\"courseView\" value=\"no\">"
                                )
                            }
                        } else {
                            unsafe {
                                raw(
                                    "<input type=\"hidden\" name=\"courseView\" value=\"yes\">"
                                )
                            }
                        }
                        unsafe {
                            raw(
                                "<input type=\"hidden\" name=\"toExpand\" value=\"none\">"
                            )
                        }
                        button(type = ButtonType.submit, classes = "adminpage-button") {
                            if (view == "yes") {
                                +"Sort By Student"
                            } else {
                                +"Sort By Teacher"
                            }
                        }
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
                        button(type = ButtonType.submit, classes = "adminpage-button2") {
                            +"Create New Schedule"
                        }
                    }
                }
            }
            div(classes = "flex-outer-2") {
                div(classes = "table-container white")
                {
                    if (solution.isEmpty()) {
                        div(classes = "whitetext") {
                            div(classes = "textaligncenter font25") {
                                +"No schedule created"
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
                                    th { +"Semester" }
                                    th { +"Period" }
                                    th(classes = "students-column") { +"Students (Click to expand)" }
                                }
                                for (i in solution.indices) {
                                    tr(classes = "schedulepage-td-th") {
                                        td {
                                            +database.coursesQueries.selectNameForCourseID(solution[i].courseID, school)
                                                .executeAsOne()
                                        }
                                        td {
                                            +database.teachersQueries.selectNameForID(solution[i].teacherID, school)
                                                .executeAsOne()
                                        }
                                        td { +"${solution[i].semester}" }
                                        td { +"${solution[i].period}" }
                                        if (studentToExpand != null && studentToExpand != i.toString()) {
                                            td {
                                                a(
                                                    href = "/adminPage?courseView=yes&toExpand=${i}&school=${school}",
                                                    classes = "white"
                                                ) {
                                                    +"Expand Students"
                                                }
                                            }
                                        } else {

                                            td {
                                                a(
                                                    href = "/adminPage?courseView=yes&toExpand=none&school=${school}",
                                                    classes = "white"
                                                ) {
                                                    +"Collapse Students"
                                                }
                                                br()
//                                                +solution[i].students.joinToString()
                                                val names = arrayListOf<String>()
                                                solution[i].students.forEach { id ->
                                                    names.add(
                                                        database.requestsQueries.selectNameForID(id, school)
                                                            .executeAsOne()
                                                    )
                                                }
                                                +names.joinToString()
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (courseView == "no") {
                            table(classes = "sp-table") {
                                tr(classes = "adminpage-td-th") {
                                    th { +"Student ID" }
                                    th { +"Student Name" }
                                    th { +"Courses" }
                                }

                                val ids = database.requestsQueries.selectAllIDsForSchool(school).executeAsList().toSet()
                                    .toList()
                                ids.forEach {
                                    tr(classes = "adminpage-td-th") {

                                        td(classes = "bold") { +it }
                                        td(classes = "bold") { +database.requestsQueries.selectNameForID(it, school).executeAsOne() }
                                        val schedule = getStudentScheduleFromSolution(solution, it)
                                        schedule.forEach { course ->

                                            td {
                                                div(classes = "flex2") {
                                                    div(classes = "bold") {
                                                        +"Class: "
                                                    }
                                                    +" "
                                                    +database.coursesQueries.selectNameForCourseID(course.course, school)
                                                        .executeAsOne()
                                                }
                                                br()
                                                div(classes = "flex2") {
                                                    div(classes = "bold") {
                                                        +"Teacher: "
                                                    }

                                                    +database.teachersQueries.selectNameForID(
                                                        course.teacher,
                                                        school
                                                    ).executeAsOne()
                                                }
                                                br()
                                                div(classes = "flex2") {
                                                    div(classes = "bold") {
                                                        +"Semester: "
                                                    }
                                                    +"${course.semester}"
                                                }
                                                br()
                                                div(classes = "flex2") {
                                                    div(classes = "bold") {
                                                        +"Period: "
                                                    }
                                                    +"${course.period}"
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
    }
}

data class CourseSet(
    var c1: String, var c2: String, var c3: String, var c4: String,
    var t1: String, var t2: String, var t3: String, var t4: String
)

data class ClassSet(
    var course: String, var teacher: String, val semester: Int, val period: Int
)

fun getStudentScheduleFromSolution(solution: List<Classroom>, target: String): List<ClassSet> {
    val result = arrayListOf<ClassSet>()
    solution.forEach {
        if (it.students.contains(target)) {
            result.add(ClassSet(it.courseID, it.teacherID, it.semester, it.period))
        }
    }
    return result.sortedBy { it.semester }.sortedBy { it.period }
}


fun convertStudentToCourseView(schedule: List<data.Schedule>): List<Classroom> {
    val result = arrayListOf<Classroom>()
    schedule.forEach {
        val student = it.student_id
        var i = 1
        val indexOfClass = findClass(result, it.course, it.teacher)
        if (indexOfClass == -1) {
            result.add(Classroom(it.course, it.teacher, it.semester.toInt(), it.period.toInt(), arrayListOf(student)))
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
