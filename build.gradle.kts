plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Driver de SQLite
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")

    // Driver de PostgreSQL
    implementation("org.postgresql:postgresql:42.6.0")

    // Driver de MySQL (incluye compatibilidad con MariaDB)
    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks.test {
    useJUnitPlatform()
}