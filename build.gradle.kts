plugins {
    kotlin("jvm") version "1.8.0"
    id("app.cash.sqldelight") version "2.0.0"
    application
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
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.15.2")
        implementation("org.wikidata.wdtk:wdtk-datamodel:0.8.0")
        implementation("org.wikidata.wdtk:wdtk-dumpfiles:0.8.0")
        implementation("org.wikidata.wdtk:wdtk-rdf:0.8.0")
        implementation("org.wikidata.wdtk:wdtk-wikibaseapi:0.8.0")
        implementation("com.google.ortools:ortools-java:9.10.4067")
        implementation("io.ktor:ktor-server-core:$ktor_version")
            implementation("io.ktor:ktor-server-netty:$ktor_version")
            implementation("ch.qos.logback:logback-classic:$logback_version")
            implementation("io.ktor:ktor-server-html-builder:$ktor_version")
            implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.473")
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