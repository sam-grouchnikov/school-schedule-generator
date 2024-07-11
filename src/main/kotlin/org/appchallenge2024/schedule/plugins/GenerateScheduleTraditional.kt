package org.appchallenge2024.schedule.plugins

import data.Course
import data.Teacher
import org.appchallenge2024.schedule.sqldelight.data.Database

// gets all possible next assignments for a traditional schedule layout

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
    val remainingRequests = getRemainingRequestsTraditional(currentCombination, requests)
    for (request in remainingRequests) {
        // check if we can add the student to an existing class
        for (i in currentCombination.indices) {
            val capacityEfficient = hashMapData.teachers[currentCombination[i].teacherID]!!.room_capacity
            val periodsAssignedForStudent = getPeriodsForStudentTraditional(currentCombination, request.student)
            // creates a new possible assignment if
            // 1. The current classroom course matches the course of the request
            // 2. The current classroom is not full
            // 3. The student does not have a class already assigned to this period
            // 4. The current student request has not already been filled
             if (request.course == currentCombination[i].courseID && currentCombination[i].students.size < capacityEfficient.toInt() &&
                !periodsAssignedForStudent.contains(currentCombination[i].period) && !alreadyAssigned(
                    request.student,
                    currentCombination,
                    request.course
                )
            ) {
                 // creates a deep copy of the current schedule branch
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
        }

        // check if we can create a new class with the student
        for (period in 1..periods) {

            val availableTeachersForClass = getAvailableTeachersTraditional(
                hashMapData,
                currentCombination,
                request.course,
                period,
            )
            for (teacher in availableTeachersForClass) {
                // if the student has not already been assigned to the course, a new class is created  as a possible assignment
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

        }
    }
    return result
}

// gets the remaining requests that have not been filled yet

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

// checks if a student has a class assigned for a given period

fun alreadyAssignedToPeriodTraditional(student: String, classrooms: List<Classroom>, period: Int): Boolean {
    classrooms.forEach {
        if (it.students.contains(student) && it.period == period) {
            return true
        }
    }
    return false
}

// given a period, checks for all teachers available (not teaching a class at the time)

fun getAvailableTeachersTraditional(
    hashMapData: HashMapData,
    classrooms: List<Classroom>,
    course: String,
    period: Int,
): List<Teacher> {
    val type = hashMapData.courses[course]!!.type
    val result = arrayListOf<Teacher>()
    val teachersForType = getTeachersForTypeTraditional(hashMapData.teachers, type)
    for (teacher in teachersForType) {
        val occupied = isOccupiedForPeriodTraditional(teacher.id, classrooms, period)
        if (!occupied) {
            result.add(teacher)
        }
    }
    return result
}

// gets a list of teachers that teach a certain type of class

fun getTeachersForTypeTraditional(teachers: HashMap<String, Teacher>, type: String): List<Teacher> {
    val result = arrayListOf<Teacher>()
    for ((key, value) in teachers) {
        if (value.type == type) {
            result.add(value)
        }
    }
    return result
}

// checks if a teacher is occupied in a given block

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

// checks if a complete schedule fulfills all the requirements for a valid schedule

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