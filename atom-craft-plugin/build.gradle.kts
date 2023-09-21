plugins {
    id("java")
}

group = "net.brian.atomcraft"
version = "unspecified"

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven( url = "https://repo.papermc.io/repository/maven-public/" )
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":atom-craft-api"))

    implementation ("com.google.code.gson:gson:2.10.1")
    compileOnly( "org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly ("org.projectlombok:lombok:1.18.28")
    annotationProcessor ("org.projectlombok:lombok:1.18.28")

    testCompileOnly( "org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.28")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation ("com.github.seeseemelk:MockBukkit-v1.20:3.9.0")
}

tasks.test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}