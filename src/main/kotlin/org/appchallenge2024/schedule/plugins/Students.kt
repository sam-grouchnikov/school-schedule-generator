package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import kotlinx.css.TagSelector
import kotlinx.html.*
import org.appchallenge2024.schedule.sqldelight.data.Database
import data.CourseChangeRequest


public suspend fun PipelineContext<Unit, ApplicationCall>.studentPage(
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
        val school = call.parameters["school"]!!
        val id = call.parameters["id"]!!
        val schedule = database.schedulesQueries.selectScheduleForStudent(school, id).executeAsOne()
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
            div(classes = "studentpage-flex-container") {
                div(classes = "studentpage-leftbox") {
                    h2(classes = "textaligncenter") {
                        +"Schedule"
                    }
                    var i = 1
                    listOf(
                        Room(schedule.c1, schedule.t1),
                        Room(schedule.c2, schedule.t2),
                        Room(schedule.c3, schedule.t3),
                        Room(schedule.c4, schedule.t4)
                    ).forEach {
                        +"${i}: ${it.course}"
                        br()
                        +it.teacher
                        br()
                        i++
                    }
                }
                div(classes = "studentpage-rightboxes") {
                    div(classes = "studentpage-rightbox") {
                        h2(classes = "textaligncenter") {
                            +"Announcements"
                        }
                        val announcements = database.announcementsQueries.selectForSchool(school).executeAsList()
                        announcements.forEach {
                            h3 {
                                +it.date
                                +" at "
                                +it.time
                            }
                            +it.content
                        }
                    }
                    val new = call.parameters["new"]
                    val old = call.parameters["old"]
                    var special = call.parameters["special"]
                    if (special == null) {
                        special = ""
                    }
                    if (new != null && old != null) {
                        val request = CourseChangeRequest(school, id, old, new, special)
                        database.courseChangeRequestsQueries.insertRequestObject(request)
                    }
                    div(classes = "studentpage-rightbox") {
                        h2(classes = "textaligncenter") {
                            +"Request Course Change"
                        }
                        form(action = "/studentPage", method = FormMethod.get) {
                            unsafe {
                                raw("<input type=\"hidden\" name=\"school\" value=\"${school}\">")
                                raw("<br>")
                                raw("\"<input type=\"hidden\" name=\"id\" value=\"${id}\">\"\n")
                            }
                            br()
                            +"Course to Replace"
                            br()
                            input(InputType.text, name = "old")
                            br()
                            +"New Course"
                            br()
                            input(InputType.text, name ="new")
                            br()
                            +"Special Request"
                            br()
                            input(InputType.text, name="special")
                            button(type = ButtonType.submit) {
                                +"Submit Request"
                            }
                        }
                    }
                }
            }
        }
    }
}