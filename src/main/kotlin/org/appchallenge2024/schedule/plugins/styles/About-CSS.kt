package org.appchallenge2024.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.RadialGradientBuilder
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.Time
import kotlinx.css.properties.boxShadow
import org.appchallenge2024.schedule.respondCss

suspend fun PipelineContext<Unit, ApplicationCall>.cssAbout() {
    call.respondCss {
        rule(".poppinsfont") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
        }
        rule(".monospace") {
            fontFamily = "Google Sans Code, sans-serif"
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
            fontWeight = FontWeight.normal
        }
        rule(".flex") {
            display = Display.flex
        }
        rule(".flex2") {
            display = Display.flex
        }
        h2 {
            fontSize = LinearDimension("35px")
        }
        rule (".landingpage-background-dark") {
            background = "#1A1B27"
            justifyContent = JustifyContent.center
            alignItems = Align.center
            display = Display.flex
        }
        rule(".schedwiz-header") {
            color = Color.white
            marginLeft = LinearDimension("240px")
        }
        rule(".nodec") {
            textDecoration = TextDecoration.none
            color = Color.white
            fontWeight = FontWeight.normal
        }
        rule("exstudents") {
            fontSize = LinearDimension("12px")
        }
        rule(".topbar-buttons") {
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("480px")
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
        rule(".grid") {
            width = LinearDimension("1250px")
            marginTop = LinearDimension("15%")
            marginLeft = LinearDimension("0")
            display = Display.grid
            gridTemplateColumns = GridTemplateColumns("15% 50% 15%")
            gap = LinearDimension("30px")
            color = Color.white
            textAlign = TextAlign.left
        }

        rule(".gridcontainer") {
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center
        }
        rule(".grid2") {
            width = LinearDimension("1250px")
            marginTop = LinearDimension("15%")
            display = Display.grid
            gridTemplateColumns = GridTemplateColumns("30% 30% 30%")
            gap = LinearDimension("30px")
            color = Color.white
            textAlign = TextAlign.left
        }
        rule(".col1font") {
            fontSize = LinearDimension("30px")
            textAlign = TextAlign.center
        }
        rule(".col2font") {
            fontSize = LinearDimension("20px")
        }
        rule(".col2font2") {
            fontSize = LinearDimension("20px")
        }
        rule(".abouticons-dark") {
            color = Color.white

        }
        rule(".topiconnegmargin") {
            marginTop = LinearDimension("-5%")
        }
        img {
            width = LinearDimension("100px")
            height = LinearDimension("auto")
        }
        rule(".center") {
            alignItems = Align.center
        }
    }
}