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
    println(isValidScheduleBlock(ordered, coursesManual, compressedRequests, teachersManual))
}

fun getAllPossibleNextAssignmentsBlock(
    hashMapData: HashMapData,
    school: String,
    requests: List<RequestCompressed>,
    teachers: List<Teacher>,
    currentCombination: List<Classroom>,
    database: Database,
    periods1: Int,
    periods2: Int
): List<List<Classroom>> {
    val result = arrayListOf<List<Classroom>>()
    // check if we can create a new change for every single request
    val remainingRequests = getRemainingRequestsBlock(currentCombination, requests)
    for (request in remainingRequests) {
        // check if we can add the student to an existing class
        val time0 = System.nanoTime()
        for (i in currentCombination.indices) {
//                val roomCapacity = database.teachersQueries.selectCapacityForTeacher(currentCombination[i].teacherID, school).executeAsOne()
            val time3 = System.nanoTime()
            val capacityEfficient = hashMapData.teachers[currentCombination[i].teacherID]!!.room_capacity
            val time4 = System.nanoTime()
            val periodsAssignedForStudent = getPeriodsForStudentBlock(currentCombination, request.student)
            val time5 = System.nanoTime()
            if (request.course == currentCombination[i].courseID && currentCombination[i].students.size < capacityEfficient.toInt() &&
                !periodsAssignedForStudent.contains(BlockPeriod(currentCombination[i].semester, currentCombination[i].period)) && !alreadyAssigned(
                    request.student,
                    currentCombination,
                    request.course
                )
            ) {
                val temp = arrayListOf<Classroom>()
                for (classroom in currentCombination) {
                    val studentsCopy = arrayListOf<String>()
                    studentsCopy.addAll(classroom.students)
                    val classroomCopy =
                        Classroom(classroom.courseID, classroom.teacherID, 1, classroom.period, studentsCopy)
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
        for (period in 1..periods1) {
            val time0 = System.currentTimeMillis()

            val availableTeachersForClass = getAvailableTeachersBlock(
                hashMapData,
                school,
                currentCombination,
                teachers,
                request.course,
                BlockPeriod(1, period),
                database
            )
            val time1 = System.currentTimeMillis()
            for (teacher in availableTeachersForClass) {
                if (!alreadyAssigned(request.student, currentCombination, request.course)) {
                    val temp = arrayListOf<Classroom>()
                    for (classroom in currentCombination) {
                        val studentsCopy = arrayListOf<String>()
                        studentsCopy.addAll(classroom.students)
                        val classroomCopy =
                            Classroom(classroom.courseID, classroom.teacherID, classroom.semester, classroom.period, studentsCopy)
                        temp.add(classroomCopy)
                    }
                    temp.add(Classroom(request.course, teacher.id, 1, period, arrayListOf(request.student)))
                    if (!alreadyAssigned(request.student, currentCombination, request.course) &&
                        !alreadyAssignedToPeriodBlock(request.student, currentCombination, period, 1)
                    )
                        result.add(temp)
                }
            }
            val time2 = System.currentTimeMillis()

        }
        for (period in 1..periods2) {
            val time0 = System.currentTimeMillis()

            val availableTeachersForClass = getAvailableTeachersBlock(
                hashMapData,
                school,
                currentCombination,
                teachers,
                request.course,
                BlockPeriod(2, period),
                database
            )
            val time1 = System.currentTimeMillis()
            for (teacher in availableTeachersForClass) {
                if (!alreadyAssigned(request.student, currentCombination, request.course)) {
                    val temp = arrayListOf<Classroom>()
                    for (classroom in currentCombination) {
                        val studentsCopy = arrayListOf<String>()
                        studentsCopy.addAll(classroom.students)
                        val classroomCopy =
                            Classroom(classroom.courseID, classroom.teacherID, classroom.semester, classroom.period, studentsCopy)
                        temp.add(classroomCopy)
                    }
                    temp.add(Classroom(request.course, teacher.id, 2, period, arrayListOf(request.student)))
                    if (!alreadyAssigned(request.student, currentCombination, request.course) &&
                        !alreadyAssignedToPeriodBlock(request.student, currentCombination, period, 2)
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

fun getRemainingRequestsBlock(currentCombination: List<Classroom>, requests: List<RequestCompressed>): List<SingleRequest> {
    for (request in requests) {
        val requestsForStudent = arrayListOf<SingleRequest>()
        val existingRequests = request.courses
        outer@ for (single in existingRequests) {
            for (current in currentCombination) {
                if (current.courseID == single && current.students.contains(request.studentID)) {
                    continue@outer
                }
            }
            requestsForStudent.add(SingleRequest(request.studentID, single))
        }
        if (requestsForStudent.isNotEmpty()) {
            return requestsForStudent
        }
    }
    return emptyList()
}

fun alreadyAssignedToPeriodBlock(student: String, classrooms: List<Classroom>, period: Int, semester: Int): Boolean {
    classrooms.forEach {
        if (it.students.contains(student) && it.period == period && semester == it.semester) {
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

fun getAvailableTeachersBlock(
    hashMapData: HashMapData,
    school: String,
    classrooms: List<Classroom>,
    teachers: List<Teacher>,
    course: String,
    period: BlockPeriod,
    database: Database
): List<Teacher> {
    val time0 = System.currentTimeMillis()
//    val type = database.coursesQueries.selectTypeForCourse(course, school).executeAsOne()
    val typeEfficient = hashMapData.courses[course]!!.type
    val time1 = System.currentTimeMillis()
    val result = arrayListOf<Teacher>()
//    val teachersForType = database.teachersQueries.selectTeachersForType(typeEfficient, school).executeAsList()
    val teachersForTypeEfficient = getTeachersForTypeBlock(hashMapData.teachers, typeEfficient)
    val time2 = System.currentTimeMillis()
    for (teacher in teachersForTypeEfficient) {
        val occupied = isOccupiedForPeriodBlock(teacher.id, classrooms, period.period, period.semester)
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

fun getTeachersForTypeBlock(teachers: HashMap<String, Teacher>, type: String): List<Teacher> {
    val result = arrayListOf<Teacher>()
    for ((key, value) in teachers) {
        if (value.type == type) {
            result.add(value)
        }
    }
    return result
}

fun isOccupiedForPeriodBlock(teacher: String, classrooms: List<Classroom>, period: Int, semester: Int): Boolean {
    val result = arrayListOf<Int>()
    classrooms.forEach {
        if (it.teacherID == teacher && period == it.period && semester == it.semester) {
            return true
        }
    }
    return false
}

data class BlockPeriod(val semester: Int, val period: Int)

fun getPeriodsForStudentBlock(classrooms: List<Classroom>, target: String): List<BlockPeriod> {
    val result = arrayListOf<BlockPeriod>()
    classrooms.forEach {
        if (it.students.contains(target)) {
            result.add(BlockPeriod(it.semester, it.period))
        }
    }
    return result
}

fun isValidScheduleBlock(
    classrooms: List<Classroom>,
    courses: List<Course>,
    requests: List<RequestCompressed>,
    teachers: List<Teacher>
): Boolean {
    // all students have their requests filled
    for (student in requests) {
        val existingRequests = student.courses
        for (request in existingRequests) {
            var found = false
            classrooms.forEach {
                if (it.courseID == request && it.students.contains(student.studentID)) {
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
        val periods = arrayListOf<BlockPeriod>()
        classrooms.forEach {
            if (it.students.contains(student.studentID)) {
                periods.add(BlockPeriod(it.semester, it.period))
            }
        }
        if (periods.size != periods.toSet().size) {
            return false
        }
    }
    // teachers do not have overlapping periods
    for (teacher in teachers) {
        val periods = arrayListOf<BlockPeriod>()
        classrooms.forEach {
            if (it.teacherID == teacher.id) {
                periods.add(BlockPeriod(it.semester, it.period))
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