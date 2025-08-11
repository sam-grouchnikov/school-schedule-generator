package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.link
import org.appchallenge2024.schedule.sqldelight.data.Database
import kotlinx.html.*


public suspend fun PipelineContext<Unit, ApplicationCall>.about(
    database: Database
) {
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "cssAbout")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet"
            )
        }
        body(classes = "landingpage-background-dark poppinsfont") {
            div(classes = "topbar-dark") {
                h1(classes = "schedwiz-header") {
                    div() {
                        +"Schedwiz"
                    }
                }
                div(classes = "topbar-buttons") {
                    div(classes = "lp-getstarted-container-dark") {
                        form(action = "/about", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"About"
                            }
                        }
                    }
                    div(classes = "lp-getstarted-container-dark") {
                        form(action = "/guide", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"Guide"
                            }
                        }
                    }
                    div(classes = "lp-getstarted-container-dark") {
                        form(action = "https://github.com/sam-grouchnikov/school-schedule-generator", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"GitHub"
                            }
                        }
                    }
                    div {
                        form(action = "/", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"Home"
                            }
                        }
                    }
                }

            }
            div(classes = "grid") {
                div(classes = "col1font") {
                    +"About"
                }
                div(classes = "col2font") {
                    +"Schedwiz is a schoolwide scheduling solution for school administrators. Schedwiz uses teacher information, course information, and student requests to make a schedule for everybody in seconds."
                }
                div(classes = "abouticons-dark topiconnegmargin") {
                    img(src = "static/images/whiteclock.png", alt = "clock")
                }
                div(classes = "col1font") {
                    +"Why?"
                }
                div(classes = "col2font") {
                    +"At many schools, administrators and counselors spend hours upon hours creating schedules, taking away from time they could be completing all other tasks or taking a vacation. Schedwiz is a solution to this, allowing for hours of manual labor to be replaced by seconds of computer work."
                }
                div {
                    img(src = "static/images/whitelightning.png", alt = "clock")
                }
            }
        }
    }
}