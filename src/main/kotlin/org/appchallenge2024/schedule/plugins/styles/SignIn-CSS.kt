package org.appchallenge2024.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.RadialGradientBuilder
import kotlinx.css.properties.Time
import org.appchallenge2024.schedule.respondCss

public suspend fun PipelineContext<Unit, ApplicationCall>.cssSignIn() {
    call.respondCss {

        // fonts and backgrounds
        rule(".poppinsfont") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
        }
        rule(".tealaquagradient") {
            val builder = LinearGradientBuilder()
            builder.colorStop(Color.aqua)
            builder.colorStop(Color.cornflowerBlue)
            background = builder.build("to right bottom", false).value
            backgroundAttachment = BackgroundAttachment.fixed
            backgroundSize = "cover"
        }
        rule(".textalignleft") {
            textAlign = TextAlign.left
        }
        rule(".textalignright") {
            textAlign = TextAlign.right
        }
        rule(".textaligncenter") {
            textAlign = TextAlign.center
        }
        h1 {
            fontSize = LinearDimension("55px")
        }
        h2 {
            fontSize = LinearDimension("35px")
        }
        rule(".signin-button") {
            paddingLeft = LinearDimension("10px")
            paddingRight = LinearDimension("10px")
            fontSize = LinearDimension("15px")
        }
        rule(".topbar-dark") {
            background = "#2d2d2d"
            width = LinearDimension("-webkit-fill-available")
            margin = "0%"
            color = Color.white
            position = Position.fixed
            top = LinearDimension("0")
            left = LinearDimension("0")
            paddingLeft = LinearDimension("3%")
            paddingRight = LinearDimension("3%")
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
        }
        rule(".textbox-signin-dark") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            color = Color.white
            width = LinearDimension("20%")
            backgroundColor = Color.midnightBlue
            border = "10px solid"
            borderColor = Color.midnightBlue
            borderRadius = LinearDimension("15px")
            transitionDuration = Time("0.3s")
            hover {
                val builder2 = RadialGradientBuilder()
                builder2.colorStop(Color.magenta)
                builder2.colorStop(Color.blue)
                background = builder2.build(false).value
                border = "20px solid rgba(255, 255, 255, 0.0)"
            }

        }
        rule(".textbox-container-signin-dark") {
            display = Display.flex
            marginTop = LinearDimension("13%")
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("0.5%")
        }
        rule (".signin-background-dark") {
            background = "#000000"
            val builder3 = RadialGradientBuilder()
            builder3.colorStop(Color.midnightBlue)
            builder3.colorStop(Color.black)
            background = builder3.build(false).value
        }
    }
}