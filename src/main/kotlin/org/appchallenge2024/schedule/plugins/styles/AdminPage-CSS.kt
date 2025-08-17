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
            fontWeight = FontWeight.normal
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
        rule(".font45") {
            fontSize = LinearDimension("40px")
            padding = "10px"
        }
        rule(".font20") {
            fontSize = LinearDimension("20px")
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
            marginLeft = LinearDimension("240px")
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

        rule(".adminpage-td-th") {
            borderRadius = LinearDimension("10px")
            textAlign = TextAlign.left
            padding = "0.2%"
            color = Color.white
            paddingTop = LinearDimension("1%")
            paddingBottom = LinearDimension("1%")
            fontSize = LinearDimension("14px")
//            hover {
//                transitionDuration = Time("0.3s")
//                border = "5px 1A1B27"
//                boxShadow = boxShadow.apply { boxShadow(color = Color("#1A1B27"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
//                    blurRadius = LinearDimension("5px"), spreadRadius = LinearDimension("5px"))}
//                padding = "0.5%"
//            }
        }
        rule(".row") {
            display = Display.grid
            hover {
                transitionDuration = Time("0.3s")
                border = "5px 1A1B27"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#1A1B27"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("5px"), spreadRadius = LinearDimension("5px"))}
                padding = "0.5%"
            }
        }
        rule(".topvertalign") {
            verticalAlign = VerticalAlign.top
        }
        tr {
            nthChild("even") {
                backgroundColor = Color("#353540")
            }
//            nthChild("odd") {
//                backgroundColor = Color("#323242")
//            }
            paddingTop = LinearDimension("2%")
        }
        rule(".expandedHighlight") {
            backgroundColor = Color("#353540")

        }
        rule(".vertcenter") {
            verticalAlign = VerticalAlign.middle
        }
        rule(".schedwiz-header-dark") {
            marginLeft = LinearDimension("4%")
            color = Color.white
            textDecoration = TextDecoration.none

        }
        rule(".nodec") {
            textDecoration = TextDecoration.none
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
        }
        rule(".bodymargin") {
            marginLeft = LinearDimension("25px")
        }
        rule(".bold2") {
            fontWeight = FontWeight.bold
            paddingRight = LinearDimension("2%")
        }
        rule(".flex-outer-2") {
            display = Display.flex
            justifyContent = JustifyContent.center
            marginTop = LinearDimension("2%")
        }
        rule(".extrapadding") {
            paddingLeft = LinearDimension("14px")
        }
        rule(".extrapadding2") {
            paddingLeft = LinearDimension("18px")
        }
        rule(".extrapadding3") {
            paddingLeft = LinearDimension("60px")
        }

        rule(".table-container") {
            color = Color.white
            width = LinearDimension("1100px")
            height = LinearDimension("100%")
            borderRadius = LinearDimension("15px")
            justifyContent = JustifyContent.center
            backgroundColor = Color("#2F2F3D")
            display = Display.flex
            justifyContent = JustifyContent.spaceAround

            padding = "2%"
        }
        rule(".textbox-step1-3") {
            marginRight = LinearDimension("1px")
            backgroundColor = Color("#2F2F3D")
            padding = "10px"
            width = LinearDimension("450px")
            color = Color.white
            height = LinearDimension("fit-content")
            borderRadius = LinearDimension("10px")
            paddingLeft = LinearDimension("20px")
            paddingRight = LinearDimension("20px")
            paddingBottom = LinearDimension("60px")
            alignItems = Align.center
            textAlign = TextAlign.center
        }
        rule(".sp-table") {
            width = LinearDimension("-webkit-fill-available")
            textAlign = TextAlign.center
            borderCollapse = BorderCollapse.collapse
        }
        
//        th {
//            borderBottom = "1px solid black"
//
//        }
        rule(".sp-table-grid") {
            display = Display.grid
            width = LinearDimension("100%")
            gap = LinearDimension("0")
            rowGap = LinearDimension("10px")
            gridTemplateColumns = GridTemplateColumns("220px 220px 120px 120px 400px")
        }
        rule(".sp-table-grid2") {
            display = Display.grid
            width = LinearDimension("100%")
            gap = LinearDimension("10px")
            gridTemplateColumns = GridTemplateColumns("10% 15% 75%")
        }

        rule(".sp-cell") {
            display = Display.flex
            flexWrap = FlexWrap.wrap
            border = "none"
            gap = LinearDimension("10px")
            alignItems = Align.start
            padding = "7px"
        }
        rule(".sp-row") {
            display = Display.contents
            borderRadius = LinearDimension("10px")
        }

        rule(".header") {
            fontWeight = FontWeight.bold
            fontSize = LinearDimension("22px")
        }

        rule(".students-column") {
            // Add any special styling for the students column
            minWidth = LinearDimension("200px") // Example: wider column
        }

        rule(".course-info") {
            // Optional styling for course info blocks inside the courses cell
            display = Display.flex
            flexDirection = FlexDirection.column
            backgroundColor = Color("#3A3A4F")
            borderRadius = LinearDimension("8px")
            padding = "8px"
            minWidth = LinearDimension("150px")        }

        rule(".flex2") {
            display = Display.flex
            alignItems = Align.center
        }

        rule(".white") {
            color = Color.white
        }

        rule(".white") {
            color = Color.white
        }
        rule(".adminpage-button") {
            border = "none"
            paddingLeft = LinearDimension("50px")
            paddingRight = LinearDimension("50px")
            paddingTop = LinearDimension("10px")
            paddingBottom = LinearDimension("10px")
            backgroundColor = Color("#3C3E4C")
            borderRadius = LinearDimension("15px")
            fontSize = LinearDimension("20px")
            color = Color.white
            cursor = Cursor.pointer
            hover {
                backgroundColor = Color("#383A47")
            }
        }
        rule(".adminpage-button2") {
            border = "none"
            paddingLeft = LinearDimension("50px")
            paddingRight = LinearDimension("50px")
            paddingTop = LinearDimension("10px")
            paddingBottom = LinearDimension("10px")
            backgroundColor = Color("#AA00FF")
            borderRadius = LinearDimension("15px")
            fontSize = LinearDimension("23px")
            color = Color.white
            cursor = Cursor.pointer
            hover {
                backgroundColor = Color("#9100D9")
            }
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