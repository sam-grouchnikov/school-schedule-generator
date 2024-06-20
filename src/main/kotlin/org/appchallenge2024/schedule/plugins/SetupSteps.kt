package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.*
import org.appchallenge2024.schedule.sqldelight.data.Database
import data.Course
import data.Request
import data.Teacher

val submittedCourses = arrayListOf<Course>()
val submittedRequests = arrayListOf<Request>()
val submittedTeachers = arrayListOf<Teacher>()

public suspend fun PipelineContext<Unit, ApplicationCall>.step1(
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
        body(classes = "tealaquagradient steps-font") {
            div(classes = "topbar ") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader") {
                        +"Schedwiz"
                    }
                }
            }
            div(classes = "textbox-container-lp") {
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"About"
                    }
                    div {
                        +"The first step in creating a schedule is to upload course data. This includes"
                        +" the course id, course name, and course type. These values on each line must be"
                        +" separated by a comma (no spaces), and each course must be on a new line."
                        +" Any formatting errors will cause the program to fail. If you need help with uploading "
                        +"text, click "
                        a(href = "/help") {
                            +"here"
                        }
                        +"."
                    }
                }
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"Example"
                    }
                    div {
                        +"ALG2-OL,On Level Algebra 2,Math"
                        br()
                        +"ALG2-H,Honors Algebra 2,Math"
                        br()
                        +"PHY-OL,On Level Physics,Science"
                        br()
                        +"PHY-AP1,AP Physics 1,Science"
                        br()
                        +"WORLD-OL,OL World History,History"
                        br()
                        +"WORLD-AP,AP World History,History"
                        br()
                        +"10LIT-OL,On Level 10th Lit,ELA"
                        br()
                        +"10LIT-H,Honors 10th Lit,ELA"

                    }

                }
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"Copy Courses Here"
                    }
                    form(action = "/step1", method = FormMethod.get) {
                        textArea { name = "courses"; placeholder = "Enter your message" }
                        br()
                        submitInput { value = "Submit" }
                    }
                }

            }
            br()
            div(classes = "steps-navigator-container steps-button-fontsize") {
                a(href = "/") {
                    button (classes = "steps-navigator-button") {
                        +"Home"
                    }
                }
                +" Step 1 "
                a(href = "/step2") {
                    button(classes = "steps-navigator-button"){
                        +"Step 2"
                    }
                }
            }

            br()
            div {
                table(classes = "steps-table") {
                    tr(classes = "steps-td-th") {
                        th(classes = "steps-td-th") { +"Course ID" }
                        th(classes = "steps-td-th") { +"Course Name" }
                        th(classes = "steps-td-th") { +"Course Type" }
                    }

                    val courses = call.parameters["courses"]
                    submittedCourses.clear()
                    courses?.split("\r\n")?.forEach {
                        val info = it.split(",")
                        submittedCourses.add(Course(info[0], info[1], info[2]))
                        tr(classes = "steps-td-th") {
                            td(classes = "steps-td-th") { +info[0] }
                            td(classes = "steps-td-th") { +info[1] }
                            td(classes = "steps-td-th") { +info[2] }
                        }
                    }
                }
            }
        }

    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.step2(
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
        body(classes = "tealaquagradient steps-font") {
            div(classes = "topbar ") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader") {
                        +"Schedwiz"
                    }
                }
            }
            div(classes = "textbox-container-lp") {
                div(classes = "textbox-step2-1") {
                    h2(classes = "textaligncenter") {
                        +"About"
                    }
                    div {
                        +"The second step in creating a schedule is to upload student request data. This includes"
                        +" the student id, student name, and course requests (exactly 4). These values on each line must be"
                        +" separated by a comma (no spaces), and each request must be on a new line."
                        +" Any formatting errors will cause the program to fail. If you need help with uploading "
                        +"text, click "
                        a(href = "/help") {
                            +"here"
                        }
                        +"."
                    }
                    h2(classes = "textaligncenter") {
                        +"Copy Requests Here"
                    }
                    div {
                        form(action = "/step2", method = FormMethod.get) {
                            textArea { name = "requests"; placeholder = "Enter your message" }
                            br()
                            submitInput { value = "Submit" }
                        }
                    }
                }
                div(classes = "textbox-step2-1") {
                    h2(classes = "textaligncenter") {
                        +"Example"
                    }
                    div {
                        +"0,Andrew Parker,ALG2-H,PHY-AP1,WORLD-AP,10LIT-OL"
                        br()
                        +"1,Brenda Chavez,PREC-OL,CHEM-AP,WORLD-AP,10LIT-OL"
                        br()
                        +"2,Brandon Wright,ALG2-OL,CHEM-AP,WORLD-OL,10LIT-OL"
                        br()
                        +"3,Kathleen Gomez,ALG2-H,CHEM-AP,WORLD-OL,10LIT-H"
                        br()
                        +"4,Gregory Gomez,PREC-AP,CHEM-AP,WORLD-OL,10LIT-OL"
                        br()
                        +"5,Daniel Morris,PREC-AP,PHY-AP1,WORLD-OL,10LIT-H"
                        br()
                        +"6,Joseph Scott,ALG2-OL,PHY-AP1,WORLD-OL,10LIT-H"
                        br()
                        +"7,Michelle Johnson,ALG2-H,PHY-AP1,WORLD-OL,10LIT-H"
                        br()
                        +"8,Emily Richardson,ALG2-H,PHY-AP1,WORLD-OL,10LIT-H"
                        br()
                        +"9,Mark Wilson,ALG2-OL,PHY-OL,WORLD-OL,10LIT-H"
                        br()
                        +"10,Jerry Peterson,ALG2-OL,PHY-AP1,WORLD-AP,10LIT-OL"
                        br()
                        +"11,William Young,ALG2-H,PHY-AP1,WORLD-AP,10LIT-OL"
                        br()
                        +"12,Cynthia Jackson,ALG2-H,PHY-OL,WORLD-OL,10LIT-OL"
                        br()
                        +"13,Katherine Lewis,ALG2-H,PHY-OL,WORLD-AP,10LIT-OL"
                        br()
                        +"14,Janet Castillo,ALG2-OL,PHY-AP1,WORLD-AP,10LIT-OL"
                        br()
                        +"15,Jack Williams,ALG2-H,PHY-OL,WORLD-OL,10LIT-H"
                    }

                }


            }
            br()
            div(classes = "steps-navigator-container steps-button-fontsize") {
                a(href = "/step1") {
                    button (classes = "steps-navigator-button") {
                        +"Step 1"
                    }
                }
                +" Step 2 "
                a(href = "/step3") {
                    button(classes = "steps-navigator-button"){
                        +"Step 3"
                    }
                }
            }
            br()
            div {
                table(classes = "steps-table") {
                    tr(classes = "steps-td-th") {
                        th(classes = "steps-td-th") { +"Student ID" }
                        th(classes = "steps-td-th") { +"Student Name" }
                        th(classes = "steps-td-th") { +"Course 1" }
                        th(classes = "steps-td-th") { +"Course 2" }
                        th(classes = "steps-td-th") { +"Course 3" }
                        th(classes = "steps-td-th") { +"Course 4" }
                    }

                    val requests = call.parameters["requests"]
                    submittedRequests.clear()
                    requests?.split("\r\n")?.forEach {
                        val info = it.split(",")
                        submittedRequests.add(Request(info[0], info[1], info[2], info[3], info[4], info[5]))
                        tr(classes = "steps-td-th") {
                            td(classes = "steps-td-th") { +info[0] }
                            td(classes = "steps-td-th") { +info[1] }
                            td(classes = "steps-td-th") { +info[2] }
                            td(classes = "steps-td-th") { +info[3] }
                            td(classes = "steps-td-th") { +info[4] }
                            td(classes = "steps-td-th") { +info[5] }
                        }
                    }
                }
            }
        }

    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.step3(
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
        body(classes = "tealaquagradient steps-font") {
            div(classes = "topbar ") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader") {
                        +"Schedwiz"
                    }
                }
            }
            div(classes = "textbox-container-lp") {
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"About"
                    }
                    div {
                        +"The final step in creating a schedule is to upload teacher data. This includes"
                        +" the teacher id, room number, room capacity, and room type."
                        +" These values on each line must be"
                        +" separated by a comma (no spaces), and each teacher must be on a new line."
                        +" Any formatting errors will cause the program to fail. If you need help with uploading "
                        +"text, click "
                        a(href = "/help") {
                            +"here"
                        }
                        +"."
                    }
                }
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"Example"
                    }
                    div {
                        +"John Smith,101,30,Math"
                        br()
                        +"Mary Johnson,102,25,Math"
                        br()
                        +"James Williams,103,20,Math"
                        br()
                        +"John Williams,104,20,Math"
                        br()
                        +"Ron Donald,113,20,Science"
                        br()
                        +"Susan Thomas,114,35,Science"
                        br()
                        +"Charles Taylor,115,40,Science"
                        br()
                        +"Daniel Clark,123,20,History"
                        br()
                        +"Margaret Lewis,124,35,History"
                        br()
                        +"Matthew Robinson,125,40,History"
                        br()
                        +"Emily Green,132,25,ELA"
                        br()
                        +"Mark Adams,133,20,ELA"
                        br()
                        +"Amanda Baker,134,35,ELA"

                    }

                }
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"Copy Teachers Here"
                    }
                    form(action = "/step3", method = FormMethod.get) {
                        textArea { name = "teachers"; placeholder = "Enter your message" }
                        br()
                        submitInput { value = "Submit" }
                    }
                }

            }
            br()
            div(classes = "steps-navigator-container steps-button-fontsize") {
                a(href = "/step2") {
                    button (classes = "steps-navigator-button") {
                        +"Step 2"
                    }
                }
                +" Step 3 "
                a(href = "/schedulePage?courseView=yes&toExpand=none") {
                    button(classes = "steps-navigator-button"){
                        +"Schedule Page"
                    }
                }
            }
            br()
            div {
                table(classes = "steps-table") {
                    tr(classes = "steps-td-th") {
                        th(classes = "steps-td-th") { +"Teacher Name" }
                        th(classes = "steps-td-th") { +"Room Number" }
                        th(classes = "steps-td-th") { +"Room Capacity" }
                        th(classes = "steps-td-th") { +"Room Type" }
                    }

                    val teachers = call.parameters["teachers"]
                    submittedCourses.clear()
                    teachers?.split("\r\n")?.forEach {
                        val info = it.split(",")
                        submittedTeachers.add(Teacher(info[0], info[1], info[2], info[3]))
                        tr(classes = "steps-td-th") {
                            td(classes = "steps-td-th") { +info[0] }
                            td(classes = "steps-td-th") { +info[1] }
                            td(classes = "steps-td-th") { +info[2] }
                            td(classes = "steps-td-th") { +info[3] }
                        }
                    }
                }
            }
        }

    }
}

