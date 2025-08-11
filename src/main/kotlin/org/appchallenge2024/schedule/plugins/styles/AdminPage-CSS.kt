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
        rule(".flex2") {
            display = Display.flex
        }
        rule(".font32") {
            marginTop = LinearDimension("12%")
            color = Color.white
            textAlign = TextAlign.center
            fontSize = LinearDimension("32px")
        }
        rule(".font25") {
            color = Color.white
            textAlign = TextAlign.center
            fontSize = LinearDimension("25px")
        }
        h2 {
            fontSize = LinearDimension("35px")
        }
        rule(".lptextbox-dark") {
            fontSize = LinearDimension("20px")
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

        rule(".adminpage-td-th") {
            borderRadius = LinearDimension("10px")
            textAlign = TextAlign.left
            padding = "0.2%"
            color = Color.white
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("1%")
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
                padding = "0.5%"
            }
        }

        rule(".schedwiz-header-dark") {
            marginLeft = LinearDimension("4%")
            color = Color.white
        }

        rule(".adminpage-background-dark") {
            background = "#1A1B27"
        }
        td {
            paddingTop = LinearDimension("1.5%")
            paddingBottom = LinearDimension("1.5%")
        }
        rule(".flex-outer-1") {
            display = Display.flex
            justifyContent = JustifyContent.spaceEvenly
            marginTop = LinearDimension("2%")
        }
        rule(".bold") {
            fontWeight = FontWeight.bold
            paddingRight = LinearDimension("2%")
            paddingLeft = LinearDimension("2%")
        }
        rule(".flex-outer-2") {
            display = Display.flex
            justifyContent = JustifyContent.center
            marginTop = LinearDimension("2%")
        }
        rule(".table-container") {
            color = Color.white
            width = LinearDimension("70%")
            height = LinearDimension("100%")
            borderRadius = LinearDimension("15px")
            justifyContent = JustifyContent.center
            backgroundColor = Color("#2F2F3D")
            display = Display.flex
            justifyContent = JustifyContent.spaceAround

            padding = "2%"
        }
        tr {
            nthChild("even") {
                backgroundColor = Color("#39394A")
            }
            paddingTop = LinearDimension("2%")
        }
        rule(".sp-table") {
            width = LinearDimension("-webkit-fill-available")
            textAlign = TextAlign.center
        }
        rule(".white") {
            color = Color.white
        }
        rule(".adminpage-button") {
            border = "none"
            padding = "15px"
            backgroundColor = Color("#282933")
            borderRadius = LinearDimension("15px")
            fontSize = LinearDimension("23px")
            color = Color.white
            cursor = Cursor.pointer
        }
        rule(".adminpage-button2") {
            border = "none"
            padding = "15px"
            backgroundColor = Color("#8436AC")
            borderRadius = LinearDimension("15px")
            fontSize = LinearDimension("23px")
            color = Color.white
            cursor = Cursor.pointer
        }
        rule(".students-column") {
            width = LinearDimension("30%")
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