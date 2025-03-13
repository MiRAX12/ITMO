plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.10")
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "org.example.utility.Main"
    }
    archiveClassifier.set("all")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
