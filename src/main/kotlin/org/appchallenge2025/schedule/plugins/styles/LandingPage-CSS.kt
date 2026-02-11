package org.appchallenge2025.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.TextDecoration
import org.appchallenge2025.schedule.respondCss

suspend fun PipelineContext<Unit, ApplicationCall>.cssLanding() {
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
            fontSize = LinearDimension("68px")
            fontWeight = FontWeight.w700
            lineHeight = LineHeight("1.1")
            marginBottom = LinearDimension("16px")
            maxWidth = LinearDimension("720px")
        }

        rule(".lptextbox-dark") {
            fontSize = LinearDimension("22px")
            color = Color("#CDD8F9")
            lineHeight = LineHeight("1.7")
        }

        rule(".hero-badge") {
            display = Display.inlineBlock
            color = Color("#DCE7FF")
            backgroundColor = Color("rgba(106, 146, 255, 0.22)")
            padding = "8px 14px"
            borderRadius = LinearDimension("999px")
            fontSize = LinearDimension("14px")
            letterSpacing = LinearDimension("0.8px")
            textTransform = TextTransform.uppercase
            marginBottom = LinearDimension("20px")
        }

        rule(".hero-subtext") {
            maxWidth = LinearDimension("680px")
            marginBottom = LinearDimension("34px")
        }

        rule(".hero-actions") {
            display = Display.flex
            gap = Gap("16px")
            alignItems = Align.center
        }

        rule(".hero-secondary-button") {
            backgroundColor = Color("rgba(255, 255, 255, 0.06)")
            color = Color.white
            border = "1px solid rgba(255, 255, 255, 0.20)"
            borderRadius = LinearDimension("12px")
            padding = "12px 24px"
            fontSize = LinearDimension("18px")
            cursor = Cursor.pointer
            transition = "all 0.2s ease"
            hover {
                backgroundColor = Color("rgba(255, 255, 255, 0.14)")
                transform {
                    translateY((-1).px)
                }
            }
        }

        rule(".schedwiz-header") {
            margin = "0"
            textDecoration = TextDecoration.none
            fontSize = LinearDimension("32px")
            letterSpacing = LinearDimension("0.8px")
        }

        rule(".nodec") {
            textDecoration = TextDecoration.none
            color = Color.white
            fontWeight = FontWeight.w600
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

        rule(".landingpage-background-dark") {
            minHeight = LinearDimension("100vh")
            background = "radial-gradient(circle at 20% 20%, #2B3A67 0%, #1A1B27 40%, #111320 100%)"
        }

        rule(".textbox-container-lp-dark") {
            display = Display.flex
            paddingTop = LinearDimension("180px")
            paddingLeft = LinearDimension("12%")
            paddingRight = LinearDimension("8%")
        }

        rule(".textbox-lp-dark") {
            width = LinearDimension("100%")
        }

        rule(".lp-getstarted-container-dark") {
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center
        }

        rule(".lp-signup-button-dark") {
            backgroundColor = Color("#4D7CFE")
            color = Color.white
            borderRadius = LinearDimension("12px")
            padding = "12px 28px"
            fontSize = LinearDimension("18px")
            border = "none"
            cursor = Cursor.pointer
            transition = "all 0.2s ease"
            hover {
                backgroundColor = Color("#3D6BEB")
                transform {
                    translateY((-1).px)
                }
                boxShadow(Color("rgba(77, 124, 254, 0.35)"), offsetX = 0.px, offsetY = 12.px, blurRadius = 24.px)
            }
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
    }
}
