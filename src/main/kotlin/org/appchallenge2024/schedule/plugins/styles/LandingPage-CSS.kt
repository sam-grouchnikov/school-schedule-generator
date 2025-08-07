package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.RadialGradientBuilder
import kotlinx.css.properties.Time
import org.appchallenge2024.schedule.respondCss
import kotlinx.css.properties.LineHeight


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
            fontWeight = FontWeight.normal
        }
        h2{
            fontSize = LinearDimension("38px")
            fontWeight = FontWeight.normal

        }
        rule(".bigtext") {
            fontSize = LinearDimension("60px")
            fontWeight = FontWeight.normal
        }
        rule(".lptextbox-dark") {
            fontSize = LinearDimension("30px")
        }
        rule(".schedwiz-header") {
            color = Color.white
            marginLeft = LinearDimension("15%")
        }
        rule(".topbar-buttons") {
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("30%")
            height = LinearDimension("50%")
            padding = "0"
        }
        rule(".topbar-dark") {
            background = "#2F2F3D"
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
            justifyContent = JustifyContent.left
        }


        rule (".schedwiz-header-dark") {
            marginLeft = LinearDimension("4%")
            color = Color.white
        }

        rule (".landingpage-background-dark") {
            background = "#1A1B27"

        }
        rule(".textbox-container-sp") {
            display = Display.flex
            marginTop = LinearDimension("1%")
            marginLeft = LinearDimension("0.5%")
        }



        rule(".textbox-container-lp-dark") {
            display = Display.flex
            marginTop = LinearDimension("14%")
            justifyContent = JustifyContent.left
            marginLeft = LinearDimension("17%")
        }
        rule(".textbox-lp-dark") {
            color = Color.white
            width = LinearDimension("35%")
            textAlign = TextAlign.left
            val builder = LinearGradientBuilder()
            borderRadius = LinearDimension("15px")
            lineHeight = LineHeight("1.6")
//            backgroundColor = Color.black
        }
        rule (".lp-getstarted-container-dark") {
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center
        }
        rule (".lp-signin-button-dark") {
            backgroundColor = Color("#2962FF")
            color = Color.white
            fontSize = LinearDimension("20px")
            borderRadius = LinearDimension("8%")
            border= "none";
            width = LinearDimension("150%")
            height = LinearDimension("18%")
            cursor = Cursor.pointer

        }
        rule (".lp-signup-button-dark") {
            backgroundColor = Color("#2962FF")
            color = Color.white
            fontSize = LinearDimension("20px")
            borderRadius = LinearDimension("8%")
            padding = "2%"
            border= "none";
            width = LinearDimension("22%")
            height = LinearDimension("10%")
            cursor = Cursor.pointer
            marginTop = LinearDimension("2%")
        }

        rule (".lp-general-button-dark") {
            background= "none";
            border= "none";
            padding = "0"
            margin = "0"
            cursor = Cursor.pointer
            outline = Outline.none
            color = Color.white
            fontSize = LinearDimension("20px")
            borderRadius = LinearDimension("15%")
            padding = "25px"
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