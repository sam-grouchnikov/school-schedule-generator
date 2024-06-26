package org.appchallenge2024.schedule.plugins

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import data.Course
import data.Request
import data.Teacher
import org.appchallenge2024.schedule.sqldelight.data.Database


fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
    Database.Schema.create(driver)

    val database = Database(driver)

    val courses = database.coursesQueries.selectAllFromSchool("1").executeAsList()
    val requests = database.requestsQueries.selectAllFromSchool("1").executeAsList()
    val teachers = database.teachersQueries.selectAllFromSchool("1").executeAsList()
    val schedule = generateSchedule("1", courses, requests, teachers, mutableListOf(), database)
    for (classroom in schedule) {
        println(classroom)
    }
}

data class Classroom(val courseID: String, val teacherName: String, var period: Int, var students: ArrayList<String>)


data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)


// used to track time between branch generations and number of generations
var lastTimestamp = System.currentTimeMillis()
val firstTimestamp = System.currentTimeMillis()
val lastXBranches = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
var count = 0

fun Double.roundToTwo() : Double {
    return String.format("%.2f", this).toDouble()
}

fun generateSchedule(school: String, courses: List<Course>, requests: List<Request>, teachers: List<Teacher>, currentCombination: MutableList<Classroom>, database: Database): List<Classroom> {
    if (isCompleteSchedule(currentCombination, requests)) {
        return currentCombination
    }
    // creates timestamps
    val timeSinceLast = ((System.currentTimeMillis() - lastTimestamp) / 1000.0).roundToTwo()
    val timeSinceStart = ((System.currentTimeMillis() - firstTimestamp) / 1000.0).roundToTwo()
    lastXBranches.removeAt(0)
    lastXBranches.add(timeSinceLast)
    // average time between last x amount of generations
    val average = lastXBranches.average().roundToTwo()
    print(" Entry $count: ")
    print(printNumOfStudent(currentCombination))
    println(" Time Elapsed: $timeSinceLast (average $average seconds). Total time elapsed: $timeSinceStart seconds")
    lastTimestamp = System.currentTimeMillis()
    count ++
    // generates all valid next assignments
    val nextPossibleMoves = getAllPossibleNextAssignments(school, requests, teachers, currentCombination, database)
    nextPossibleMoves.forEach {

        val currentBranch = generateSchedule(school, courses, requests, teachers, it as MutableList<Classroom>, database)
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

fun isCompleteSchedule(schedule: List<Classroom>, requests: List<Request>): Boolean {
    var assignmentCount = 0
    schedule.forEach {
        assignmentCount += it.students.size
    }
    return requests.size * 4 == assignmentCount
}

// generates all possible valid next assignments

fun getAllPossibleNextAssignments(school: String, requests: List<Request>, teachers: List<Teacher>, currentCombination: List<Classroom>, database: Database): List<List<Classroom>> {
    val result = arrayListOf<List<Classroom>>()
    // check if we can create a new change for every single request
    for (request in requests) {
        for (course in listOf(request.course1, request.course2, request.course3, request.course4)) {
            // check if we can add the student to an existing class
            for (i in currentCombination.indices) {
                val roomCapacity = database.teachersQueries.selectCapacityForTeacher(currentCombination[i].teacherName, school).executeAsOne()
                val periodsAssignedForStudent = getPeriodsForStudent(currentCombination, request.student_id)
                if (course == currentCombination[i].courseID && currentCombination[i].students.size < roomCapacity.toInt() &&
                    !periodsAssignedForStudent.contains(currentCombination[i].period) && !alreadyAssigned(request.student_id, currentCombination, course)
                ) {
                    val temp = arrayListOf<Classroom>()
                    for (classroom in currentCombination) {
                        val studentsCopy = arrayListOf<String>()
                        studentsCopy.addAll(classroom.students)
                        val classroomCopy = Classroom(classroom.courseID, classroom.teacherName, classroom.period, studentsCopy)
                        temp.add(classroomCopy)
                    }
                    temp[i].students.add(request.student_id)
                    result.add(temp)
                }
            }
            // check if we can create a new class with the student
            for (period in 1..4) {
                val availableTeachersForClass = getAvailableTeachers(school, currentCombination, teachers, course, period, database)
                for (teacher in availableTeachersForClass) {
                    if (!alreadyAssigned(request.student_id, currentCombination, course)) {
                        val temp = arrayListOf<Classroom>()
                        for (classroom in currentCombination) {
                            val studentsCopy = arrayListOf<String>()
                            studentsCopy.addAll(classroom.students)
                            val classroomCopy = Classroom(classroom.courseID, classroom.teacherName, classroom.period, studentsCopy)
                            temp.add(classroomCopy)
                        }
                        temp.add(Classroom(course, teacher.name, period, arrayListOf(request.student_id)))
                        if (!alreadyAssigned(request.student_id, currentCombination, course) &&
                            !alreadyAssignedToPeriod(request.student_id, currentCombination, period)
                        )
                        result.add(temp)
                    }
                }
            }
        }
    }
    return result
}

fun alreadyAssignedToPeriod(student: String, classrooms: List<Classroom>, period: Int): Boolean {
    classrooms.forEach {
        if (it.students.contains(student) && it.period == period) {
            return true
        }
    }
    return false
}

fun alreadyAssigned(student: String, classrooms: List<Classroom>, course: String): Boolean {
    classrooms.forEach {
        if (it.courseID == course && it.students.contains(student)) {
            return true
        }
    }
    return false
}

fun getAvailableTeachers(school: String, classrooms: List<Classroom>, teachers: List<Teacher>, course: String, period: Int, database: Database): List<Teacher> {
    val type = database.coursesQueries.selectTypeForCourse(course, school).executeAsOne()
    val result = arrayListOf<Teacher>()
    val teachersForType = database.teachersQueries.selectTeachersForType(type, school).executeAsList()
    for (teacher in teachersForType) {
        val occupied = isOccupiedForPeriod(teacher.name, classrooms, period)
        if (!occupied) {
            result.add(teacher)
        }
    }
    return result
}

fun isOccupiedForPeriod(teacher: String, classrooms: List<Classroom>, period: Int): Boolean {
    val result = arrayListOf<Int>()
    classrooms.forEach {
        if (it.teacherName == teacher && period == it.period) {
            return true
        }
    }
    return false
}

fun getPeriodsForStudent(classrooms: List<Classroom>, target: String): List<Int> {
    val result = arrayListOf<Int>()
    classrooms.forEach {
        if (it.students.contains(target)) {
            result.add(it.period)
        }
    }
    return result
}



