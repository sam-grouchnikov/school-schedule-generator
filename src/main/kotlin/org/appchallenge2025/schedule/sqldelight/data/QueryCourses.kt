package org.appchallenge2025.schedule.sqldelight.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

fun main() {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:schedule.db")
    Database.Schema.create(driver)
}
