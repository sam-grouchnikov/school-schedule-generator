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
            link(rel = "stylesheet", href = "styles.css")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet"
            )
        }

        body(classes = "tealaquagradient poppinsfont") {
            div(classes = "topbar") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader") {
                        +"Schedwiz"
                        img(src = "http://localhost:8080/images/logo.png") {
                            this.width = "50"
                        }

                    }
                }
            }
            div(classes = "textbox-container-lp") {
                div(classes = "textbox-lp") {
                    h2(classes = "textaligncenter") {
                        +"Admins"
                    }
                    div(classes = "lptextbox textaligncenter") {
                        h3(classes = "textaligncenter") {
                            +"Sign In"
                        }
                        form(action = "/adminpage", method = FormMethod.get) {
                            +"School Name"
                            br()
                            input(type = InputType.text, name = "name")
                            br()
                            +"Admin Password"
                            br()
                            input(type = InputType.text, name = "psw")
                            br()
                            button(type = ButtonType.submit) {
                                +"Login"
                            }
                            input(type = InputType.text, name = "new")

                        }
                        h3(classes = "textaligncenter") {
                            +"Register School"
                        }
                        form(action = "/addSchoolToDB", method = FormMethod.get) {
                            +"School Name"
                            br()
                            input(type = InputType.text, name = "name")
                            br()
                            +"Password"
                            br()
                            input(type = InputType.text, name = "psw")
                            br()
                            button(type = ButtonType.submit) {
                                +"Register"
                            }
                        }
                    }
                }
            }
        }
    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.addSchoolToDB(
    database: Database
) {
    val parameters = call.parameters
//    parameters["name"]?.let { parameters["psw"]?.let { it1 -> School(it, it1) } }
//        ?.let { database.schoolsQueries.insertSchoolObject(it) }
    call.respondRedirect(url = "/adminpage?name=${parameters["name"]}&psw=${parameters["psw"]}", permanent = false)

}