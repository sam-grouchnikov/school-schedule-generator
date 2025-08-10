package org.appchallenge2024.schedule.plugins

import data.School
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.appchallenge2024.schedule.sqldelight.data.Database

public suspend fun PipelineContext<Unit, ApplicationCall>.signUpLanding(
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
                        form(action = "/blank", method = FormMethod.get) {
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
                div(classes = "extrapadding textbox-signin-dark textaligncenter") {
                    div(classes = "textaligncenter bigtext") {
                        +"Welcome"
                    }
                    +"Please enter a school name and password"
                    form(action = "/verifyNewSchool", method = FormMethod.get, classes = "textaligncenter extralinespacing") {
                        input(type = InputType.text, name = "school", classes = "inputbox") {
                            placeholder = "School Name"
                        }
                        br()
                        input(type = InputType.password, name = "psw", classes = "inputbox") {
                            placeholder = "Password"
                        }
                        br()
                        button(type = ButtonType.submit, classes = "signin-button") {
                            +"Sign Up"
                        }
                        br()
                        +"Already have an account? "
                        a(href = "/signInLanding") {
                            +"Sign In"
                        }
                    }
                    div (classes = "textaligncenter") {
                        if (call.parameters["error"] == "schoolExists") {
                            +"That School Name Already Exists"
                        }
                    }
                }
            }
        }
    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.verifyNewSchool(
    database: Database
) {
    val school = call.parameters["school"]!!
    val psw = call.parameters["psw"]!!
    if (database.schoolsQueries.selectAllSchools().executeAsList().contains(school)) {
        call.respondRedirect("/signInLanding?error=schoolExists")
    } else {
        call.respondRedirect("/addSchoolToDB?school=$school&psw=$psw&courseView=yes&toExpand=none")
    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.addSchoolToDB(
    database: Database
) {
    val school = call.parameters["school"]!!
    val psw = call.parameters["psw"]!!
    database.schoolsQueries.insertSchoolObject(School(school, psw))
    call.respondRedirect("/adminPage?school=${school}&courseView=true")
}
