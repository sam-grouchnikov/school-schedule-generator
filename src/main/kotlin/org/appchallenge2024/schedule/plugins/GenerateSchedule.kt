package org.appchallenge2024.schedule.plugins

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import data.Course
import data.Request
import data.Teacher
import org.appchallenge2024.schedule.sqldelight.data.Database

enum class ScheduleLayout {
    BLOCK,
    TRADITIONAL
}

data class ScheduleFormatInfo(val layout: ScheduleLayout, val firstSemesPeriods: Int, val secondSemesPeriods: Int)

data class RequestCompressed(val school: String, val studentID: String, val studentName: String, val courses: List<String>)

data class Classroom(val courseID: String, val teacherID: String, var semester: Int, var period: Int, var students: ArrayList<String>)

data class HashMapData(
    val courses: HashMap<String, Course>,
    val requests: HashMap<String, RequestCompressed>,
    val teachers: HashMap<String, Teacher>
)

data class SingleRequest(val student: String, val course: String)

// converts a list of single requests into requests grouped by student

fun convertSingleToMultipleRequests(single: List<Request>): List<RequestCompressed> {
    if (single.isEmpty()) {
        return emptyList()
    }
    var uniqueIds = arrayListOf<String>()
    single.forEach {
        uniqueIds.add(it.student_id)
    }
    val result = arrayListOf<RequestCompressed>()
    uniqueIds = uniqueIds.toSet().toList() as ArrayList<String>
    // for each unique student id, match their classes from the list of single requests
    uniqueIds.forEach { student ->
        val requestsForStudent = arrayListOf<String>()
        var name = ""
        single.forEach { request ->
            if (student == request.student_id) {
                requestsForStudent.add(request.course)
                name = request.student_name
            }
        }
        result.add(RequestCompressed(single[0].school_name, student, name, requestsForStudent))
    }
    return result
}

// orders classrooms by teacher name

fun orderByTeacher(teacherIds: List<String>, classrooms: List<Classroom>): List<Classroom> {
    val result = arrayListOf<Classroom>()
    teacherIds.forEach {
        for (room in classrooms) {
            if (room.teacherID == it) {
                result.add(room)
            }
        }
    }
    return result
}

// for any given school, converts the data from the database into hash maps
// this is done for run time efficiency, as database queries take a non-trivial amount of time

fun getHashMapData(database: Database, school: String): HashMapData {
    val courses = database.coursesQueries.selectAllFromSchool(school).executeAsList()
    val coursesMap = hashMapOf<String, Course>()
    courses.forEach {
        coursesMap[it.id] = it
    }
    val requests = database.requestsQueries.selectAllFromSchool(school).executeAsList()
    val compressedRequests = convertSingleToMultipleRequests(requests)
    val requestsMap = hashMapOf<String, RequestCompressed>()
    compressedRequests.forEach {
        requestsMap[it.studentID] = it
    }
    val teachers = database.teachersQueries.selectAllFromSchool(school).executeAsList()
    val teacherMap = hashMapOf<String, Teacher>()
    teachers.forEach {
        teacherMap[it.id] = it
    }
    return HashMapData(coursesMap, requestsMap, teacherMap)
}

// recursive function thats takes in all the data and returns a completed schedule

fun generateSchedule(
    school: String,
    courses: List<Course>,
    requests: List<RequestCompressed>,
    teachers: List<Teacher>,
    currentCombination: MutableList<Classroom>,
    database: Database,
    hashMapData: HashMapData,
    formatInfo: ScheduleFormatInfo
): List<Classroom> {
    // if every request has been filled, we return the schedule
    if (isCompleteSchedule(currentCombination, requests)) {
        return currentCombination
    }
    // generates all valid next assignments
    val nextPossibleMoves: List<List<Classroom>>
    if (formatInfo.layout == ScheduleLayout.TRADITIONAL) {
        nextPossibleMoves = getAllPossibleNextAssignmentsTraditional(hashMapData, school, requests, teachers, currentCombination, database, formatInfo.firstSemesPeriods)
    } else {
        nextPossibleMoves = getAllPossibleNextAssignmentsBlock(hashMapData, school, requests, teachers, currentCombination, database, formatInfo.firstSemesPeriods, formatInfo.secondSemesPeriods)
    }
    // checks each next assignment to see if we can fill more requests
     nextPossibleMoves.forEach {
        val currentBranch = generateSchedule(school, courses, requests, teachers, it as MutableList<Classroom>, database, hashMapData, formatInfo)
        // if the generation from this point is not empty, it has reached the needed depth of the branch (complete schedule)
        if (currentBranch.isNotEmpty()) {
            return currentBranch
        }
    }
    // if no more assignments are possible, an empty list will be returned to signify this branch is unusable
    return emptyList()
}

// checks if the number of assignments is the same as the number of requests

fun isCompleteSchedule(schedule: List<Classroom>, requests: List<RequestCompressed>): Boolean {
    var assignmentCount = 0
    schedule.forEach {
        assignmentCount += it.students.size
    }
    var expectedCount = 0
    for (student in requests) {
        expectedCount += student.courses.size
    }
    return expectedCount == assignmentCount
}

fun getTypeForCourse(course: String, courses: List<Course>): String {
    courses.forEach {
        if (it.id == course) {
            return it.type
        }
    }
    return ""
}

fun getTypeForTeacher(teacher: String, teachers: List<Teacher>): String {
    teachers.forEach {
        if (it.id == teacher) {
            return it.type
        }
    }
    return ""
}

