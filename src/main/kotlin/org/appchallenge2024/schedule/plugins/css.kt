package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.Float
import kotlinx.css.properties.GradientSideOrCorner
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.linearGradient
import org.appchallenge2024.schedule.respondCss
import javax.sound.sampled.Line
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.w3c.dom.Text

public suspend fun PipelineContext<Unit, ApplicationCall>.css() {
    call.respondCss {


        rule(".poppinsfont") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
        }
        rule(".textbox-container-lp") {
            display = Display.flex
            marginTop = LinearDimension("13%")
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("0.5%")
        }
        rule(".textbox-container-sp") {
            display = Display.flex
            marginTop = LinearDimension("1%")
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("0.5%")
        }
        rule(".textbox-container-lp-step2") {
            display = Display.block
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("1.7%")
        }
        rule(".textbox-lp") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            border = "1px solid"
            width = LinearDimension("30%")
            val builder = LinearGradientBuilder()
            builder.colorStop(Color.darkOrange)
            builder.colorStop(Color.red)
            builder.colorStop(Color.purple)
//            background = builder.build("to right bottom", false).value
            color = Color.black
            border = "10px solid"
            borderRadius = LinearDimension("20px")
        }

        rule(".textbox-step1-1") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            border = "1px solid"
            width = LinearDimension("30%")
            color = Color.black
            border = "10px solid"
            borderRadius = LinearDimension("20px")
        }
        rule(".textbox-step1-2") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            border = "1px solid"
            width = LinearDimension("40%")
            color = Color.black
            border = "10px solid"
            borderRadius = LinearDimension("20px")
        }
        rule(".textbox-step1-3") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            width = LinearDimension("44.8%")
            color = Color.black
            border = "10px solid"
            borderRadius = LinearDimension("20px")
        }
        rule(".textbox-step2-1") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            border = "1px solid"
            width = LinearDimension("45%")
            color = Color.black
            border = "10px solid"
            borderRadius = LinearDimension("20px")
        }
        rule(".textbox-step2-2") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            border = "1px solid"
            width = LinearDimension("45%")
            color = Color.black
            border = "10px solid"
            borderRadius = LinearDimension("20px")
        }
        rule(".steps-textarea") {
            backgroundColor = Color.aqua
            borderRadius = LinearDimension("15px")
        }
        rule(".steps-td-th") {
            border = "2px solid #000000"
            textAlign = TextAlign.left
            padding = "0.2%"
        }
        rule(".schedulepage-td-th") {
            border = "2px solid #000000"
            textAlign = TextAlign.left
            padding = "0.2%"
            hover {
                backgroundColor = Color.lightSeaGreen
            }
        }
        rule(".sp-table") {
            width = LinearDimension("100%")
        }
        rule(".steps-table") {
            width = LinearDimension("100%")
        }
        rule(".tealaquagradient") {
            val builder = LinearGradientBuilder()
            builder.colorStop(Color.aqua)
            builder.colorStop(Color.cornflowerBlue)
            background = builder.build("to right bottom", false).value
            backgroundAttachment = BackgroundAttachment.fixed
            backgroundSize = "cover"
        }
        rule(".getstartedbutton") {
            backgroundColor = Color.black
            width = LinearDimension("10%")
            textAlign = TextAlign.center
            border = "10px solid"
            borderRadius = LinearDimension("10px")
            borderColor = Color.black
            marginLeft = LinearDimension("37%")
            color = Color.red
        }
        rule(".topbar") {
            backgroundColor = Color.black
            width = LinearDimension("100%")
            margin = "0%"
            color = Color.white
            position = Position.fixed
            top = LinearDimension("0")
            left = LinearDimension("0")
            paddingLeft =LinearDimension("15px")
            paddingRight = LinearDimension("15px")

        }
        rule(".steps-navigator-container") {
            textAlign = TextAlign.center
            marginLeft = LinearDimension("33.7%")
            padding = "2%"
            display = Display.inlineBlock
            width = LinearDimension("27%")
            border = "10px solid"
            borderRadius = LinearDimension("20px")
        }
        rule(".sp-navigator-container") {
            textAlign = TextAlign.center
            marginLeft = LinearDimension("33.7%")
            padding = "2%"
            display = Display.inlineBlock
            width = LinearDimension("27%")
            border = "10px solid"
            borderRadius = LinearDimension("20px")
            marginTop = LinearDimension("12%")
        }
        rule(".steps-navigator-button") {
            display = Display.inlineBlock
            padding = "1% 2%"
            margin = ("1%")
            cursor = Cursor.pointer
        }
        rule(".schedwizheader") {
            marginLeft = LinearDimension("4%")
        }
        rule(".headercontainer") {
            justifyContent = JustifyContent.spaceBetween
            color = Color.aqua
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
        rule(".lptextbox") {
            fontSize = LinearDimension("20px")
        }
        rule("steps-button-fontsize") {
            fontSize = LinearDimension("30px")
        }
        rule(".steps-font") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
            fontSize = LinearDimension("20px")
        }
    }
}