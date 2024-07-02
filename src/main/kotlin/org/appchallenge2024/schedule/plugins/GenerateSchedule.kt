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

fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
    Database.Schema.create(driver)

    val database = Database(driver)

//    val courses = database.coursesQueries.selectAllFromSchool("1").executeAsList()
//    val requests = database.requestsQueries.selectAllFromSchool("1").executeAsList()
//    val teachers = database.teachersQueries.selectAllFromSchool("1").executeAsList()
//    val dataMap = getHashMapData(database, "1")

    val coursesManual = listOf(
        Course("1", "ALG2-OL", "OL Alg 2", "Math"),
        Course("1", "ALG2-H", "H Alg 2", "Math"),
        Course("1", "PHY-OL", "OL Phy", "Science"),
        Course("1", "PHY-AP", "Ap phy", "Science"),
        Course("1", "WORLD-OL", "OL World", "History"),
        Course("1", "WORLD-AP", "Ap world", "History"),
        Course("1", "10LIT-OL", "OL Lit", "ELA"),
        Course("1", "10LIT-H", "H Lit", "ELA"),
        Course("1", "BIO-OL", "OL Bio", "Science"),
        Course("1", "BIO-AP", "AP Bio", "Science"),
        Course("1", "CALC-AB", "Calc AB", "Math"),
        Course("1", "CALC-BC", "Calc BC", "Math"),
        Course("1", "USH-AP", "APUSH", "History"),
        Course("1", "USH-OL", "OL USH", "History"),
        Course("1", "11LIT-OL", "OL Lit", "ELA"),
        Course("1", "11LIT-H", "H Lit", "ELA"),
    )
    val coursesMap = hashMapOf<String, Course>()
    coursesManual.forEach {
        coursesMap[it.id] = it
    }

    val requestsManual = listOf(
        Request("1", "0", "Student 0", "ALG2-H"),
        Request("1", "0", "Student 0", "PHY-OL"),
        Request("1", "0", "Student 0", "WORLD-AP"),
        Request("1", "0", "Student 0", "10LIT-OL"),
        Request("1", "0", "Student 0", "CALC-AB"),
        Request("1", "0", "Student 0", "BIO-OL"),
        Request("1", "0", "Student 0", "USH-AP"),
        Request("1", "0", "Student 0", "11LIT-OL"),
        Request("1", "1", "Student 1", "ALG2-OL"),
        Request("1", "1", "Student 1", "PHY-OL"),
        Request("1", "1", "Student 1", "WORLD-OL"),
        Request("1", "1", "Student 1", "10LIT-OL"),
        Request("1", "1", "Student 1", "CALC-BC"),
        Request("1", "1", "Student 1", "BIO-OL"),
        Request("1", "1", "Student 1", "USH-OL"),
        Request("1", "1", "Student 1", "11LIT-H"),
//        Request("1", "2", "Student 2", "ALG2-H"),
//        Request("1", "2", "Student 2", "PHY-AP"),
//        Request("1", "2", "Student 2", "WORLD-AP"),
//        Request("1", "2", "Student 2", "10LIT-H"),
//        Request("1", "3", "Student 3", "ALG2-OL"),
//        Request("1", "3", "Student 3", "PHY-AP"),
//        Request("1", "3", "Student 3", "WORLD-AP"),
//        Request("1", "3", "Student 3", "10LIT-H"),
//        Request("1", "4", "Student 4", "ALG2-OL"),
//        Request("1", "4", "Student 4", "PHY-AP"),
//        Request("1", "4", "Student 4", "WORLD-AP"),
//        Request("1", "4", "Student 4", "10LIT-OL"),
//        Request("1", "5", "Student 5", "ALG2-H"),
//        Request("1", "5", "Student 5", "PHY-AP"),
//        Request("1", "5", "Student 5", "WORLD-AP"),
//        Request("1", "5", "Student 5", "10LIT-H"),
//        Request("1", "6", "Student 6", "ALG2-H"),
//        Request("1", "6", "Student 6", "PHY-AP"),
//        Request("1", "6", "Student 6", "WORLD-OL"),
//        Request("1", "6", "Student 6", "10LIT-H"),
//        Request("1", "7", "Student 7", "ALG2-OL"),
//        Request("1", "7", "Student 7", "PHY-OL"),
//        Request("1", "7", "Student 7", "WORLD-OL"),
//        Request("1", "7", "Student 7", "10LIT-H"),
//        Request("1", "8", "Student 8", "ALG2-H"),
//        Request("1", "8", "Student 8", "PHY-OL"),
//        Request("1", "8", "Student 8", "WORLD-OL"),
//        Request("1", "8", "Student 8", "10LIT-OL"),
//        Request("1", "9", "Student 9", "ALG2-OL"),
//        Request("1", "9", "Student 9", "PHY-AP"),
//        Request("1", "9", "Student 9", "WORLD-OL"),
//        Request("1", "9", "Student 9", "10LIT-H"),

        )
    val compressedRequests = convertSingleToMultipleRequests(requestsManual)
    val requestsMap = hashMapOf<String, RequestCompressed>()
    compressedRequests.forEach {
        requestsMap[it.studentID] = it
    }

    val teachersManual = listOf(
        Teacher("1", "0", "Robert (Math)", "100", "5", "Math"),
        Teacher("1", "1", "John (Science)", "101", "4", "Science"),
        Teacher("1", "2", "James (History)", "102", "5", "History"),
        Teacher("1", "3", "Justin (ELA)", "103", "4", "ELA"),
    )
    val teachersMap = hashMapOf<String, Teacher>()
    teachersManual.forEach {
        teachersMap[it.id] = it
    }
    val manualDBMap = HashMapData(coursesMap, requestsMap, teachersMap)
    val formatInfo = ScheduleFormatInfo(ScheduleLayout.BLOCK, 4, 4)
    val schedule =
        generateSchedule("1", coursesManual, compressedRequests, teachersManual, mutableListOf(), database, manualDBMap, formatInfo)
    val ordered = orderByTeacher(listOf("0", "1", "2", "3"), schedule)
    for (classroom in ordered) {
        println(classroom)
    }
    println(isValidScheduleTraditional(ordered, coursesManual, compressedRequests, teachersManual))
}

data class RequestCompressed(val school: String, val studentID: String, val studentName: String, val courses: List<String>)

fun convertSingleToMultipleRequests(single: List<Request>): List<RequestCompressed> {
    var uniqueIds = arrayListOf<String>()
    single.forEach {
        uniqueIds.add(it.student_id)
    }
    val result = arrayListOf<RequestCompressed>()
    uniqueIds = uniqueIds.toSet().toList() as ArrayList<String>
    uniqueIds.forEach { student ->
        val requestsForStudent = arrayListOf<String>()
        var name = ""
        single.forEach { request ->
            if (student == request.student_id) {
                requestsForStudent.add(request.course)
                name = request.school_name
            }
        }
        result.add(RequestCompressed(single[0].school_name, student, name, requestsForStudent))
    }
    return result
}

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

data class Classroom(val courseID: String, val teacherID: String, var semester: Int, var period: Int, var students: ArrayList<String>)


data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)


// used to track time between branch generations and number of generations
var lastTimestamp = System.nanoTime()
val firstTimestamp = System.nanoTime()
val lastXBranches = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
var count = 0

fun Double.roundToTwo(): Double {
    return String.format("%.2f", this).toDouble()
}

data class HashMapData(
    val courses: HashMap<String, Course>,
    val requests: HashMap<String, RequestCompressed>,
    val teachers: HashMap<String, Teacher>
)

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
    if (isCompleteSchedule(currentCombination, requests)) {
        return currentCombination
    }
    // creates timestamps
    val timeSinceLast = ((System.nanoTime() - lastTimestamp) / 1000000.0).roundToTwo()
    val timeSinceStart = ((System.nanoTime() - firstTimestamp) / 1000000000.0).roundToTwo()
    lastXBranches.removeAt(0)
    lastXBranches.add(timeSinceLast)
    // average time between last x amount of generations
    val average = lastXBranches.average().roundToTwo()
    print(" Entry $count: ")
    print(printNumOfStudent(currentCombination))
    println(" Time Elapsed: $timeSinceLast (average $average miliseconds). Total time elapsed: $timeSinceStart seconds")
    lastTimestamp = System.nanoTime()
    count++
    // generates all valid next assignments
    var nextPossibleMoves = emptyList<List<Classroom>>()
    if (formatInfo.layout == ScheduleLayout.TRADITIONAL) {
        nextPossibleMoves =
            getAllPossibleNextAssignmentsTraditional(hashMapData, school, requests, teachers, currentCombination, database, formatInfo.firstSemesPeriods)
    } else {
        nextPossibleMoves =
            getAllPossibleNextAssignmentsBlock(hashMapData, school, requests, teachers, currentCombination, database, formatInfo.firstSemesPeriods, formatInfo.secondSemesPeriods)
    }
     nextPossibleMoves.forEach {

        val currentBranch =
            generateSchedule(school, courses, requests, teachers, it as MutableList<Classroom>, database, hashMapData, formatInfo)
        // if the generation from this point is not empty, it has reached the needed depth of the branch
        if (currentBranch.isNotEmpty()) {
            return currentBranch
        }
    }
    // if no more moves are possible, an empty list will be returned to signify this
    return emptyList()
}

// used to track how deep the branch went

fun printNumOfStudent(classrooms: List<Classroom>): Int {
    var result = 0
    classrooms.forEach {
        result += it.students.size
    }
    return result
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

// generates all possible valid next assignments

// save database call results in a hash map
// deep copy loops (is it possible to do without copying the entire list)

// the nested loops are not the problem, it is the constant calling of the database

// get next possible for student, not for all





data class SingleRequest(val student: String, val course: String)



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

