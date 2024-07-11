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
            link(rel = "stylesheet", href = "cssSteps")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet"
            )
        }
        val school = call.parameters["school"]!!
        body(classes = "steps-background-dark steps-font") {
            div(classes = "topbar-dark") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader-dark") {
                        +"Schedwiz"
                    }
                }
                h1 {
                    +school
                }
            }
            div(classes = "textbox-container-steps") {
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"About"
                    }
                    div("steps-fontsize") {
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
                div(classes = "textbox-step1-2") {
                    h2(classes = "textaligncenter") {
                        +"Example"
                    }
                    div("steps-fontsize") {
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
                div(classes = "textbox-step1-3") {
                    h2(classes = "textaligncenter") {
                        +"Course Information"
                    }
                    form(action = "/step1", method = FormMethod.get, classes = "steps-form-height") {
                        textArea(classes = "steps-textarea") { name = "courses"; placeholder = "Enter courses here" }
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
                    button(classes = "steps-navigator-button") {
                        +"Home"
                    }
                }
                div(classes = "steps-navigator-padding") {
                    +" Step 1 "
                }
                a(href = "/step2?school=${school}") {
                    button(classes = "steps-navigator-button") {
                        +"Step 2"
                    }
                }
            }

            br()
            div {
                table(classes = "steps-table") {
                    tr(classes = "steps-td-th") {
                        th(classes = "steps-td-th font-25px") { +"Course ID" }
                        th(classes = "steps-td-th font-25px") { +"Course Name" }
                        th(classes = "steps-td-th font-25px") { +"Course Type" }
                    }

                    val courses = call.parameters["courses"]
                    if (courses != null) {
                        database.coursesQueries.deleteAllFromSchool(school)
                    }
                    val existing = database.coursesQueries.selectAllFromSchool(school).executeAsList()
                    existing.forEach {
                        tr(classes = "steps-td-th") {
                            td(classes = "steps-td-th") { +it.id }
                            td(classes = "steps-td-th") { +it.name }
                            td(classes = "steps-td-th") { +it.type }
                        }
                    }
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
            link(rel = "stylesheet", href = "cssSteps")
            link(rel = "preconnect", href = "https://fonts.googleapis.com")
            link(rel = "preconnect", href = "https://fonts.gstatic.com")
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\" rel=\"stylesheet"
            )
        }
        val school = call.parameters["school"]!!
        body(classes = "steps-background-dark steps-font") {
            div(classes = "topbar-dark") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader-dark") {
                        +"Schedwiz"
                    }
                }
                h1 {
                    +school
                }
            }
            div(classes = "textbox-container-steps") {
                div(classes = "textbox-step2-1") {
                    h2(classes = "textaligncenter") {
                        +"About"
                    }
                    div("steps-fontsize") {
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
                }
                div(classes = "textbox-step2-2") {
                    h2(classes = "textaligncenter") {
                        +"Example"
                    }
                    div("steps-fontsize") {
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
                div(classes = "textbox-step2-3") {
                    h2(classes = "textaligncenter") {
                        +"Student Requests"
                    }

                    form(action = "/step2", method = FormMethod.get, classes = "steps-form-height") {
                        unsafe {
                            raw(
                                "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                            )
                        }
                        textArea(classes = "steps-textarea") { name = "requests"; placeholder = "Enter student requests here" }
                        br()
                        submitInput { value = "Submit" }
                    }

                }


            }
            br()
            div(classes = "steps-navigator-container steps-button-fontsize") {
                a(href = "/step1?school=${school}") {
                    button(classes = "steps-navigator-button") {
                        +"Step 1"
                    }
                }
                div(classes = "steps-navigator-padding") {
                    +"Step 2"
                }
                a(href = "/step3?school=${school}") {
                    button(classes = "steps-navigator-button") {
                        +"Step 3"
                    }
                }
            }
            br()
            val requests = call.parameters["requests"]
            val existing = database.requestsQueries.selectAllFromSchool(school).executeAsList()
            val compressed = convertSingleToMultipleRequests(existing)
            val most = getMostAmountOfRequests(compressed)
            div {
                table(classes = "steps-table") {
                    tr(classes = "steps-td-th") {
                        th(classes = "steps-td-th font-25px") { +"Student ID" }
                        th(classes = "steps-td-th font-25px") { +"Student Name" }
                        th(classes = "steps-td-th font-25px") { +"Courses" }
                    }

                    if (requests != null) {
                        database.requestsQueries.deletAllFromSchool(school)
                    } else {
                        compressed.forEach {
                            tr(classes = "steps-td-th") {
                                td(classes = "steps-td-th") { +it.studentID }
                                td(classes = "steps-td-th") { +it.studentName }
                                td(classes = "steps-td-th") {
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
                    }
                    requests?.split("\r\n")?.forEach {
                        val info = it.split(",")
                        tr {
                            td(classes = "steps-td-th") { +info[0] }
                            td(classes = "steps-td-th") { +info[1] }
                            td(classes = "steps-td-th") {
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
//                        tr(classes = "steps-td-th") {
//                            td(classes = "steps-td-th") { +info[0] }
//                            td(classes = "steps-td-th") { +info[1] }
//                            
//                            td(classes = "steps-td-th") { +info[2] }
//                            td(classes = "steps-td-th") { +info[3] }
//                            td(classes = "steps-td-th") { +info[4] }
//                            td(classes = "steps-td-th") { +info[5] }
//                        }
                    }
                }
            }
        }

    }
}

fun getMostAmountOfRequests(requests: List<RequestCompressed>): Int {
    if (requests.isEmpty()) {
        return 0
    }
    var longest = requests[0]
    requests.forEach {
        if (it.courses.size > longest.courses.size) {
            longest = it
        }
    }
    return longest.courses.size
}

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
        body(classes = "steps-background-dark steps-font") {
            div(classes = "topbar-dark") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader") {
                        +"Schedwiz"
                    }
                }
                h1 {
                    +school
                }
            }
            div(classes = "textbox-container-steps") {
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"About"
                    }
                    div("steps-fontsize") {
                        +"The third step in creating a schedule is to upload teacher data. This includes"
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
                    div("steps-fontsize") {
                        +"1, John Smith,101,30,Math"
                        br()
                        +"2, Mary Johnson,102,25,Math"
                        br()
                        +"3, James Williams,103,20,Math"
                        br()
                        +"4, John Williams,104,20,Math"
                        br()
                        +"5, Ron Donald,113,20,Science"
                        br()
                        +"6, Susan Thomas,114,35,Science"
                        br()
                        +"7, Charles Taylor,115,40,Science"
                        br()
                        +"8, Daniel Clark,123,20,History"
                        br()
                        +"9, Margaret Lewis,124,35,History"
                        br()
                        +"10, Matthew Robinson,125,40,History"
                        br()
                        +"11, Emily Green,132,25,ELA"
                        br()
                        +"12, Mark Adams,133,20,ELA"
                        br()
                        +"13, Amanda Baker,134,35,ELA"

                    }

                }
                div(classes = "textbox-step1-3") {
                    h2(classes = "textaligncenter") {
                        +"Teacher Information"
                    }
                    form(action = "/step3", method = FormMethod.get, classes = "steps-form-height") {
                        unsafe {
                            raw(
                                "<input type=\"hidden\" name=\"school\" value=\"${school}\">"
                            )
                        }
                        textArea(classes = "steps-textarea") { name = "teachers"; placeholder = "Enter teacher information" }
                        br()
                        submitInput { value = "Submit" }
                    }
                }

            }
            br()
            div(classes = "steps-navigator-container steps-button-fontsize") {
                div {
                    a(href = "/step2?school=${school}") {
                        button(classes = "steps-navigator-button") {
                            +"Step 2"
                        }
                    }
                }
                div(classes = "steps-navigator-padding") {
                    +"Step 3"
                }
                a(href = "/step4?school=${school}") {
                    button(classes = "steps-navigator-button") {
                        +"Step 4"
                    }
                }
            }
            br()
            div {
                table(classes = "steps-table") {
                    tr(classes = "steps-td-th") {
                        th(classes = "steps-td-th font-25px") { +"Teacher ID" }
                        th(classes = "steps-td-th font-25px") { +"Teacher Name" }
                        th(classes = "steps-td-th font-25px") { +"Room Number" }
                        th(classes = "steps-td-th font-25px") { +"Room Capacity" }
                        th(classes = "steps-td-th font-25px") { +"Room Type" }
                    }

                    val teachers = call.parameters["teachers"]
                    if (teachers != null) {
                        database.teachersQueries.deleteAllFromSchool(school)
                    }
                    val existing = database.teachersQueries.selectAllFromSchool(school).executeAsList()
                    existing.forEach {
                        tr(classes = "steps-td-th") {
                            td(classes = "steps-td-th") { +it.id }
                            td(classes = "steps-td-th") { +it.name }
                            td(classes = "steps-td-th") { +it.room }
                            td(classes = "steps-td-th") { +it.room_capacity }
                            td(classes = "steps-td-th") { +it.type }
                        }
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
                        tr(classes = "steps-td-th") {
                            td(classes = "steps-td-th") { +info[0] }
                            td(classes = "steps-td-th") { +info[1] }
                            td(classes = "steps-td-th") { +info[2] }
                            td(classes = "steps-td-th") { +info[3] }
                            td(classes = "steps-td-th") { +info[4] }
                        }
                    }
                }
            }
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
        body(classes = "steps-background-dark steps-font") {
            div(classes = "topbar-dark") {
                h1(classes = "headercontainer") {
                    div(classes = "textaligncenter shedwizheader") {
                        +"Schedwiz"
                    }
                }
                h1 {
                    +school
                }
            }
            div(classes = "textbox-container-steps") {
                div(classes = "textbox-step1-1") {
                    h2(classes = "textaligncenter") {
                        +"About"
                    }
                    div("steps-fontsize") {
                        +"The final step in creating a schedule is to select the format of your schedule. "
                        +"Please select whether you would like to create a block (two semesters) or a traditional "
                        +"(year long) schedule."
                    }
                }
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
                        button(type = ButtonType.submit, classes = "steps-navigator-button textaligncenter") {
                            +"Create Schedule"
                        }
                    }
                }
            }
            div(classes = "steps-navigator-container steps-button-fontsize") {
                div {
                    a(href = "/step3?school=${school}") {
                        button(classes = "steps-navigator-button") {
                            +"Step 3"
                        }
                    }
                }
                div(classes = "steps-navigator-padding") {
                    +"Step 4"
                }
            }
        }
    }
}

public suspend fun PipelineContext<Unit, ApplicationCall>.addSchedToDB(
    database: Database
) {
    val school = call.parameters["school"]!!
    val courses = database.coursesQueries.selectAllFromSchool(school).executeAsList()
    val requests = database.requestsQueries.selectAllFromSchool(school).executeAsList()
    val teachers = database.teachersQueries.selectAllFromSchool(school).executeAsList()
    val map = getHashMapData(database, school)
    val typeInput = call.parameters["type"]!!
    val periods = call.parameters["periods"]!!
    val info = ScheduleFormatInfo(
        layout = when (typeInput) {
            "block" -> ScheduleLayout.BLOCK
            else -> ScheduleLayout.TRADITIONAL
        },
        firstSemesPeriods = periods.toInt(),
        secondSemesPeriods = periods.toInt()
    )
    val schedule = generateSchedule(
        school,
        courses,
        convertSingleToMultipleRequests(requests),
        teachers,
        mutableListOf(),
        database,
        map,
        info
    )
    database.schedulesQueries.deleteAllFromSchool(school)
    schedule.forEach { classroom ->
        classroom.students.forEach { student ->
            database.schedulesQueries.insertScheduleObject(
                data.Schedule(
                    school,
                    student,
                    classroom.courseID,
                    classroom.teacherID,
                    classroom.period.toString(),
                    classroom.semester.toString()
                )
            )
        }
    }
    call.respondRedirect("/adminPage?school=${call.parameters["school"]!!}&toExpand=none&courseView=yes")
}