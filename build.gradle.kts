plugins {
    kotlin("multiplatform") version "1.8.10"
    `convention-publication`
}

group = "com.vexdev"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    val nativeTarget = linuxX64("native")

    sourceSets {
        val nativeMain by getting
        val nativeTest by getting
    }
}
