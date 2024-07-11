package org.appchallenge2024.schedule.plugins

import data.Course
import data.Teacher
import org.appchallenge2024.schedule.sqldelight.data.Database

data class BlockPeriod(val semester: Int, val period: Int)

// given all data and the amount of periods for each semester, this returns all next possible single assignments

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
    val remainingRequests = getRemainingRequestsBlock(currentCombination, requests)
    for (request in remainingRequests) {
        // check if we can add the student to an existing class
        for (i in currentCombination.indices) {
            val capacity = hashMapData.teachers[currentCombination[i].teacherID]!!.room_capacity
            val periodsAssignedForStudent = getPeriodsForStudentBlock(currentCombination, request.student)
            // creates a new possible assignment if
            // 1. The current classroom course matches the course of the request
            // 2. The current classroom is not full
            // 3. The student does not have a class already assigned to this period
            // 4. The current student request has not already been filled
            if (request.course == currentCombination[i].courseID && currentCombination[i].students.size < capacity.toInt() &&
                !periodsAssignedForStudent.contains(BlockPeriod(currentCombination[i].semester, currentCombination[i].period)) && !alreadyAssigned(
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
                        Classroom(classroom.courseID, classroom.teacherID, classroom.semester, classroom.period, studentsCopy)
                    temp.add(classroomCopy)
                }
                // adds the student to the target class
                temp[i].students.add(request.student)
                //  adds the possible assignment to the result
                result.add(temp)

            }
        }

        // check if we can create a new class with the student

        // checks for availability in the first semester
        for (period in 1..periods1) {

            val availableTeachersForClass = getAvailableTeachersBlock(
                hashMapData,
                currentCombination,
                request.course,
                BlockPeriod(1, period),
            )
            // iterates over every available teacher to teach the class in the current period
            for (teacher in availableTeachersForClass) {
                // if the request has not already been assigned, a new class can be created
                if (!alreadyAssigned(request.student, currentCombination, request.course)) {
                    /// creates a deep copy of the current schedule branch
                    val temp = arrayListOf<Classroom>()
                    for (classroom in currentCombination) {
                        val studentsCopy = arrayListOf<String>()
                        studentsCopy.addAll(classroom.students)
                        val classroomCopy =
                            Classroom(classroom.courseID, classroom.teacherID, classroom.semester, classroom.period, studentsCopy)
                        temp.add(classroomCopy)
                    }
                    temp.add(Classroom(request.course, teacher.id, 1, period, arrayListOf(request.student)))
                    // if the student does not have the request fullfilled and if not assigned to this period, the assignment is added to the result
                    if (!alreadyAssigned(request.student, currentCombination, request.course) &&
                        !alreadyAssignedToPeriodBlock(request.student, currentCombination, period, 1)
                    )
                        result.add(temp)
                }
            }

        }

        // checks for availability in the second semester with same logic as the first semester

        for (period in 1..periods2) {
            val availableTeachersForClass = getAvailableTeachersBlock(
                hashMapData,
                currentCombination,
                request.course,
                BlockPeriod(2, period),
            )
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
        }
    }
    return result
}

// gets the remaining requests that have not been filled yet

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

// checks if a student has a class assigned for a given period and semester

fun alreadyAssignedToPeriodBlock(student: String, classrooms: List<Classroom>, period: Int, semester: Int): Boolean {
    classrooms.forEach {
        if (it.students.contains(student) && it.period == period && semester == it.semester) {
            return true
        }
    }
    return false
}

// checks if a student has already been assigned a given course

fun alreadyAssigned(student: String, classrooms: List<Classroom>, course: String): Boolean {
    classrooms.forEach {
        if (it.courseID == course && it.students.contains(student)) {
            return true
        }
    }
    return false
}

// given a period, checks for all teachers available (not teaching a class at the time)

fun getAvailableTeachersBlock(
    hashMapData: HashMapData,
    classrooms: List<Classroom>,
    course: String,
    period: BlockPeriod,
): List<Teacher> {
    val type = hashMapData.courses[course]!!.type
    val result = arrayListOf<Teacher>()
    val teachersForType = getTeachersForTypeBlock(hashMapData.teachers, type)
    for (teacher in teachersForType) {
        val occupied = isOccupiedForPeriodBlock(teacher.id, classrooms, period.period, period.semester)
        if (!occupied) {
            result.add(teacher)
        }
    }
    return result
}

// gets a list of teachers that teach a certain type of class

fun getTeachersForTypeBlock(teachers: HashMap<String, Teacher>, type: String): List<Teacher> {
    val result = arrayListOf<Teacher>()
    for ((_, value) in teachers) {
        if (value.type == type) {
            result.add(value)
        }
    }
    return result
}

// checks if a teacher is occupied in a given block

fun isOccupiedForPeriodBlock(teacher: String, classrooms: List<Classroom>, period: Int, semester: Int): Boolean {
    classrooms.forEach {
        if (it.teacherID == teacher && period == it.period && semester == it.semester) {
            return true
        }
    }
    return false
}


fun getPeriodsForStudentBlock(classrooms: List<Classroom>, target: String): List<BlockPeriod> {
    val result = arrayListOf<BlockPeriod>()
    classrooms.forEach {
        if (it.students.contains(target)) {
            result.add(BlockPeriod(it.semester, it.period))
        }
    }
    return result
}

// checks if a complete schedule fulfills all the requirements for a valid schedule

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