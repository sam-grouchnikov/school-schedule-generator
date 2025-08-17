package org.appchallenge2024.schedule.plugins

import data.School
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2024.schedule.sqldelight.data.Database

public suspend fun PipelineContext<Unit, ApplicationCall>.signInLanding(
    database: Database
) {
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "cssSignIn")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet"
            )
        }

        body(classes = "signin-background-dark poppinsfont") {
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
                        form(action = "/", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"Home"
                            }
                        }
                    }
                }

            }
            div(classes = "textbox-container-signin-dark") {
                div(classes = "textbox-signin-dark") {

                    div(classes = "textaligncenter") {
                        div(classes = "textaligncenter bigtext") {
                            +"Welcome Back"
                        }
                        +"Please enter your school name and password"
                        br()
                        form(action = "/verifyCredentials", method = FormMethod.get, classes = "extralinespacing") {
                            input(type = InputType.text, name = "school", classes = "inputbox") {
                                placeholder = "School Name"
                            }
                            br()
                            input(type = InputType.password, name = "psw", classes = "inputbox") {
                                placeholder = "Password"
                            }
                            unsafe {
                                raw(
                                    "<input type=\"hidden\" name=\"courseView\" value=\"yes\">"
                                )
                            }
                            br()
                            button(type = ButtonType.submit, classes = "signin-button") {
                                +"Login"
                            }
                            br()
                            +"Don't have an account? "
                            a(href = "/signUpLanding") {
                                +"Sign Up"
                            }
                        }
                    }
                    div (classes = "textaligncenter red") {
                        if (call.parameters["error"] == "wrongPsw") {
                            +"Incorrect Password"
                        }
                        if (call.parameters["error"] == "invalidSchool") {
                            +"That School Does Not Exist"
                        }
                    }
                }
            }
        }
    }
}


public suspend fun PipelineContext<Unit, ApplicationCall>.verifyCredentials(
    database: Database
) {
    var error: String? = null
    val school = call.parameters["school"]!!
    val psw = call.parameters["psw"]!!
    val allSchools = database.schoolsQueries.selectAllSchools().executeAsList() as List<*>
    if (!allSchools.contains(school)) {
        call.respondRedirect("/signInLanding?error=invalidSchool")
    } else {
        val actualPsw = database.schoolsQueries.selectPSWForSchool(school).executeAsOne()
        if (psw != actualPsw) {
            call.respondRedirect("/signInLanding?error=wrongPsw")
        }
    }
    call.respondRedirect("/adminPage?school=$school&courseView=yes&toExpand=none")
}

