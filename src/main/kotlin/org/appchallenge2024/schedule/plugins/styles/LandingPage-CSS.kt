package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.RadialGradientBuilder
import kotlinx.css.properties.Time
import org.appchallenge2024.schedule.respondCss

suspend fun PipelineContext<Unit, ApplicationCall>.cssLanding() {
    call.respondCss {
        rule(".poppinsfont") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
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
        h1{
            fontSize = LinearDimension("55px")
        }
        h2{
            fontSize = LinearDimension("35px")
        }
        rule(".lptextbox-dark") {
            fontSize = LinearDimension("20px")
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

        rule (".schedwiz-header-dark") {
            marginLeft = LinearDimension("4%")
            color = Color.white
        }

        rule (".landingpage-background-dark") {
            background = "#000000"
            val builder3 = RadialGradientBuilder()
            builder3.colorStop(Color.midnightBlue)
            builder3.colorStop(Color.black)
            background = builder3.build(false).value
        }
        rule(".textbox-container-sp") {
            display = Display.flex
            marginTop = LinearDimension("1%")
            marginLeft = LinearDimension("0.5%")
        }



        rule(".textbox-container-lp-dark") {
            display = Display.flex
            marginTop = LinearDimension("13%")
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("0.5%")
        }
        rule(".textbox-lp-dark") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            color = Color.white
            width = LinearDimension("30%")
            val builder = LinearGradientBuilder()
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
        rule (".lp-getstarted-container-dark") {
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center
        }
        rule (".lp-getstarted-button-dark") {
            backgroundColor = Color.white
            color = Color.black
            fontSize = LinearDimension("20px")
            border = "10 px solid #ffffff"
            borderRadius = LinearDimension("15%")
            padding = "1%"
            hover {
                backgroundColor = Color.gainsboro
                padding = "2%"
            }

        }
        rule(".lp-modeswitch-dark") {
            border = "10px solid"
            borderColor = Color.midnightBlue
            backgroundColor = Color.midnightBlue
            borderRadius = LinearDimension("15px")
            fontSize = LinearDimension("23px")
            color = Color.white
            hover {
                val builder2 = RadialGradientBuilder()
                builder2.colorStop(Color.magenta)
                builder2.colorStop(Color.blue)
                background = builder2.build(false).value
                border = "10px solid rgba(255, 255, 255, 0.0)"
            }
        }
        rule(".lp-buttons-container") {
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
        }

    }
}