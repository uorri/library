plugins {
    kotlin("jvm") version "1.8.21"
}

group = "org.uorri"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.uorri.app.Application"
    }
}

kotlin {
    jvmToolchain(8)
}