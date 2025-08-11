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
import data.School
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
                div(classes = "darkgrey"){
                    +"darkgrey"
                }
                div(classes = "steps-navigator-padding schoolcontainer") {
                    +school
                }
                a(href = "/step2?school=${school}") {
                    button(classes = "steps-navigator-button") {
                        +"Next"
                    }
                }
            }
            div(classes = "textbox-container-steps") {
                div(classes = "textbox-step1-3") {
                    div(classes = "textaligncenter font20px") {
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
                div(classes = "listContainer paddingleft") {
                    div(classes = "steps-table3") {
                        div(classes = "steps-cell header font-25px") { +"Course ID" }
                        div(classes = "steps-cell header font-25px") { +"Course Name" }
                        div(classes = "steps-cell header font-25px") { +"Course Type" }

                        val courses = call.parameters["courses"]
                        if (courses != null) {
                            database.coursesQueries.deleteAllFromSchool(school)
                        }
                        val existing = database.coursesQueries.selectAllFromSchool(school).executeAsList()
                        existing.forEach {
                            div(classes = "steps-cell") { +it.id }
                            div(classes = "steps-cell") { +it.name }
                            div(classes = "steps-cell") { +it.type }
                        }
                        courses?.split("\r\n")?.forEach {
                            val info = it.split(",")
                            database.coursesQueries.insertCourseObject(Course(school, info[0], info[1], info[2]))
                            div(classes = "steps-cell") { +info[0] }
                            div(classes = "steps-cell") { +info[1] }
                            div(classes = "steps-cell") { +info[2] }
                        }

                    }
                }
            }
            br()


            br()

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

public suspend fun PipelineContext<Unit, ApplicationCall>.addRequestsToDB(
    database: Database
) {
    val requests = call.parameters["requests"]!!
    val school = call.parameters["school"]!!
    database.requestsQueries.deletAllFromSchool(school)
    requests?.split("\r\n")?.forEach { request ->
        val info = request.split(",")
        info.subList(2, info.size).forEach {
            database.requestsQueries.insertRequestObject(Request(school, info[0], info[1], it))
        }
    }
    call.respondRedirect("/step2?school=$school")
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