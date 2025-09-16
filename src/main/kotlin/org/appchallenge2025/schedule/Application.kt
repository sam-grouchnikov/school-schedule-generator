package org.appchallenge2025.schedule

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import kotlinx.css.CssBuilder
import org.appchallenge2025.schedule.module
import org.appchallenge2025.schedule.plugins.mainRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module, configure = {
        maxHeaderSize = 200000
        maxInitialLineLength = 200000
    })
        .start(wait = true)
}

fun Application.module() {
    mainRouting()
}


suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}