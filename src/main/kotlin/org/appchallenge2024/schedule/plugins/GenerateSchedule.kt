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
    val dataMap = getHashMapData(database, "1")

    val coursesManual = listOf(
        Course("1", "ALG2-OL", "OL Alg 2", "Math"),
        Course("1", "ALG2-H", "H Alg 2", "Math"),
        Course("1", "PHY-OL", "OL Phy", "Science"),
        Course("1", "PHY-AP", "Ap phy", "Science"),
        Course("1", "WORLD-OL", "OL World", "History"),
        Course("1", "WORLD-AP", "Ap world", "History"),
        Course("1", "10LIT-OL", "OL Lit", "ELA"),
        Course("1", "10LIT-H", "H Lit", "ELA"),
    )
    val coursesMap = hashMapOf<String, Course>()
    coursesManual.forEach {
        coursesMap[it.id] = it
    }

    val requestsManual = listOf(
        Request("1", "0", "Student 0", "ALG2-H", "PHY-OL", "WORLD-AP", "10LIT-OL"),
        Request("1", "1", "Student 1", "ALG2-OL", "PHY-OL", "WORLD-OL", "10LIT-OL"),
        Request("1", "2", "Student 2", "ALG2-H", "PHY-AP", "WORLD-AP", "10LIT-H"),
        Request("1", "3", "Student 3", "ALG2-OL", "PHY-AP", "WORLD-AP", "10LIT-H"),
        Request("1", "4", "Student 4", "ALG2-OL", "PHY-AP", "WORLD-AP", "10LIT-OL"),
        Request("1", "5", "Student 5", "ALG2-H", "PHY-AP", "WORLD-AP", "10LIT-H"),
        Request("1", "6", "Student 6", "ALG2-H", "PHY-AP", "WORLD-OL", "10LIT-H"),
        Request("1", "7", "Student 7", "ALG2-OL", "PHY-OL", "WORLD-OL", "10LIT-H"),
        Request("1", "8", "Student 8", "ALG2-H", "PHY-OL", "WORLD-OL", "10LIT-OL"),
        Request("1", "9", "Student 9", "ALG2-OL", "PHY-AP", "WORLD-OL", "10LIT-H"),

        )
    val requestsMap = hashMapOf<String, Request>()
    requestsManual.forEach {
        requestsMap[it.student_id] = it
    }

    val teachersManual = listOf(
        Teacher("1", "0", "Robert (Math)", "100", "3", "Math"),
        Teacher("1", "1", "John (Science)", "101", "4", "Science"),
        Teacher("1", "2", "James (History)", "102", "3", "History"),
        Teacher("1", "3", "Justin (ELA)", "103", "4", "ELA"),
        )
    val teachersMap = hashMapOf<String, Teacher>()
    teachersManual.forEach {
        teachersMap[it.id] = it
    }
    val manualDBMap = HashMapData(coursesMap, requestsMap, teachersMap)

    val schedule = generateSchedule("1", coursesManual, requestsManual, teachersManual, mutableListOf(), database, manualDBMap)
    val ordered = orderByTeacher(listOf("0", "1", "2", "3"), schedule)
    for (classroom in ordered) {
        println(classroom)
    }
    println(isValidSchedule(ordered, coursesManual, requestsManual, teachersManual))
}

fun orderByTeacher(teacherIds: List<String>, classrooms: List<Classroom>): List<Classroom> {
    val result = arrayListOf<Classroom>()
    teacherIds.forEach {
        for(room in classrooms) {
            if (room.teacherID == it) {
                result.add(room)
            }
        }
    }
    return result
}

data class Classroom(val courseID: String, val teacherID: String, var period: Int, var students: ArrayList<String>)


data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)


// used to track time between branch generations and number of generations
var lastTimestamp = System.nanoTime()
val firstTimestamp = System.nanoTime()
val lastXBranches = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
var count = 0

fun Double.roundToTwo() : Double {
    return String.format("%.2f", this).toDouble()
}

data class HashMapData(val courses: HashMap<String, Course>, val requests: HashMap<String, Request>, val teachers: HashMap<String, Teacher>)

fun getHashMapData(database: Database, school: String): HashMapData {
    val courses = database.coursesQueries.selectAllFromSchool(school).executeAsList()
    val coursesMap = hashMapOf<String, Course>()
    courses.forEach {
        coursesMap[it.id] = it
    }
    val requests = database.requestsQueries.selectAllFromSchool(school).executeAsList()
    val requestsMap = hashMapOf<String, Request>()
    requests.forEach {
        requestsMap[it.student_id] = it
    }
    val teachers = database.teachersQueries.selectAllFromSchool(school).executeAsList()
    val teacherMap = hashMapOf<String, Teacher>()
    teachers.forEach {
        teacherMap[it.id] = it
    }
    return HashMapData(coursesMap, requestsMap, teacherMap)
}

fun generateSchedule(school: String, courses: List<Course>, requests: List<Request>, teachers: List<Teacher>, currentCombination: MutableList<Classroom>, database: Database, hashMapData: HashMapData): List<Classroom> {
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
    count ++
    // generates all valid next assignments
    val nextPossibleMoves = getAllPossibleNextAssignments(hashMapData, school, requests, teachers, currentCombination, database)
    nextPossibleMoves.forEach {

        val currentBranch = generateSchedule(school, courses, requests, teachers, it as MutableList<Classroom>, database, hashMapData)
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

// save database call results in a hash map
// deep copy loops (is it possible to do without copying the entire list)

// the nested loops are not the problem, it is the constant calling of the database

// get next possible for student, not for all

fun getAllPossibleNextAssignments(hashMapData: HashMapData, school: String, requests: List<Request>, teachers: List<Teacher>, currentCombination: List<Classroom>, database: Database): List<List<Classroom>> {
    val result = arrayListOf<List<Classroom>>()
    // check if we can create a new change for every single request
    val remainingRequests = getRemainingRequests(currentCombination, requests)
    for (request in remainingRequests) {
            // check if we can add the student to an existing class
            val time0 = System.nanoTime()
            for (i in currentCombination.indices) {
//                val roomCapacity = database.teachersQueries.selectCapacityForTeacher(currentCombination[i].teacherID, school).executeAsOne()
                val time3 = System.nanoTime()
                val capacityEfficient = hashMapData.teachers[currentCombination[i].teacherID]!!.room_capacity
                val time4 = System.nanoTime()
                val periodsAssignedForStudent = getPeriodsForStudent(currentCombination, request.student)
                val time5 = System.nanoTime()
                if (request.course == currentCombination[i].courseID && currentCombination[i].students.size < capacityEfficient.toInt() &&
                    !periodsAssignedForStudent.contains(currentCombination[i].period) && !alreadyAssigned(request.student, currentCombination, request.course)
                ) {
                    val temp = arrayListOf<Classroom>()
                    for (classroom in currentCombination) {
                        val studentsCopy = arrayListOf<String>()
                        studentsCopy.addAll(classroom.students)
                        val classroomCopy = Classroom(classroom.courseID, classroom.teacherID, classroom.period, studentsCopy)
                        temp.add(classroomCopy)
                    }
                    temp[i].students.add(request.student)
                    result.add(temp)

                }
//                println("1: " + (time4 - time3))
//                println("2: " + (time5 - time4))
            }
            val time1 = System.nanoTime()

            // check if we can create a new class with the student
            for (period in 1..4) {
                val time0 = System.currentTimeMillis()

                val availableTeachersForClass = getAvailableTeachers(hashMapData, school, currentCombination, teachers, request.course, period, database)
                val time1 = System.currentTimeMillis()
                for (teacher in availableTeachersForClass) {
                    if (!alreadyAssigned(request.student, currentCombination, request.course)) {
                        val temp = arrayListOf<Classroom>()
                        for (classroom in currentCombination) {
                            val studentsCopy = arrayListOf<String>()
                            studentsCopy.addAll(classroom.students)
                            val classroomCopy = Classroom(classroom.courseID, classroom.teacherID, classroom.period, studentsCopy)
                            temp.add(classroomCopy)
                        }
                        temp.add(Classroom(request.course, teacher.id, period, arrayListOf(request.student)))
                        if (!alreadyAssigned(request.student, currentCombination, request.course) &&
                            !alreadyAssignedToPeriod(request.student, currentCombination, period)
                        )
                        result.add(temp)
                    }
                }
                val time2 = System.currentTimeMillis()

            }
        val time2 = System.nanoTime()
//        println("Part 1: " + (time1 - time0) / 1000000.0)
//        println("Part 2: " + (time2 - time1) / 1000000.0)
    }
    return result
}

data class SingleRequest(val student: String, val course: String)

fun getRemainingRequests(currentCombination: List<Classroom>, requests: List<Request>): List<SingleRequest> {
    for (request in requests) {
        val requestsForStudent = arrayListOf<SingleRequest>()
        outer@ for (single in listOf(request.course1, request.course2, request.course3, request.course4)) {
            for (current in currentCombination) {
                if (current.courseID == single && current.students.contains(request.student_id)) {
                    continue@outer
                }
            }
            requestsForStudent.add(SingleRequest(request.student_id, single))
        }
        if (requestsForStudent.isNotEmpty()) {
            return requestsForStudent
        }
    }
    return emptyList()
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

fun getAvailableTeachers(hashMapData: HashMapData, school: String, classrooms: List<Classroom>, teachers: List<Teacher>, course: String, period: Int, database: Database): List<Teacher> {
    val time0 = System.currentTimeMillis()
//    val type = database.coursesQueries.selectTypeForCourse(course, school).executeAsOne()
    val typeEfficient = hashMapData.courses[course]!!.type
    val time1 = System.currentTimeMillis()
    val result = arrayListOf<Teacher>()
//    val teachersForType = database.teachersQueries.selectTeachersForType(typeEfficient, school).executeAsList()
    val teachersForTypeEfficient = getTeachersForType(hashMapData.teachers, typeEfficient)
    val time2 = System.currentTimeMillis()
    for (teacher in teachersForTypeEfficient) {
        val occupied = isOccupiedForPeriod(teacher.id, classrooms, period)
        if (!occupied) {
            result.add(teacher)
        }
    }
    val time3 = System.currentTimeMillis()
//    println("       P1: ${((time1 - time0))}")
//    println("       P2: ${((time2 - time1))}")
//    println("       P3: ${((time3 - time2))}")
    return result
}

fun getTeachersForType(teachers: HashMap<String, Teacher>, type: String): List<Teacher> {
    val result = arrayListOf<Teacher>()
    for ((key, value) in teachers) {
        if (value.type == type) {
            result.add(value)
        }
    }
    return result
}

fun isOccupiedForPeriod(teacher: String, classrooms: List<Classroom>, period: Int): Boolean {
    val result = arrayListOf<Int>()
    classrooms.forEach {
        if (it.teacherID == teacher && period == it.period) {
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

fun isValidSchedule(classrooms: List<Classroom>, courses: List<Course>, requests: List<Request>, teachers: List<Teacher>): Boolean {
    // all students have their requests filled
    for (student in requests) {
        for (request in listOf(student.course1, student.course2, student.course3, student.course4)) {
            var found = false
            classrooms.forEach {
                if (it.courseID == request && it.students.contains(student.student_id)) {
                    found = true
                }
            }
            if (!found) {
                return false
            }
        }
    }
    // all students have no overlapping periods
    for (student in requests) {
        val periods = arrayListOf<Int>()
        classrooms.forEach {
            if (it.students.contains(student.student_id)) {
                periods.add(it.period)
            }
        }
        if (periods.size != periods.toSet().size) {
            return false
        }
    }
    // teachers do not have overlapping periods
    for (teacher in teachers) {
        val periods = arrayListOf<Int>()
        classrooms.forEach {
            if (it.teacherID == teacher.id) {
                periods.add(it.period)
            }
        }
        if (periods.size != periods.toSet().size) {
            return false
        }
    }
    // teachers are mapped to their correct subjects
    classrooms.forEach {
        val courseType = getTypeForCourse(it.courseID, courses)
        val teacherType = getTypeForTeacher(it.teacherID, teachers)
        if (courseType != teacherType) {
            return false
        }
    }
    return true
}

fun getTypeForCourse(course: String, courses: List<Course>): String {
    courses.forEach {
        if(it.id == course) {
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

