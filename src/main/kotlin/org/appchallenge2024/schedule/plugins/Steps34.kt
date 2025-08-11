package org.appchallenge2024.schedule.plugins

import data.Teacher
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.util.pipeline.PipelineContext
import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.input
import kotlinx.html.link
import kotlinx.html.submitInput
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.textArea
import kotlinx.html.th
import kotlinx.html.tr
import kotlinx.html.unsafe
import org.appchallenge2024.schedule.sqldelight.data.Database
import kotlin.text.split

public suspend fun PipelineContext<Unit, ApplicationCall>.step3(
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
                        form(action = "https://github.com/sam-grouchnikov/school-schedule-generator", method = FormMethod.get) {
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
                div {
                    a(href = "/step2?school=${school}") {
                        button(classes = "steps-navigator-button") {
                            +"Previous"
                        }
                    }
                }
                div(classes = "steps-navigator-padding button-margin schoolcontainer") {
                    +school
                }
                a(href = "/step4?school=${school}") {
                    button(classes = "steps-navigator-button") {
                        +"Next"
                    }
                }
            }
            div(classes = "textbox-container-steps") {
                div(classes = "textbox-step1-3") {
                    h2(classes = "textaligncenter font20px") {
                        +"Teacher Information"
                    }
                    form(action = "/step3", method = FormMethod.get, classes = "steps-form-height") {
                        unsafe {
                            raw(
                                "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                            )
                        }
                        textArea(classes = "steps-textarea") {
                            name = "teachers"; placeholder = "Enter teacher information"
                        }
                        br()
                        submitInput { value = "Submit" }
                    }
                }
                div(classes= "listContainer2") {
                    div(classes = "steps-table2") {
                        div(classes = "steps-cell header font-25px") { +"ID" }
                        div(classes = "steps-cell header font-25px") { +"Name" }
                        div(classes = "steps-cell header font-25px") { +"Room #" }
                        div(classes = "steps-cell header font-25px") { +"Capacity" }
                        div(classes = "steps-cell header font-25px") { +"Room Type" }

                        val teachers = call.parameters["teachers"]
                        if (teachers != null) {
                            database.teachersQueries.deleteAllFromSchool(school)
                        }
                        val existing = database.teachersQueries.selectAllFromSchool(school).executeAsList()
                        existing.forEach {
                            div(classes = "steps-cell") { +it.id }
                            div(classes = "steps-cell") { +it.name }
                            div(classes = "steps-cell") { +it.room }
                            div(classes = "steps-cell") { +it.room_capacity }
                            div(classes = "steps-cell") { +it.type }
                        }
                        teachers?.split("\r\n")?.forEach {
                            val info = it.split(",")
                            database.teachersQueries.insertTeacherObject(
                                Teacher(
                                    school,
                                    info[0],
                                    info[1],
                                    info[2],
                                    info[3],
                                    info[4]
                                )
                            )
                            div(classes = "steps-cell") { +info[0] }
                            div(classes = "steps-cell") { +info[1] }
                            div(classes = "steps-cell") { +info[2] }
                            div(classes = "steps-cell") { +info[3] }
                            div(classes = "steps-cell") { +info[4] }
                        }
                    }
                }
            }
            br()

            br()

        }

    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.step4(
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
                        form(action = "https://github.com/sam-grouchnikov/school-schedule-generator", method = FormMethod.get) {
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
                div {
                    a(href = "/step3?school=${school}") {
                        button(classes = "steps-navigator-button") {
                            +"Previous"
                        }
                    }
                }
                div(classes = "steps-navigator-padding schoolcontainer") {
                    +school
                }
                div(classes = "darkgrey"){
                    +"nextttt"
                }
            }
            div(classes = "textbox-container-steps2") {

                div(classes = "textbox-step1-1-center") {
                    form(action = "/addSchedToDB", method = FormMethod.get) {
                        h2(classes = "textaligncenter") {
                            +"Configure"
                        }
                        div(classes = "steps-fontsize textaligncenter") {
                            unsafe {
                                raw(
                                    "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                                )
                            }
                            +"Type: "
                            unsafe {
                                raw("<select name=\"type\">")
                                raw("<option value=\"block\">Block</option>")
                                raw("<option value=\"traditional\">Traditional</option>")
                                raw("</select>")
                            }
                            br()
                            br()
                            +" Periods (per semester): "
                            input(type = InputType.number, name = "periods", classes = "periods-input")

                        }

                        br()
                        button(type = ButtonType.submit, classes = "steps-navigator-button-purple textaligncenter") {
                            +"Create Schedule"
                        }
                    }
                }
            }

        }
    }
}