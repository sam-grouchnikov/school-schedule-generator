plugins {
    kotlin("jvm") version "1.8.0"
    id("app.cash.sqldelight") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}