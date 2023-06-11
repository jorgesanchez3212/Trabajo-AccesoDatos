import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //KMongo
    implementation("org.litote.kmongo:kmongo:4.7.1")
    implementation("org.litote.kmongo:kmongo-async:4.7.2")
    implementation("org.litote.kmongo:kmongo-coroutine:4.7.2")
    //Serializar
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // BCrypt
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")
    //Cache4K
    implementation("io.github.reactivecircus.cache4k:cache4k:0.9.0")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    implementation("ch.qos.logback:logback-classic:1.4.4")

    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}