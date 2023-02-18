plugins {
    kotlin("multiplatform") version "1.8.10"
    id("maven-publish")
}

group = "org.vexdev"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    linuxArm64("native") {
        binaries {
            executable()
        }
    }
}

sourceSets.forEach { print("AAA") }

tasks.withType<Wrapper> {
    gradleVersion = "8.0"
    distributionType = Wrapper.DistributionType.BIN
}