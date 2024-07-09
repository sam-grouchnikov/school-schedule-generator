package org.appchallenge2024.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.RadialGradientBuilder
import kotlinx.css.properties.Time
import kotlinx.css.properties.boxShadow
import org.appchallenge2024.schedule.respondCss

suspend fun PipelineContext<Unit, ApplicationCall>.cssAdminPage() {
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
        h1 {
            fontSize = LinearDimension("55px")
        }
        rule(".flex") {
            display = Display.flex
        }
        h2 {
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
            justifyContent = JustifyContent.spaceEvenly
        }

        rule(".schedwiz-header-dark") {
            marginLeft = LinearDimension("4%")
            color = Color.white
        }

        rule(".adminpage-background-dark") {
            background = "#000000"
            val builder3 = RadialGradientBuilder()
            builder3.colorStop(Color("rgba(25, 25, 112) 15%"))
            builder3.colorStop(Color("rgba(0, 0, 0) 85%"))
            background = builder3.build(false).value
        }
        rule(".flex-outer") {
            display = Display.flex
            justifyContent = JustifyContent.center
            marginTop = LinearDimension("13%")
        }
        rule(".table-container") {
            color = Color.white
            width = LinearDimension("60%")
            height = LinearDimension("100%")
            borderRadius = LinearDimension("15px")
            justifyContent = JustifyContent.center
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
            hover {
                transitionDuration = Time("0.3s")
                border = "10px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
            }
            padding = "2%"
        }
        rule(".adminpage-button") {
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
        rule (".right-boxes") {
            display = Display.flex
            flexDirection = FlexDirection.column
            width = LinearDimension("40%")
        }
        rule (".right-box") {
            border = "10px solid #ffffff"
            borderRadius = LinearDimension("15px")
            display = Display.flex
            justifyContent = JustifyContent.center
            margin = "1%"
        }
        rule(".whitetext") {
            color = Color.white
        }
    }
}