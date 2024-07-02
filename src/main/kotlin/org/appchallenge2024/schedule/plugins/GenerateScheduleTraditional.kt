package org.appchallenge2024.schedule.plugins

import data.Course
import data.Teacher
import org.appchallenge2024.schedule.sqldelight.data.Database

fun getAllPossibleNextAssignmentsTraditional(
    hashMapData: HashMapData,
    school: String,
    requests: List<RequestCompressed>,
    teachers: List<Teacher>,
    currentCombination: List<Classroom>,
    database: Database,
    periods: Int
): List<List<Classroom>> {
    val result = arrayListOf<List<Classroom>>()
    // check if we can create a new change for every single request
    val remainingRequests = getRemainingRequestsTraditional(currentCombination, requests)
    for (request in remainingRequests) {
        // check if we can add the student to an existing class
        val time0 = System.nanoTime()
        for (i in currentCombination.indices) {
//                val roomCapacity = database.teachersQueries.selectCapacityForTeacher(currentCombination[i].teacherID, school).executeAsOne()
            val time3 = System.nanoTime()
            val capacityEfficient = hashMapData.teachers[currentCombination[i].teacherID]!!.room_capacity
            val time4 = System.nanoTime()
            val periodsAssignedForStudent = getPeriodsForStudentTraditional(currentCombination, request.student)
            val time5 = System.nanoTime()
            if (request.course == currentCombination[i].courseID && currentCombination[i].students.size < capacityEfficient.toInt() &&
                !periodsAssignedForStudent.contains(currentCombination[i].period) && !alreadyAssigned(
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
        for (period in 1..periods) {
            val time0 = System.currentTimeMillis()

            val availableTeachersForClass = getAvailableTeachersTraditional(
                hashMapData,
                school,
                currentCombination,
                teachers,
                request.course,
                period,
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
                            Classroom(classroom.courseID, classroom.teacherID, 1, classroom.period, studentsCopy)
                        temp.add(classroomCopy)
                    }
                    temp.add(Classroom(request.course, teacher.id, 1, period, arrayListOf(request.student)))
                    if (!alreadyAssigned(request.student, currentCombination, request.course) &&
                        !alreadyAssignedToPeriodTraditional(request.student, currentCombination, period)
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

fun getRemainingRequestsTraditional(currentCombination: List<Classroom>, requests: List<RequestCompressed>): List<SingleRequest> {
    for (request in requests) {
        val requestsForStudent = arrayListOf<SingleRequest>()
        val existingRequests = request.courses
        outer@ for (single in existingRequests) {
            for (current in currentCombination) {
                if (current.courseID == single && current.students.contains(request.studentID)) {
                    continue@outer
                }
            }
            requestsForStudent.add(SingleRequest(request.studentID, single!!))
        }
        if (requestsForStudent.isNotEmpty()) {
            return requestsForStudent
        }
    }
    return emptyList()
}

fun alreadyAssignedToPeriodTraditional(student: String, classrooms: List<Classroom>, period: Int): Boolean {
    classrooms.forEach {
        if (it.students.contains(student) && it.period == period) {
            return true
        }
    }
    return false
}

fun alreadyAssignedTraditional(student: String, classrooms: List<Classroom>, course: String): Boolean {
    classrooms.forEach {
        if (it.courseID == course && it.students.contains(student)) {
            return true
        }
    }
    return false
}

fun getAvailableTeachersTraditional(
    hashMapData: HashMapData,
    school: String,
    classrooms: List<Classroom>,
    teachers: List<Teacher>,
    course: String,
    period: Int,
    database: Database
): List<Teacher> {
    val time0 = System.currentTimeMillis()
//    val type = database.coursesQueries.selectTypeForCourse(course, school).executeAsOne()
    val typeEfficient = hashMapData.courses[course]!!.type
    val time1 = System.currentTimeMillis()
    val result = arrayListOf<Teacher>()
//    val teachersForType = database.teachersQueries.selectTeachersForType(typeEfficient, school).executeAsList()
    val teachersForTypeEfficient = getTeachersForTypeTraditional(hashMapData.teachers, typeEfficient)
    val time2 = System.currentTimeMillis()
    for (teacher in teachersForTypeEfficient) {
        val occupied = isOccupiedForPeriodTraditional(teacher.id, classrooms, period)
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

fun getTeachersForTypeTraditional(teachers: HashMap<String, Teacher>, type: String): List<Teacher> {
    val result = arrayListOf<Teacher>()
    for ((key, value) in teachers) {
        if (value.type == type) {
            result.add(value)
        }
    }
    return result
}

fun isOccupiedForPeriodTraditional(teacher: String, classrooms: List<Classroom>, period: Int): Boolean {
    val result = arrayListOf<Int>()
    classrooms.forEach {
        if (it.teacherID == teacher && period == it.period) {
            return true
        }
    }
    return false
}

fun getPeriodsForStudentTraditional(classrooms: List<Classroom>, target: String): List<Int> {
    val result = arrayListOf<Int>()
    classrooms.forEach {
        if (it.students.contains(target)) {
            result.add(it.period)
        }
    }
    return result
}

fun isValidScheduleTraditional(
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
        val periods = arrayListOf<Int>()
        classrooms.forEach {
            if (it.students.contains(student.studentID)) {
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