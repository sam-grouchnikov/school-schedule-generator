//package org.appchallenge2024.schedule.sqldelight.data
//
//import app.cash.sqldelight.db.SqlDriver
//import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
//import data.Request
//import data.RequestsQueries
//import java.io.BufferedReader
//import java.io.InputStreamReader
//
//fun main() {
//    val requests: List<Request> = readAllRequests("/student_course_requests.csv")
//    println("Read ${requests.size} requests")
//
//    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
//    Database.Schema.create(driver)
//    populateRequestsDatabase(driver, requests)
//}
//
//fun readAllRequests(filename: String) : List<Request> {
//    val wrapped = BufferedReader(
//        InputStreamReader(
//            Request::class.java.getResourceAsStream(filename)
//        )
//    )
//    val result = mutableListOf<Request>()
//    while (true) {
//        val line = wrapped.readLine() ?: break
//        val parts = line.split(",")
//        if (parts[0] == "student_id") {
//            continue
//        }
//        result.add(
//            Request(
//                student_id = parts[0],
//                student_name = parts[1],
//                course1 = parts[2],
//                course2 = parts[3],
//                course3 = parts[4],
//                course4 = parts[5],
//            )
//        )
//    }
//    wrapped.close()
//    return result
//}
//
//fun populateRequestsDatabase(driver: SqlDriver, requests: List<Request>) {
//    val database = Database(driver)
//    val requestQueries: RequestsQueries = database.requestsQueries
//    database.requestsQueries.deleteAll()
//    val start = System.currentTimeMillis()
//    for (request in requests) {
//        requestQueries.insertRequestObject(request)
//    }
//    val end = System.currentTimeMillis()
//    println("Inserted requests in " + (end - start) + " ms")
//
//    println("" + requestQueries.selectAllRequests().executeAsList().size + " requests in database")
//}