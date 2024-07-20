plugins {
    kotlin("jvm") version "2.0.0"
    id("app.cash.sqldelight") version "2.0.0"
    application
    id("io.ktor.plugin") version "2.3.12"
}

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("org.appchallenge2024.schedule.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("org.appchallenge2024.schedule.sqldelight.data")
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.473")
    testImplementation("junit:junit:4.13.2")

}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}