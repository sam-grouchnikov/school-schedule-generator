package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.link
import org.appchallenge2024.schedule.sqldelight.data.Database
import kotlinx.html.*


public suspend fun PipelineContext<Unit, ApplicationCall>.landingPage(
    database: Database
) {
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "cssLanding")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(rel = "stylesheet", href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet")
        }
            body(classes = "landingpage-background-dark poppinsfont") {
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
                                    +"Sign In"
                                }
                            }
                        }
                    }

                }
                div(classes = "textbox-container-lp-dark") {
                    div(classes = "textbox-lp-dark") {

                        div(classes = "lptextbox-dark") {
                            div(classes = "bigtext") {
                                +"Quick Scheduling."
                                br()
                                +"Made Easy."
                            }
                            +"click below to get started"
                            div {
                                form(action = "/signInLanding", method = FormMethod.get) {
                                    button(type = ButtonType.submit, classes = "lp-signup-button-dark") {
                                        +"Sign Up"
                                    }
                                }
                            }
                        }
                    }
                    div(classes = "textbox-lp-dark") {
                        img(src = "static/images/white calendar.png", alt = "clock", classes = "iconsize")
                    }
            }
        }
    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.help() {
    call.respondHtml {
        body {
            +"Help page"
        }
    }
}