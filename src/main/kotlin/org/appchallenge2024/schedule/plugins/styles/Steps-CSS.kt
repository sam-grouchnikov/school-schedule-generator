package org.appchallenge2024.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.RadialGradientBuilder
import kotlinx.css.properties.Time
import kotlinx.css.properties.boxShadow
import org.appchallenge2024.schedule.respondCss
import javax.sound.sampled.Line

suspend fun PipelineContext<Unit, ApplicationCall>.cssSteps() {
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
        rule(".font-45px") {
            fontSize = LinearDimension("45px")
            padding = "2%"
        }
        rule(".font20px") {
            fontSize = LinearDimension("28px")
            paddingTop = LinearDimension("10px")
            paddingBottom = LinearDimension("10px")
        }
        h1 {
            fontSize = LinearDimension("55px")
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
        rule(".schedwiz-header-dark") {
            marginLeft = LinearDimension("4%")
            color = Color.white
        }
        rule(".textbox-container-steps") {
            display = Display.flex
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("7%")
            height = LinearDimension("fit-content")
            gap = LinearDimension("6%")
        }
        rule(".textbox-container-steps2") {
            display = Display.flex
            justifyContent = JustifyContent.center
            height = LinearDimension("fit-content")
            gap = LinearDimension("6%")
        }
        rule(".listContainer") {
            width = LinearDimension("48%")
            height = LinearDimension("fit-content")
            padding = "10px"
            marginRight = LinearDimension("28px")
            borderRadius = LinearDimension("10px")
            backgroundColor = Color("#2F2F3D")
        }
        rule(".listContainer2") {
            width = LinearDimension("60%")
            height = LinearDimension("fit-content")
            padding = "10px"
            marginRight = LinearDimension("28px")
            borderRadius = LinearDimension("10px")
            backgroundColor = Color("#2F2F3D")
        }

        rule(".steps-navigator-container") {
            textAlign = TextAlign.center
            alignItems = Align.center
            marginTop = LinearDimension("8%")
            padding = "2%"
            marginLeft = LinearDimension("10%")
            display = Display.flex
            width = LinearDimension("-webkit-fill-available")
            color = Color.white
            fontSize = LinearDimension("30px")
            justifyContent = JustifyContent.center
            gap = LinearDimension("24.5%")
        }

        rule(".steps-navigator-container2") {
            textAlign = TextAlign.center
            alignItems = Align.center
            marginTop = LinearDimension("8%")
            padding = "2%"
            marginLeft = LinearDimension("-8%")
            display = Display.flex
            width = LinearDimension("-webkit-fill-available")
            color = Color.white
            fontSize = LinearDimension("30px")
            justifyContent = JustifyContent.center
            gap = LinearDimension("14%")
        }
        rule(".steps-navigator-container3") {
            textAlign = TextAlign.center
            alignItems = Align.center
            marginTop = LinearDimension("8%")
            padding = "2%"
            marginLeft = LinearDimension("-1%")
            display = Display.flex
            width = LinearDimension("-webkit-fill-available")
            color = Color.white
            fontSize = LinearDimension("30px")
            justifyContent = JustifyContent.center
            gap = LinearDimension("14%")
        }
        rule(".customcolwidth") {
            gridTemplateColumns = GridTemplateColumns("10% 60% 20%")
        }
        rule(".darkgrey") {
            color = Color("#1A1B27")
        }
        rule(".leftpadding") {
            paddingLeft = LinearDimension("2%")
        }
        rule(".schoolcontainer") {
            width = LinearDimension("30%")
        }
        rule(".steps-navigator-button") {
            border = "none"
            borderColor = Color.midnightBlue
            backgroundColor = Color("#2962FF")
            borderRadius = LinearDimension("10px")
            fontSize = LinearDimension("20px")
            color = Color.white
            padding = "10px"
            paddingLeft = LinearDimension("30px")
            paddingRight = LinearDimension("30px")
            cursor = Cursor.pointer
        }

        rule(".steps-navigator-button-purple") {
            border = "none"
            borderColor = Color.midnightBlue
            backgroundColor = Color("#AA00FF")
            borderRadius = LinearDimension("10px")
            fontSize = LinearDimension("20px")
            color = Color.white
            padding = "10px"
            paddingLeft = LinearDimension("30px")
            paddingRight = LinearDimension("30px")
            cursor = Cursor.pointer
        }
        rule(".steps-fontsize") {
            fontSize = LinearDimension("20px")
        }

        rule(".steps-background-dark") {
            background = "#1A1B27"
        }
        rule(".textbox-step1-1") {
            padding = "10px"
            width = LinearDimension("30%")
            color = Color.white
            backgroundColor = Color("#2F2F3D")
            borderRadius = LinearDimension("8px")
        }
        rule(".textbox-step1-1-center") {
            textAlign = TextAlign.center
            padding = "10px"
            paddingBottom = LinearDimension("15px")
            width = LinearDimension("37%")
            color = Color.white
            backgroundColor = Color("#2F2F3D")
            borderRadius = LinearDimension("10px")

        }
        rule(".periods-input") {
            width = LinearDimension("7%")
        }

        rule(".textbox-step1-2") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            width = LinearDimension("30%")
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
            color = Color.white
            borderRadius = LinearDimension("20px")
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
            }
        }
        rule(".textbox-step1-3") {
            marginRight = LinearDimension("1px")
            backgroundColor = Color("#2F2F3D")
            padding = "10px"
            width = LinearDimension("20%")
            color = Color.white
            height = LinearDimension("fit-content")
            borderRadius = LinearDimension("10px")
            paddingLeft = LinearDimension("20px")
            paddingRight = LinearDimension("20px")

        }
        rule(".steps-textarea") {
            width = LinearDimension("-webkit-fill-available")
            height = LinearDimension("250px")
            color = Color.black
            backgroundColor = Color("#DFDBFF")
        }
        rule(".textbox-step2-1") {
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
            marginRight = LinearDimension("10px")
            padding = "2%"
            width = LinearDimension("20%")
            color = Color.white
            borderRadius = LinearDimension("20px")
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
            }
        }
        rule(".steps-form-height") {
            height = LinearDimension("50%")
        }
        rule(".textbox-step2-3") {
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
            marginRight = LinearDimension("10px")
            padding = "2%"
            width = LinearDimension("24%")
            color = Color.white
            borderRadius = LinearDimension("20px")
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
            }
            paddingLeft = LinearDimension("20px")
            paddingRight = LinearDimension("20px")
        }
        rule(".steps-navigator-padding") {
            padding = "2%"
        }
        rule(".textbox-step2-2") {
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
            marginRight = LinearDimension("10px")
            padding = "2%"
            width = LinearDimension("40%")
            color = Color.white
            borderRadius = LinearDimension("20px")
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
            }
        }
        rule(".steps-td-th") {
            backgroundColor = Color("#2F2F3D")
            textAlign = TextAlign.left
            padding = "0.2%"
            color = Color.white

        }
        rule(".steps-table") {
            display = Display.grid
            width = LinearDimension("100%")
            fontSize = LinearDimension("20px")
            rowGap = LinearDimension("10px")
        }
        rule(".steps-table3") {
            display = Display.grid
            width = LinearDimension("100%")
            fontSize = LinearDimension("20px")
            gap = LinearDimension("10px")
            gridTemplateColumns = GridTemplateColumns("repeat(3, minmax(max-content, 1fr))")
            // ID = 1fr, Name = 1fr, Courses = 2fr (wider)
        }

        rule(".header") {
            fontWeight = FontWeight.bold
            backgroundColor = Color("#3A3A4F")
        }
        rule(".steps-cell") {
            backgroundColor = Color("#2F2F3D")
            textAlign = TextAlign.left
            padding = "0.5rem"
            color = Color.white
            borderRadius = LinearDimension("4px")
        }
        rule(".steps-table2") {
            display = Display.grid
            width = LinearDimension("100%")
            fontSize = LinearDimension("20px")
            gap = LinearDimension("5px") // works for both row + column spacing
            gridTemplateColumns = GridTemplateColumns("repeat(5, minmax(max-content, 1fr))")
        }

        rule(".font-25px") {
            fontSize = LinearDimension("29px")
        }
    }
}