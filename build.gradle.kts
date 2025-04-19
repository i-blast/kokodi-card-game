
val kotlin_version: String by project
val ktor_version: String by project
val logback_version: String by project

val sqlite_version: String by project
val exposed_version: String by project

val mockk_version: String by project
val kotest_version: String by project

plugins {
    kotlin("jvm") version "2.1.20"
    id("io.ktor.plugin") version "3.1.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20"
}

group = "io.pii.game"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {

    // Ktor core
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-content-negotiation")

    // Auth
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")

    // DB
    implementation("org.xerial:sqlite-jdbc:$sqlite_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Tests
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:${kotlin_version}")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
