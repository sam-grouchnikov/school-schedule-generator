package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2024.schedule.sqldelight.data.Database

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
                    listOf(Room(schedule.c1, schedule.t1), Room(schedule.c2, schedule.t2), Room(schedule.c3, schedule.t3), Room(schedule.c4, schedule.t4)).forEach {
                        +"${i}: ${it.course}"
                        br()
                        +"${it.teacher}"
                        br()
                        i++
                    }
                }
                div(classes = "studentpage-rightboxes") {
                    div(classes = "studentpage-rightbox") {
                        h2(classes = "textaligncenter") {
                            +"Announcements"
                        }
                    }
                    div(classes = "studentpage-rightbox") {
                        h2(classes = "textaligncenter") {
                            +"Request Course Change"
                        }
                    }
                }
            }
        }
    }
}