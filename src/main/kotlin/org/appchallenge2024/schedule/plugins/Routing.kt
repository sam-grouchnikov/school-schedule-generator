package org.appchallenge2024.schedule.plugins

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.appchallenge2024.schedule.sqldelight.data.Database

fun Application.mainRouting() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:atp.db")
    Database.Schema.create(driver)
    val database = Database(driver)
    routing {
        get("/") {
            landingPage(database)
        }
        get("/styles.css") {
            css()
        }
        get("/step1") {
            step1(database)
        }
        get("/step2") {
            step2(database)
        }
        get("/step3") {
            step3(database)
        }
        get("/schedulePage") {
            schedulePage(database)
        }
    }
}