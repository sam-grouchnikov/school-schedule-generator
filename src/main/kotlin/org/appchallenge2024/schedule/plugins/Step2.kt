package org.appchallenge2024.schedule.plugins

import data.Request
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.util.pipeline.PipelineContext
import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.hiddenInput
import kotlinx.html.link
import kotlinx.html.submitInput
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.textArea
import kotlinx.html.th
import kotlinx.html.tr
import org.appchallenge2024.schedule.sqldelight.data.Database
import kotlin.text.split

public suspend fun PipelineContext<Unit, ApplicationCall>.step2(
    database: Database
) {
    call.respondHtml {
        head {
            link(rel = "stylesheet", href = "cssSteps")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet"
            )
        }
        val school = call.parameters["school"]!!
        body(classes = "steps-background-dark steps-font poppinsfont") {
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
                        form(action = "/signInLanding", method = FormMethod.get) {
                            button(type = ButtonType.submit, classes = "lp-general-button-dark") {
                                +"Sign Out"
                            }
                        }
                    }
                }

            }
            div(classes = "steps-navigator-container3 steps-button-fontsize") {
                a(href = "/step1?school=${school}") {
                    button(classes = "steps-navigator-button") {
                        +"Previous"
                    }
                }
                div(classes = "steps-navigator-padding schoolcontainer") {
                    +school
                }
                a(href = "/step3?school=${school}") {
                    button(classes = "steps-navigator-button button-margin") {
                        +"Next"
                    }
                }
            }
            div(classes = "textbox-container-steps") {

                div(classes = "textbox-step1-3") {
                    h2(classes = "textaligncenter font20px") {
                        +"Student Requests"
                    }

                    form(action = "/step2", method = FormMethod.get, classes = "steps-form-height") {
                        textArea(classes = "steps-textarea") {
                            name = "requests"; placeholder = "Enter student requests here"
                        }
                        br
                        hiddenInput {
                            name = "school"
                            value = school
                        }
                        submitInput { value = "Submit" }
                    }

                }
                div(classes = "listContainer") {
                    val requests = call.parameters["requests"]
                    val existing = database.requestsQueries.selectAllFromSchool(school).executeAsList()
                    val compressed = convertSingleToMultipleRequests(existing)
                    val most = getMostAmountOfRequests(compressed)
                    div {
                        div(classes = "steps-table3 customcolwidth") {
                            div(classes = "steps-cell header font-25px") { +"ID" }
                            div(classes = "steps-cell header font-25px") { +"Student Name" }
                            div(classes = "steps-cell header font-25px") { +"Courses" }

                            if (requests != null) {
                                database.requestsQueries.deletAllFromSchool(school)
                            } else {
                                compressed.forEach {
                                    div(classes = "steps-cell") { +it.studentID }
                                    println(it.studentName)
                                    div(classes = "steps-cell") { +it.studentName }
                                    div(classes = "steps-cell") {
                                        (0 until most).forEach { course ->
                                            if (course < it.courses.size) {
                                                +it.courses[course]
                                                if (course != it.courses.size - 1) {
                                                    +", "
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            requests?.split("\r\n")?.forEach {
                                val info = it.split(",")
                                div(classes = "steps-cell") { +info[0] }
                                div(classes = "steps-cell") { +info[1] }
                                div(classes = "steps-cell") {
                                    for (i in 2 until info.size) {
                                        database.requestsQueries.insertRequestObject(
                                            Request(
                                                school,
                                                info[0],
                                                info[1],
                                                info[i]
                                            )
                                        )
                                        +info[i]
                                        if (i != info.size - 1) {
                                            +", "
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
            br()

            br()

        }
    }
}