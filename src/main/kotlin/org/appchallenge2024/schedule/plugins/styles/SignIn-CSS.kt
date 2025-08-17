package org.appchallenge2024.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.RadialGradientBuilder
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.Time
import org.appchallenge2024.schedule.respondCss

public suspend fun PipelineContext<Unit, ApplicationCall>.cssSignIn() {
    call.respondCss {

        // fonts and backgrounds
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
            fontSize = LinearDimension("40px")
            fontWeight = FontWeight.normal
        }
        rule(".signin-button") {

            border = "none"
            borderRadius = LinearDimension("10px")
            color = Color.white
            backgroundColor = Color("#2962FF")
            paddingLeft = LinearDimension("50px")
            paddingRight = LinearDimension("50px")
            paddingTop = LinearDimension("10px")
            paddingBottom = LinearDimension("10px")
            fontSize = LinearDimension("20px")
            cursor = Cursor.pointer
            hover {
                backgroundColor = Color("#2A5CE8")
            }
        }
        a {
            color = Color("#2962FF")
        }
        rule(".nodec") {
            textDecoration = TextDecoration.none
            color = Color.white
        }
        rule(".schedwiz-header") {
            color = Color.white
            marginLeft = LinearDimension("240px")
            textDecoration = TextDecoration.none

        }
        rule(".red") {
            fontWeight = FontWeight.bold
            fontSize = LinearDimension("20px")
            color = Color("#F44336")
        }
        rule(".topbar-buttons") {
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("480px")
            height = LinearDimension("50%")
            padding = "0"
        }
        rule(".topbar-dark") {
            background = "#2F2F3D"
            width = LinearDimension("-webkit-fill-available")
            margin = "0%"
            marginLeft = LinearDimension("-63px")
            color = Color.white
            position = Position.fixed
            top = LinearDimension("0")
            left = LinearDimension("0")
            paddingLeft = LinearDimension("45px")
            paddingRight = LinearDimension("33px")
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.left
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
        rule(".extrapadding") {
            paddingTop = LinearDimension("15px")
        }
        rule(".textbox-signin-dark") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            color = Color.white
            width = LinearDimension("27%")
            height = LinearDimension("600px")
            backgroundColor = Color("#2F2F3D")
            borderRadius = LinearDimension("15px")
            lineHeight = LineHeight("2.4")

        }
        rule(".inputbox") {
            width = LinearDimension("330px")
            height = LinearDimension("14px")
            borderRadius = LinearDimension("5px")
            backgroundColor = Color("#F4F3FF")
            fontSize = LinearDimension("18px")
            verticalAlign = VerticalAlign.middle
            paddingLeft = LinearDimension("10px")
            paddingTop = LinearDimension("10px")
            paddingBottom = LinearDimension("8px")

            paddingLeft = LinearDimension("10px")
            border = "none"

        }
        rule(".extralinespacing") {
            lineHeight = LineHeight("4.0")
        }
        rule(".textbox-container-signin-dark") {
            display = Display.flex
            marginTop = LinearDimension("13%")
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("0.5%")

        }
        rule (".signin-background-dark") {
            background = "#1A1B27"

        }
    }
}