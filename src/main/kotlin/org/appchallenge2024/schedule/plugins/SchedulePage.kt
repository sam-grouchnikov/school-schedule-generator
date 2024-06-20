package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2024.schedule.sqldelight.data.Database

public suspend fun PipelineContext<Unit, ApplicationCall>.schedulePage(
    database: Database
) {
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "styles.css")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet"
            )
        }

        val solution = arrayListOf(
            Classroom("ALG2-H", "John Smith", 1, listOf("0", "3")),
            Classroom("PHY-AP1", "Richard Anderson", 2, listOf("0", "5")),
            Classroom("WORLD-AP", "Daniel Clark", 3, listOf("0", "1")),
            Classroom("10LIT-OL", "Emily Green", 4, listOf("0", "1", "2", "4")),
            Classroom("PREC-OL", "Mary Johnson", 1, listOf("1")),
            Classroom("CHEM-AP", "Susan Thomas", 2, listOf("1", "2", "3", "4")),
            Classroom("ALG2-OL", "James Williams", 1, listOf("2")),
            Classroom("WORLD-OL", "Margaret Lewis", 3, listOf("2", "3", "4", "5", "5", "5", "5", "5", "5", "5", "5")),
            Classroom("10LIT-H", "Mark Adams", 4, listOf("3", "5")),
            Classroom("PREC-AP", "John Williams", 1, listOf("4", "5"))
        )

        body(classes = "tealaquagradient poppinsfont") {
            div(classes = "topbar yellowredpurplegradient") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader") {
                        +"Schedwiz"
                        img(src = "http://localhost:8080/images/logo.png") {
                            this.width = "50"
                        }
                    }
                }
            }
            div(classes = "sp-navigator-container steps-button-fontsize") {
                a(href = "/step3") {
                    button(classes = "steps-navigator-button") {
                        +"Step 3"
                    }
                }
                +" Schedule Page "
                if (call.parameters["courseView"] == "yes") {
                    a(href = "/schedulePage?courseView=no&toExpand=none") {
                        button(classes = "steps-navigator-button") {
                            +"Student View"
                        }
                    }
                } else {
                    a(href = "/schedulePage?courseView=yes&toExpand=none") {
                        button(classes = "steps-navigator-button") {
                            +"Course View"
                        }
                    }
                }
            }
            div(classes = "textbox-container-sp") {
                val courseView = call.parameters["courseView"]!!
                val studentToExpand = call.parameters["toExpand"]!!
                if (courseView == "yes") {
                    table(classes = "sp-table") {
                        tr(classes = "schedulepage-td-th") {
                            th { +"Course Name" }
                            th { +"Teacher Name" }
                            th { +"Period" }
                            th { +"Student (Click to expand)" }
                        }
                        for (i in solution.indices) {
                            tr(classes = "schedulepage-td-th") {
                                td {
                                    +database.coursesQueries.selectNameForCourseID(solution[i].courseID).executeAsOne()
                                }
                                td { +solution[i].teacherName }
                                td { +"${solution[i].period}" }
                                if (studentToExpand != i.toString()) {
                                    td {
                                        a(href = "/schedulePage?courseView=${courseView}&toExpand=${i}") {
                                            +"Expand Students"
                                        }
                                    }
                                } else {
                                    td {
                                        solution[i].students.forEach {
                                            +"$it "
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    table(classes = "sp-table") {
                        tr(classes = "schedulepage-td-th") {
                            th { +"Student ID" }
                            th { +"Student Name" }
                            th { +"Course 1" }
                            th { +"Course 2" }
                            th { +"Course 3" }
                            th { +"Course 4" }
                        }

                        getAllRequests(database).forEach {
                            val schedule = getStudentScheduleFromSolution(solution, it.id)
                            tr(classes = "schedulepage-td-th") {
                                td { +it.id }
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

data class Classroom2(val courseID: String, val teacherName: String, var period: Int, val students: List<String>)

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
                result.t1 = it.teacherName
            }
            if (it.period == 2) {
                result.c2 = it.courseID
                result.t2 = it.teacherName
            }
            if (it.period == 3) {
                result.c3 = it.courseID
                result.t3 = it.teacherName
            }
            if (it.period == 4) {
                result.c4 = it.courseID
                result.t4 = it.teacherName
            }

        }
    }
    return result
}
