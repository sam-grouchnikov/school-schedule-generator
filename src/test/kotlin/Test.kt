import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.appchallenge2024.schedule.plugins.*
import org.appchallenge2024.schedule.sqldelight.data.Database
import kotlin.test.assertEquals
import kotlin.test.Test

fun getDatabase(): Database {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:schedule.db")
    Database.Schema.create(driver)
    val database = Database(driver)
    return database
}

fun main() {
    val database = getDatabase()
    val raw = getRaw1()
    val map = convertRawToMap(raw.courses, raw.requests, raw.teachers)
    val schedule = generateSchedule("", raw.courses, raw.requests, raw.teachers, mutableListOf(), database, map, ScheduleFormatInfo(ScheduleLayout.TRADITIONAL, 6, 0))
    schedule.forEach {
        println(it)
    }
}
//
//class Test {
//    private val database = getDatabase()
//    @Test
//    fun test1() {
//        val raw = getRaw1()
//        val map = convertRawToMap(raw.courses, raw.requests, raw.teachers)
//        val schedule = generateSchedule("", raw.courses, raw.requests, raw.teachers, mutableListOf(), database, map, ScheduleFormatInfo(ScheduleLayout.TRADITIONAL, 6, 0))
//        val isValid = isValidScheduleTraditional(schedule, raw.courses, raw.requests, raw.teachers)
//        assertEquals(true, isValid)
//    }
//
//    @Test
//    fun test2() {
//        val raw = getRaw2()
//        val map = convertRawToMap(raw.courses, raw.requests, raw.teachers)
//        val schedule = generateSchedule("", raw.courses, raw.requests, raw.teachers, mutableListOf(), database, map, ScheduleFormatInfo(ScheduleLayout.TRADITIONAL, 4, 0))
//        val isValid = isValidScheduleTraditional(schedule, raw.courses, raw.requests, raw.teachers)
//        assertEquals(true, isValid)
//        schedule.forEach {
//            println(it)
//        }
//    }
//}


