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
import io.ktor.server.response.*
import org.jetbrains.annotations.Async
import org.jetbrains.annotations.Async.Schedule

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
        val school = call.parameters["school"]!!
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
                        unsafe {
                            raw(
                                "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                            )
                        }
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
                a(href = "/step2?school=${school}") {
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
                    database.coursesQueries.deleteAllFromSchool(school)
                    courses?.split("\r\n")?.forEach {
                        val info = it.split(",")
                        database.coursesQueries.insertCourseObject(Course(school, info[0], info[1], info[2]))
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
        val school = call.parameters["school"]!!
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
                            unsafe {
                                raw(
                                    "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                                )
                            }
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
                        +"0,Amanda Miller,ALG2-OL,PHY-AP1,WORLD-AP,10LIT-H"
                        br()
                        +"1,Donna Roberts,ALG2-H,PHY-AP1,WORLD-AP,10LIT-OL"
                        br()
                        +"2,Alexander Hughes,ALG2-H,PHY-OL,WORLD-OL,10LIT-H"
                        br()
                        +"3,Dennis Torres,ALG2-OL,PHY-OL,WORLD-OL,10LIT-H"
                        br()
                        +"4,Stephen Jackson,ALG2-OL,PHY-AP1,WORLD-OL,10LIT-H"
                        br()
                        +"5,Jessica Brooks,ALG2-H,PHY-OL,WORLD-AP,10LIT-H"
                        br()
                        +"6,Helen Ruiz,ALG2-OL,PHY-AP1,WORLD-OL,10LIT-OL"
                        br()
                        +"7,Debra Young,ALG2-OL,PHY-OL,WORLD-AP,10LIT-OL"
                        br()
                        +"8,Jessica Lopez,ALG2-OL,PHY-OL,WORLD-OL,10LIT-OL"
                        br()
                        +"9,Timothy Carter,ALG2-H,PHY-AP1,WORLD-AP,10LIT-H"
                        br()
                        +"10,Scott Cooper,ALG2-H,PHY-OL,WORLD-OL,10LIT-OL"
                        br()
                        +"11,Ronald Bennett,ALG2-OL,PHY-AP1,WORLD-OL,10LIT-H"
                        br()
                        +"12,Matthew Ward,ALG2-OL,PHY-OL,WORLD-AP,10LIT-OL"
                        br()
                        +"13,Emily Ruiz,ALG2-OL,PHY-OL,WORLD-AP,10LIT-H"
                        br()
                        +"14,Alexander Peterson,ALG2-H,PHY-AP1,WORLD-AP,10LIT-OL"
                    }

                }


            }
            br()
            div(classes = "steps-navigator-container steps-button-fontsize") {
                a(href = "/step1?school=${school}") {
                    button (classes = "steps-navigator-button") {
                        +"Step 1"
                    }
                }
                +" Step 2 "
                a(href = "/step3?school=${school}") {
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
                    database.requestsQueries.deletAllFromSchool(school)
                    requests?.split("\r\n")?.forEach {
                        val info = it.split(",")
                        database.requestsQueries.insertRequestObject(Request(school, info[0], info[1], info[2], info[3], info[4], info[5]))
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
        val school = call.parameters["school"]!!
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
                        unsafe {
                            raw(
                                "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                            )
                        }
                        textArea { name = "teachers"; placeholder = "Enter your message" }
                        br()
                        submitInput { value = "Submit" }
                    }
                }

            }
            br()
            div(classes = "steps-navigator-container steps-button-fontsize") {
                a(href = "/step2") {
                    unsafe {
                        raw(
                            "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                        )
                    }
                    button (classes = "steps-navigator-button") {
                        +"Step 2"
                    }
                }
                +" Step 3 "
                a(href = "/addSchedToDB?school=${school}") {
                    button(classes = "steps-navigator-button"){
                        +"Submit Schedule"
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
                    database.teachersQueries.deleteAllFromSchool(school)
                    teachers?.split("\r\n")?.forEach {
                        val info = it.split(",")
                        database.teachersQueries.insertTeacherObject(Teacher(school, info[0], info[1], info[2], info[3]))
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

public suspend fun PipelineContext<Unit, ApplicationCall>.addSchedToDB(
    database: Database
) {
//    val school = call.parameters["school"]!!
//    val courses = database.coursesQueries.selectAllFromSchool(school).executeAsList()
//    val requests = database.requestsQueries.selectAllFromSchool(school).executeAsList()
//    val teachers = database.teachersQueries.selectAllFromSchool(school).executeAsList()
//
//    val rawLists = getPossibleCombinations(courses, requests, teachers, database, school)
//    val handler = PrintCombinationHandler(requests, teachers)
//    val solution = combineLists(rawLists, requests, teachers, handler)!!
//    database.schedulesQueries.deleteAllFromSchool(school)
//    requests.forEach {
//        val schedule = getStudentScheduleFromSolution(solution, it.student_id)
//        val final = data.Schedule(school, it.student_id, schedule.c1, schedule.t1, schedule.c2, schedule.t2, schedule.c3, schedule.t3, schedule.c4, schedule.t4)
//        database.schedulesQueries.insertScheduleObject(final)
//    }
//    call.respondRedirect("/adminPage?school=${call.parameters["school"]!!}&toExpand=none")
}