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
                    a(href = "/", classes = "nodec") {
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
            div(classes = "bodymargin") {
                val school = call.parameters["school"]!!
                div(classes = "font32") {
                    +school
                }
                div(classes = "flex-outer-1") {
                    if (solution.isNotEmpty()) {
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
                    } else {
                        div(classes = "textbox-step1-3") {
                            div(classes = "font45") {
                                +"Welcome!"
                            }
                            div(classes = "font20") {
                                +"Click below to create a schedule"
                            }
                            br()
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

                }
                if(solution.isNotEmpty()) {
                    div(classes = "flex-outer-2") {
                        div(classes = "table-container white") {
                                val courseView = call.parameters["courseView"]
                                val studentToExpand = call.parameters["toExpand"]

                                if (courseView == null || courseView == "yes") {
                                    // Course view grid

                                    div(classes = "sp-table-grid") {
                                        // Header row (5 columns)
                                        div(classes = "sp-cell header") { +"Course Name" }
                                        div(classes = "sp-cell header") { +"Teacher Name" }
                                        div(classes = "sp-cell header") { +"Semester" }
                                        div(classes = "sp-cell header") { +"Period" }
                                        div(classes = "sp-cell header students-column") { +"Students" }

                                        // Data rows

                                        solution.forEachIndexed { i, entry ->
                                            var expandedHighlight = ""
                                            if (studentToExpand != null && studentToExpand == i.toString()) {
                                                expandedHighlight = "expandedHighlight"
                                            }
                                            div(classes = "sp-row") {
                                                div(classes = "sp-cell $expandedHighlight") {
                                                    +database.coursesQueries.selectNameForCourseID(entry.courseID, school).executeAsOne()
                                                }
                                                div(classes = "sp-cell $expandedHighlight") {
                                                    +database.teachersQueries.selectNameForID(entry.teacherID, school).executeAsOne()
                                                }
                                                div(classes = "sp-cell $expandedHighlight") { +"${entry.semester}" }
                                                div(classes = "sp-cell $expandedHighlight") { +"${entry.period}" }
                                                div(classes = "sp-cell $expandedHighlight") {
                                                    if (studentToExpand != null && studentToExpand != i.toString()) {
                                                        a(
                                                            href = "/adminPage?courseView=yes&toExpand=${i}&school=${school}",
                                                            classes = "white"
                                                        ) {
                                                            +"Expand Students"
                                                        }
                                                    } else {
                                                        a(
                                                            href = "/adminPage?courseView=yes&toExpand=none&school=${school}",
                                                            classes = "white"
                                                        ) {
                                                            +"Collapse Students"
                                                        }
                                                        br()
                                                        val names = entry.students.map { id ->
                                                            database.requestsQueries.selectNameForID(id, school).executeAsOne()
                                                        }
                                                        +names.joinToString()
                                                    }
                                                }
                                            }

                                        }
                                    }
                                } else if (courseView == "no") {
                                    // Student view grid
                                    table(classes = "sp-table") {
                                        tr(classes = "adminpage-td-th") {
                                            th(classes = "font25") { +"Student ID" }
                                            th(classes = "font25") { +"Student Name" }
                                            th(classes = "font25") { +"Courses" }
                                        }

                                        val ids = database.requestsQueries.selectAllIDsForSchool(school).executeAsList().toSet()
                                            .toList()
                                        ids.forEach {
                                            tr(classes = "adminpage-td-th") {

                                                td(classes = "bold topvertalign extrapadding") { +it }
                                                td(classes = "bold topvertalign extrapadding2") { +database.requestsQueries.selectNameForID(it, school).executeAsOne() }
                                                val schedule = getStudentScheduleFromSolution(solution, it)
                                                schedule.forEachIndexed { index, course ->
                                                    var pad = ""
                                                    if (index == 0) {
                                                        pad = "extrapadding3"
                                                    }
                                                    td(classes = pad) {
                                                        div() {
                                                            div(classes = "bold") {
                                                                +"Course: "
                                                                br()

                                                            }
                                                            div {+database.coursesQueries.selectNameForCourseID(course.course, school)
                                                                .executeAsOne()}


                                                        }
                                                        br()
                                                        div() {
                                                            div(classes = "bold") {
                                                                +"Teacher: "
                                                                br()

                                                            }
                                                            div{
                                                                +database.teachersQueries.selectNameForID(
                                                                    course.teacher,
                                                                    school
                                                                ).executeAsOne()
                                                            }

                                                        }
                                                        br()
                                                        div(classes = "flex2") {
                                                            div(classes = "bold2") {
                                                                +"Semester: "
                                                            }
                                                            +"${course.semester}"
                                                        }
                                                        br()
                                                        div(classes = "flex2") {
                                                            div(classes = "bold2") {
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
