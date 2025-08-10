package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.link
import org.appchallenge2024.schedule.sqldelight.data.Database
import kotlinx.html.*


public suspend fun PipelineContext<Unit, ApplicationCall>.guide(
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
            div(classes = ".gridcontainer") {
                div(classes = "grid2") {
                    div(classes = "col1font") {
                    }
                    div(classes = "col1font") {
                        +"About"
                    }
                    div(classes = "col1font") {
                        +"Example"
                    }

                    div(classes = "col1font") {
                        +"Step 1: Courses"
                    }
                    div(classes = "col2font") {
                        +"The first step in creating a schedule is to upload course data. This includes the course id, course name, and course type. These values on each line must be separated by a comma (no spaces), and each course must be on a new line. Any formatting errors will cause the program to fail."
                    }
                    div(classes = "col2font") {
                        +"ALG2-OL,On Level Algebra 2,Math\u2028ALG2-H,Honors Algebra 2,Math\u2028PHY-OL,On Level Physics,Science\u2028PHY-AP1,AP Physics 1,Science\u2028WORLD-OL,OL World History,History\u2028WORLD-AP,AP World History,History\u202810LIT-OL,On Level 10th Lit,ELA\u202810LIT-H,Honors 10th Lit,ELA"
                    }

                    div(classes = "col1font") {
                        +"Step 2: Students"
                    }
                    div(classes = "col2font") {
                        +"The second step in creating a schedule is to upload student request data. This includes the student id, student name, and course requests (exactly 4). These values on each line must be separated by a comma (no spaces), and each request must be on a new line. Any formatting errors will cause the program to fail."
                    }
                    div(classes = "col2font") {
                        +"0,Amanda,ALG2-OL,PHY-AP1,WORLD-AP,10LIT-H\u20281,Donna,ALG2-H,PHY-AP1,WORLD-AP,10LIT-OL\u20282,Alexander,ALG2-H,PHY-OL,WORLD-OL,10LIT-H\u20283,Dennis,ALG2-OL,PHY-OL,WORLD-OL,10LIT-H\u20284,Stephen,ALG2-OL,PHY-AP1,WORLD-OL,10LIT-H\u20285,Jessica,ALG2-H,PHY-OL,WORLD-AP,10LIT-H\u20286,Helen,ALG2-OL,PHY-AP1,WORLD-OL,10LIT-OL\u2028"
                    }

                    div(classes = "col1font") {
                        +"Step 3: Teachers"
                    }
                    div(classes = "col2font") {
                        +"The third step in creating a schedule is to upload teacher data. This includes the teacher id, room number, room capacity, and room type. These values on each line must be separated by a comma (no spaces), and each teacher must be on a new line. Any formatting errors will cause the program to fail."
                    }
                    div(classes = "col2font") {
                        +"1,John Smith,101,30,Math\u20282,Mary Johnson,102,25,Math\u20283,James Williams,103,20,Math\u20284,John Williams,104,20,Math\u20285,Ron Donald,113,20,Science\u20286,Susan Thomas,114,35,Science\u2028"
                    }

                    div(classes = "col1font") {
                        +"Step 4: Format"
                    }
                    div(classes = "col2font") {
                        +"The final step in creating a schedule is to select the format of your schedule. Please select whether you would like to create a block (two semesters) or a traditional (year long) schedule."
                    }
                    div(classes = "col2font") {
                        +"Seven year-long courses per student:"
                        br()
                        +"Type: Traditional, Periods: 7"
                        br()
                        br()
                        +"Four semester-long courses per student:"
                        +"Type: Block, Periods: 4"
                    }

                }
            }

        }
    }
}