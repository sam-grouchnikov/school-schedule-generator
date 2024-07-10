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
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader-dark") {
                        +"Schedwiz"
                        img(src = "http://localhost:8080/images/logo.png") {
                            this.width = "50"
                        }

                    }
                }
            }
            div(classes = "textbox-container-signin-dark") {
                div(classes = "textbox-signin-dark") {
                    h2(classes = "textaligncenter") {
                        +"Register School"
                    }
                    form(action = "/verifyNewSchool", method = FormMethod.get, classes = "textaligncenter") {
                        +"School Name"
                        br()
                        input(type = InputType.text, name = "school")
                        br()
                        +"Password"
                        br()
                        input(type = InputType.text, name = "psw")
                        br()
                        button(type = ButtonType.submit) {
                            +"Register"
                        }
                    }
                    div (classes = "textaligncenter") {
                        if (call.parameters["error"] == "schoolExists") {
                            +"That School Name Already Exists"
                        }
                    }
                }
                div(classes = "textbox-signin-dark") {
                    h2(classes = "textaligncenter") {
                        +"Sign In"
                    }
                    div(classes = "lptextbox textaligncenter") {
                        form(action = "/verifyCredentials", method = FormMethod.get) {
                            +"School Name"
                            br()
                            input(type = InputType.text, name = "school")
                            br()
                            +"Admin Password"
                            br()
                            input(type = InputType.text, name = "psw")
                            br()
                            unsafe {
                                raw(
                                    "<input type=\"hidden\" name=\"courseView\" value=\"yes\">"
                                )
                            }
                            button(type = ButtonType.submit) {
                                +"Login"
                            }
                        }
                    }
                    div (classes = "textaligncenter") {
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

public suspend fun PipelineContext<Unit, ApplicationCall>.addSchoolToDB(
    database: Database
) {
    val school = call.parameters["school"]!!
    val psw = call.parameters["psw"]!!
    database.schoolsQueries.insertSchoolObject(School(school, psw))
    call.respondRedirect("/adminPage?school=${school}&courseView=true")
}
