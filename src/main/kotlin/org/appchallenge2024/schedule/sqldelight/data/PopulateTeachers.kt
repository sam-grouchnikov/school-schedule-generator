//package org.appchallenge2024.schedule.sqldelight.data
//
//import app.cash.sqldelight.db.SqlDriver
//import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
//import data.TeachersQueries
//import data.Teacher
//import java.io.BufferedReader
//import java.io.InputStreamReader
//
//fun main() {
//    val teachers: List<Teacher> = readAllTeachers("/teacher_list.csv")
//    println("Read ${teachers.size} teachers")
//
//    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
//    Database.Schema.create(driver)
//    populateTeachersDatabase(driver, teachers)
//}
//
//fun readAllTeachers(filename: String) : List<Teacher> {
//    val wrapped = BufferedReader(
//        InputStreamReader(
//            Teacher::class.java.getResourceAsStream(filename)
//        )
//    )
//    val result = mutableListOf<Teacher>()
//    while (true) {
//        val line = wrapped.readLine() ?: break
//        val parts = line.split(",")
//        if (parts[0] == "teacher_id") {
//            continue
//        }
//        result.add(
//            Teacher(
//                name = parts[0],
//                room = parts[1],
//                room_capacity = parts[2],
//                type = parts[3]
//            )
//        )
//    }
//    wrapped.close()
//    return result
//}
//
//fun populateTeachersDatabase(driver: SqlDriver, teachers: List<Teacher>) {
//    val database = Database(driver)
//    val teacherQueries: TeachersQueries = database.teachersQueries
//    database.teachersQueries.deleteAll()
//    val start = System.currentTimeMillis()
//    for (teacher in teachers) {
//        teacherQueries.insertTeacherObject(teacher)
//    }
//    val end = System.currentTimeMillis()
//    println("Inserted teachers in " + (end - start) + " ms")
//
//    println("" + teacherQueries.selectAllTeachers().executeAsList().size + " teachers in database")
//}