plugins {
    id("java")
}

group = "net.brian.atomcraft.api"

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly( "org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly ("org.projectlombok:lombok:1.18.28")
    annotationProcessor ("org.projectlombok:lombok:1.18.28")

    testCompileOnly( "org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.28")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}