plugins {
    id("java")
}

group = "net.brian.atomcraft.addons"
version = "unspecified"

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":atom-craft-api"))
    compileOnly( "org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}