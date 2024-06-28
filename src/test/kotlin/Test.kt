import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.appchallenge2024.schedule.plugins.generateSchedule
import org.appchallenge2024.schedule.plugins.isValidSchedule
import org.appchallenge2024.schedule.sqldelight.data.Database
import org.junit.Test
import kotlin.test.assertEquals


//    private val database = getDatabase()
//    @Test
//    fun test1() {
//        val raw = getRaw1()
//        val map = convertRawToMap(raw.courses, raw.requests, raw.teachers)
//        val schedule = generateSchedule("", raw.courses, raw.requests, raw.teachers, mutableListOf(), database, map)
//        val isValid = isValidSchedule(schedule, raw.courses, raw.requests, raw.teachers)
//        assertEquals(true, isValid)
//    }

//
//fun getDatabase(): Database {
//    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
//    Database.Schema.create(driver)
//    val database = Database(driver)
//    return database
//}

@Test
fun testSample() {
    assertEquals(true, true)
}

