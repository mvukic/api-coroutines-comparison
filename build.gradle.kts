import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    application
}

group = "me.matija"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk9:1.4.2")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}