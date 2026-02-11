package org.appchallenge2025.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import org.appchallenge2025.schedule.respondCss

suspend fun PipelineContext<Unit, ApplicationCall>.cssAbout() {
    call.respondCss {
        body {
            margin = "0"
        }

        rule(".poppinsfont") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
        }

        rule(".textalignleft") {
            textAlign = TextAlign.left
        }

        h1 {
            fontSize = LinearDimension("42px")
            fontWeight = FontWeight.w600
        }

        rule(".landingpage-background-dark") {
            minHeight = LinearDimension("100vh")
            background = "radial-gradient(circle at 20% 20%, #2B3A67 0%, #1A1B27 45%, #111320 100%)"
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

        rule(".grid") {
            width = LinearDimension("min(1120px, 90vw)")
            margin = "0 auto"
            paddingTop = LinearDimension("170px")
            paddingBottom = LinearDimension("50px")
            display = Display.grid
            gridTemplateColumns = GridTemplateColumns("180px 1fr 120px")
            gap = Gap("28px")
            color = Color.white
            textAlign = TextAlign.left
            alignItems = Align.center
        }

        rule(".col1font") {
            fontSize = LinearDimension("34px")
            fontWeight = FontWeight.w600
            color = Color("#FFFFFF")
        }

        rule(".col2font") {
            fontSize = LinearDimension("20px")
            lineHeight = kotlinx.css.properties.LineHeight("1.8")
            color = Color("#D4DEFF")
            padding = "22px 24px"
            borderRadius = LinearDimension("14px")
            backgroundColor = Color("rgba(27, 34, 58, 0.72)")
            border = "1px solid rgba(255, 255, 255, 0.08)"
        }

        rule(".abouticons-dark") {
            justifySelf = JustifyContent.center
        }

        rule(".topiconnegmargin") {
            marginTop = LinearDimension("0")
        }

        img {
            width = LinearDimension("88px")
            height = LinearDimension("auto")
            filter = "drop-shadow(0 12px 20px rgba(0, 0, 0, 0.35))"
        }
    }
}
