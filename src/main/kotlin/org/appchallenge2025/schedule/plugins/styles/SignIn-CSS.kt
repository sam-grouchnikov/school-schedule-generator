package org.appchallenge2025.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.TextDecoration
import org.appchallenge2025.schedule.respondCss

public suspend fun PipelineContext<Unit, ApplicationCall>.cssSignIn() {
    call.respondCss {
        body {
            margin = "0"
        }

        rule(".poppinsfont") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
        }

        h1 {
            fontSize = LinearDimension("42px")
            fontWeight = FontWeight.w600
        }

        h2 {
            fontSize = LinearDimension("34px")
            fontWeight = FontWeight.w500
        }

        rule(".bigtext") {
            fontSize = LinearDimension("44px")
            fontWeight = FontWeight.w700
            color = Color.white
            marginBottom = LinearDimension("8px")
        }

        rule(".signin-button") {
            border = "none"
            borderRadius = LinearDimension("12px")
            color = Color.white
            backgroundColor = Color("#4D7CFE")
            padding = "12px 34px"
            fontSize = LinearDimension("18px")
            cursor = Cursor.pointer
            transition = "all 0.2s ease"
            hover {
                backgroundColor = Color("#3D6BEB")
                transform {
                    translateY((-1).px)
                }
            }
        }

        a {
            color = Color("#85A8FF")
            textDecoration = TextDecoration.none
        }

        rule(".nodec") {
            textDecoration = TextDecoration.none
            color = Color.white
            fontWeight = FontWeight.w600
        }

        rule(".schedwiz-header") {
            margin = "0"
            textDecoration = TextDecoration.none
            fontSize = LinearDimension("32px")
            letterSpacing = LinearDimension("0.8px")
        }

        rule(".red") {
            fontWeight = FontWeight.w600
            fontSize = LinearDimension("16px")
            color = Color("#FF7B93")
            marginTop = LinearDimension("20px")
        }

        rule(".topbar-buttons") {
            display = Display.flex
            alignItems = Align.center
            gap = Gap("4px")
            marginLeft = LinearDimension("auto")
        }

        rule(".topbar-dark") {
            backgroundColor = Color("rgba(20, 23, 40, 0.76)")
            property("backdrop-filter", "blur(12px)")
            width = LinearDimension("100%")
            color = Color.white
            position = Position.fixed
            top = LinearDimension("0")
            left = LinearDimension("0")
            padding = "18px 56px"
            display = Display.flex
            alignItems = Align.center
            borderBottom = "1px solid rgba(255, 255, 255, 0.08)"
            boxShadow(Color("rgba(0, 0, 0, 0.18)"), offsetX = 0.px, offsetY = 8.px, blurRadius = 30.px)
            zIndex = 100
        }

        rule(".lp-general-button-dark") {
            background = "none"
            border = "none"
            cursor = Cursor.pointer
            outline = Outline.none
            color = Color("#DFE6FF")
            fontSize = LinearDimension("18px")
            borderRadius = LinearDimension("10px")
            padding = "10px 14px"
            transition = "background-color 0.2s ease"
            hover {
                backgroundColor = Color("rgba(255, 255, 255, 0.09)")
            }
        }

        rule(".extrapadding") {
            paddingTop = LinearDimension("20px")
        }

        rule(".textbox-signin-dark") {
            padding = "42px"
            color = Color("#D4DEFF")
            width = LinearDimension("430px")
            maxWidth = LinearDimension("88vw")
            backgroundColor = Color("rgba(27, 34, 58, 0.88)")
            borderRadius = LinearDimension("20px")
            lineHeight = LineHeight("2.1")
            border = "1px solid rgba(255, 255, 255, 0.10)"
            boxShadow(Color("rgba(0, 0, 0, 0.35)"), offsetX = 0.px, offsetY = 30.px, blurRadius = 60.px)
        }

        rule(".inputbox") {
            width = LinearDimension("100%")
            height = LinearDimension("48px")
            borderRadius = LinearDimension("10px")
            backgroundColor = Color("#F3F6FF")
            fontSize = LinearDimension("16px")
            padding = "0 14px"
            border = "1px solid rgba(0, 0, 0, 0.08)"
            boxSizing = BoxSizing.borderBox
        }

        rule(".extralinespacing") {
            lineHeight = LineHeight("3.1")
            marginTop = LinearDimension("12px")
        }

        rule(".textbox-container-signin-dark") {
            display = Display.flex
            minHeight = LinearDimension("100vh")
            justifyContent = JustifyContent.center
            alignItems = Align.center
            paddingTop = LinearDimension("80px")
        }

        rule(".signin-background-dark") {
            minHeight = LinearDimension("100vh")
            background = "radial-gradient(circle at 20% 20%, #2B3A67 0%, #1A1B27 45%, #111320 100%)"
        }
    }
}
