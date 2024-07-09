package org.appchallenge2024.schedule.plugins

import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.css.*
import kotlinx.css.Float
import org.appchallenge2024.schedule.respondCss
import javax.sound.sampled.Line
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.css.properties.*
import kotlinx.html.*
import org.w3c.dom.Text
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.util.pipeline.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.link
import org.appchallenge2024.schedule.sqldelight.data.Database
import kotlinx.html.*

public suspend fun PipelineContext<Unit, ApplicationCall>.css() {
    call.respondCss {

        // fonts and backgrounds
        rule(".poppinsfont") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
        }
        rule(".tealaquagradient") {
            val builder = LinearGradientBuilder()
            builder.colorStop(Color.aqua)
            builder.colorStop(Color.cornflowerBlue)
            background = builder.build("to right bottom", false).value
            backgroundAttachment = BackgroundAttachment.fixed
            backgroundSize = "cover"
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

        // admin page

        rule(".adminpage-flex-container") {
            display = Display.flex
            marginTop = LinearDimension("14%")
            marginLeft = LinearDimension("1.5%")
        }
        rule(".adminpage-leftbox") {
            width = LinearDimension("65%")
            height = LinearDimension("100%")
            border = "10px solid #000000"
            borderRadius = LinearDimension("15px")
            alignItems = Align.center
            justifyContent = JustifyContent.center
        }
        rule (".adminpage-rightboxes") {
            display = Display.flex
            flexDirection = FlexDirection.column
            width = LinearDimension("30%")
            marginTop = LinearDimension("-0.45%")
            marginLeft = LinearDimension("2%")
        }
        rule(".adminpage-rightbox") {
            border = "10px solid #000000"
            borderRadius = LinearDimension("15px")
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            margin = "1%"
        }

        // landing page
        rule (".landingpage-background") {
            background = "#2d2d2d"
        }


        rule(".textbox-container-lp") {
            display = Display.flex
            marginTop = LinearDimension("13%")
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("0.5%")
        }
        rule(".textbox-lp") {
            marginRight = LinearDimension("10px")
            padding = "10px"

            width = LinearDimension("30%")
            val builder = LinearGradientBuilder()
            builder.colorStop(Color.darkOrange)
            builder.colorStop(Color.red)
            builder.colorStop(Color.purple)
//            background = builder.build("to right bottom", false).value
            color = Color.aquamarine
            transitionDuration = Time("0.3s")
            hover {
                border = "10px solid"
                borderRadius = LinearDimension("20px")
                borderColor = Color.cornflowerBlue
                width = LinearDimension("35%")

            }

        }

        // steps

        rule(".textbox-container-lp-step2") {
            display = Display.block
            justifyContent = JustifyContent.center
            marginLeft = LinearDimension("1.7%")
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

        // schedule

        rule(".textbox-container-sp") {
            display = Display.flex
            marginTop = LinearDimension("1%")
            marginLeft = LinearDimension("0.5%")
        }

        rule(".schedulepage-td-th") {
            border = "2px solid #000000"
            textAlign = TextAlign.left
            padding = "0.2%"
            hover {
                backgroundColor = Color.lightSeaGreen
            }
        }

        rule(".studentpage-flex-container") {
            marginTop = LinearDimension("13%")
            display = Display.flex
        }
        rule(".studentpage-leftbox") {
            width = LinearDimension("49%")
            height = LinearDimension("100%")
            border = "10px solid #000000"
            borderRadius = LinearDimension("15px")
            alignItems = Align.center
            justifyContent = JustifyContent.center
        }
        rule (".studentpage-rightboxes") {
            display = Display.flex
            flexDirection = FlexDirection.column
            width = LinearDimension("49%")
            marginTop = LinearDimension("-0.45%")
            marginLeft = LinearDimension("0.5%")
        }
        rule(".studentpage-rightbox") {
            border = "10px solid #000000"
            borderRadius = LinearDimension("15px")
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            margin = "1%"
        }

        rule(".sp-table") {
            width = LinearDimension("65%")
        }
        rule(".steps-table") {
            width = LinearDimension("100%")
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
            marginLeft = LinearDimension("14%")
            padding = "1%"
            display = Display.inlineBlock
            width = LinearDimension("20%")
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
            color = Color.white
        }


        rule("steps-button-fontsize") {
            fontSize = LinearDimension("30px")
        }
        rule(".steps-font") {
            fontFamily = "Poppins, sans-serif"
            fontStyle = FontStyle.normal
            fontSize = LinearDimension("20px")
        }
        rule(".flex") {
            display = Display.flex
        }

        //  landing page


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
                paddingLeft =LinearDimension("3%")
                paddingRight = LinearDimension("3%")
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.spaceBetween
            }

            rule (".schedwiz-header-dark") {
                marginLeft = LinearDimension("4%")
                color = Color.white
            }

            rule (".landingpage-background-dark") {
                background = "#000000"
                val builder3 = RadialGradientBuilder()
                builder3.colorStop(Color.midnightBlue)
                builder3.colorStop(Color.black)
                background = builder3.build(false).value
            }


            rule(".textbox-container-lp-dark") {
                display = Display.flex
                marginTop = LinearDimension("13%")
                justifyContent = JustifyContent.center
                marginLeft = LinearDimension("0.5%")
            }
            rule(".textbox-lp-dark") {
                marginRight = LinearDimension("10px")
                padding = "10px"
                color = Color.white
                width = LinearDimension("30%")
                val builder = LinearGradientBuilder()
                backgroundColor = Color.midnightBlue
                border = "10px solid"
                borderColor = Color.midnightBlue
                borderRadius = LinearDimension("15px")
                transitionDuration = Time("0.3s")
                hover {
                    val builder2 = RadialGradientBuilder()
                    builder2.colorStop(Color.magenta)
                    builder2.colorStop(Color.blue)
                    background = builder2.build(false).value
                    border = "20px solid rgba(255, 255, 255, 0.0)"
                }

            }
            rule (".lp-getstarted-container-dark") {
                display = Display.flex
                justifyContent = JustifyContent.center
                alignItems = Align.center
            }
            rule (".lp-getstarted-button-dark") {
                backgroundColor = Color.white
                color = Color.black
                fontSize = LinearDimension("20px")
                border = "10 px solid #ffffff"
                borderRadius = LinearDimension("15%")
                padding = "1%"
                hover {
                    backgroundColor = Color.gainsboro
                    padding = "2%"
                }

            }
            rule(".lp-modeswitch-dark") {
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
                }
            }
        rule(".lp-buttons-container") {
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.spaceEvenly
        }
        // sign in
        rule(".textbox-signin-dark") {
            marginRight = LinearDimension("10px")
            padding = "10px"
            color = Color.white
            width = LinearDimension("20%")
            backgroundColor = Color.midnightBlue
            border = "10px solid"
            borderColor = Color.midnightBlue
            borderRadius = LinearDimension("15px")
            transitionDuration = Time("0.3s")
            hover {
                val builder2 = RadialGradientBuilder()
                builder2.colorStop(Color.magenta)
                builder2.colorStop(Color.blue)
                background = builder2.build(false).value
                border = "20px solid rgba(255, 255, 255, 0.0)"
            }

        }
    }
}