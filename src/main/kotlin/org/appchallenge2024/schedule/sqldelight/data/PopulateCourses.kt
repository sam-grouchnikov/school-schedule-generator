//package org.appchallenge2024.schedule.sqldelight.data
//
//import app.cash.sqldelight.db.SqlDriver
//import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
//import data.Course
//import data.CoursesQueries
//import java.io.BufferedReader
//import java.io.InputStreamReader
//
//fun main() {
//    val courses: List<Course> = readAllCourses("/course_catalog.csv")
//    println("Read ${courses.size} players")
//
//    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
//    Database.Schema.create(driver)
//    populateCoursesDatabase(driver, courses)
//}
//
//fun readAllCourses(filename: String) : List<Course> {
//    val wrapped = BufferedReader(
//        InputStreamReader(
//            Course::class.java.getResourceAsStream(filename)
//        )
//    )
//    val result = mutableListOf<Course>()
//    while (true) {
//        val line = wrapped.readLine() ?: break
//        val parts = line.split(",")
//        if (parts[0] == "course_id") {
//            continue
//        }
//        result.add(
//            Course(
//                student_id = parts[0],
//                name = parts[1],
//                type = parts[2]
//            )
//        )
//    }
//    wrapped.close()
//    return result
//}
//
//fun populateCoursesDatabase(driver: SqlDriver, courses: List<Course>) {
//    val database = Database(driver)
//    val courseQueries: CoursesQueries = database.coursesQueries
////    database.coursesQueries.deleteAll()
//    val start = System.currentTimeMillis()
//    for (course in courses) {
//        courseQueries.insertCourseObject(course)
//    }
//    val end = System.currentTimeMillis()
//    println("Inserted players in " + (end - start) + " ms")
//
//    println("" + courseQueries.selectAllCourses().executeAsList().size + " players in database")
//}