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
                    h1(classes = "headercontainer") {
                        div(classes = "textaligncenter shedwizheader-dark") {
                            +"Schedwiz"
                            img(src = "http://localhost:8080/images/logo.png") {
                                this.width = "50"
                            }
                        }
                    }
                    div (classes = "lp-buttons-container") {
                        form(action = "/features", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-modeswitch-dark") {
                                +"Features"
                            }
                        }
                        div(classes = "lp-modeswitch-dark") {
                            unsafe {
                                raw("<button onclick=\"window.location.href='https://github.com/sam-hoodie/school-schedule-generator';\" classes = lp-modeswitch-dark>Source Code</button>")
                            }
                        }
                    }
                }
                div(classes = "textbox-container-lp-dark") {
                    div(classes = "textbox-lp-dark") {
                        h2(classes = "textaligncenter") {
                            +"About"
                        }
                        div(classes = "lptextbox-dark") {
                            +"Schedwiz is a schoolwide scheduling solution for school administrators."
                            +" Schedwiz uses teacher information,"
                            +" course information, and student requests to make a schedule for everybody in seconds."
                        }
                    }
                    div(classes = "textbox-lp-dark") {
                        h2(classes = "textaligncenter") {
                            +"Why?"
                        }
                        div(classes = "lptextbox-dark") {
                            +"At many schools, administrators and counselors spend hours upon hours creating schedules,"
                            +" taking away from time they could be completing all their tasks or taking a vacation. Schedwiz is a solution to this,"
                            +" allowing for hours of manual labor to be replaced by second of computer work."
                        }
                    }
                    div(classes = "textbox-lp-dark") {
                        h2(classes = "textaligncenter") {
                            +"Get Started"
                        }
                        div(classes = "lptextbox-dark") {
                            +"Creating a schedule is easy with Schedwiz, to get started click the button below!"
                            br()
                            br()
                            div (classes = "lp-getstarted-container-dark") {
                                form(action = "/signInLanding", method = FormMethod.get) {
                                    button(type = ButtonType.submit, classes = "lp-getstarted-button-dark") {
                                        +"Get Started"
                                    }
                                }
                            }
                        }
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