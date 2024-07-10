package org.appchallenge2024.schedule.plugins.styles

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.properties.LinearGradientBuilder
import kotlinx.css.properties.RadialGradientBuilder
import kotlinx.css.properties.Time
import kotlinx.css.properties.boxShadow
import org.appchallenge2024.schedule.respondCss

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
        h1 {
            fontSize = LinearDimension("55px")
        }
        h2 {
            fontSize = LinearDimension("35px")
        }
        rule(".lptextbox-dark") {
            fontSize = LinearDimension("20px")
        }
        rule(".topbar-dark") {
            background = "#2d2d2d"
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
            justifyContent = JustifyContent.spaceBetween
        }

        rule(".schedwiz-header-dark") {
            marginLeft = LinearDimension("4%")
            color = Color.white
        }
        rule(".textbox-container-steps") {
            display = Display.flex
            marginTop = LinearDimension("13%")
            justifyContent = JustifyContent.spaceEvenly
            marginLeft = LinearDimension("0.5%")
        }

        rule(".steps-navigator-container") {
            textAlign = TextAlign.center
            alignItems = Align.center
            padding = "2%"
            display = Display.flex
            width = LinearDimension("-webkit-fill-available")
            color = Color.white
            fontSize = LinearDimension("30px")
            justifyContent = JustifyContent.center
        }
        rule(".steps-navigator-button") {
//            fontSize = LinearDimension("25px")
//            borderRadius = LinearDimension(("15px"))
//            alignItems = Align.center
//            fontWeight = FontWeight.bold
//            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
//            color = Color.white
//            hover {
//                transitionDuration = Time("0.3s")
//                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
//                    blurRadius = LinearDimension("8px"), spreadRadius = LinearDimension("8px"))}
//            }
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
                boxShadow = boxShadow.apply {
                    boxShadow(
                        color = Color.blue, offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                        blurRadius = LinearDimension("8px"), spreadRadius = LinearDimension("8px")
                    )
                }
            }
        }
        rule(".steps-fontsize") {
            fontSize = LinearDimension("20px")
        }

        rule(".steps-background-dark") {
            background = "#000000"
            val builder3 = RadialGradientBuilder()
            builder3.colorStop(Color.midnightBlue)
            builder3.colorStop(Color.black)
            background = builder3.build(false).value
        }
        rule(".textbox-step1-1") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            width = LinearDimension("30%")
            color = Color.white
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
//            border = "10px solid"
            borderRadius = LinearDimension("20px")
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
            }
        }
        rule(".textbox-step1-1-center") {
            textAlign = TextAlign.center
            marginRight = LinearDimension("10px")
            padding = "10px"
            width = LinearDimension("40%")
            color = Color.white
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
//            border = "10px solid"
            borderRadius = LinearDimension("20px")
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
            }
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
            marginRight = LinearDimension("10px")
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
            padding = "10px"
            width = LinearDimension("30%")
            color = Color.white
            borderRadius = LinearDimension("20px")
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
            }
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
        rule(".steps-textarea") {
            backgroundColor = Color.aqua
            borderRadius = LinearDimension("15px")
        }
        rule(".steps-td-th") {
            backgroundColor = Color("rgba(25, 25, 112, 0.5)")
            borderRadius = LinearDimension("10px")
            textAlign = TextAlign.left
            padding = "0.2%"
            color = Color.white
            hover {
                transitionDuration = Time("0.3s")
                border = "5px rgb(25, 25, 112)"
                boxShadow = boxShadow.apply { boxShadow(color = Color("#191970"), offsetX = LinearDimension("0px"), offsetY = LinearDimension("0px"),
                    blurRadius = LinearDimension("15px"), spreadRadius = LinearDimension("15px"))}
                padding = "0.5%"
            }
        }
        rule(".steps-table") {
            width = LinearDimension("100%")
            fontSize = LinearDimension("20px")
            fontWeight = FontWeight.bold
        }
        rule(".font-25px") {
            fontSize = LinearDimension("25px")
        }
    }
}