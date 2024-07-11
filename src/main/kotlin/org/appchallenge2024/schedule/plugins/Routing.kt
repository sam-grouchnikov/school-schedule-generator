package org.appchallenge2024.schedule.plugins

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.appchallenge2024.schedule.sqldelight.data.Database
import io.ktor.server.http.content.*
import org.appchallenge2024.schedule.plugins.styles.cssAdminPage
import org.appchallenge2024.schedule.plugins.styles.cssSignIn
import org.appchallenge2024.schedule.plugins.styles.cssSteps
import java.io.File

fun Application.mainRouting() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:schedule.db")
    Database.Schema.create(driver)
    val database = Database(driver)
    routing {
        get("/") {
            landingPage(database)
        }
        get("/styles.css") {
            css()
        }
        get("/cssLanding") {
            cssLanding()
        }
        get("/cssSignIn") {
            cssSignIn()
        }
        get("/cssAdminPage") {
            cssAdminPage()
        }
        get("/cssSteps") {
            cssSteps()
        }
        get("/addSchoolToDB") {
            addSchoolToDB(database)
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
        get("/step4") {
            step4(database)
        }
        get("/signInLanding") {
            signInLanding(database)
        }
        get("/adminPage") {
            adminPage(database)
        }
        get("/verifyCredentials") {
            verifyCredentials(database)
        }
        get("/verifyNewSchool") {
            verifyNewSchool(database)
        }
        get("/addSchedToDB") {
            addSchedToDB(database)
        }
        staticFiles("/images", File("C:\\Users\\Sam\\IdeaProjects\\school-schedule-generator\\src\\main\\kotlin\\org\\appchallenge2024\\schedule\\images\\logo.png"))

    }
}