plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
    id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

javafx {
    version = "21.0.7"
    modules("javafx.controls", "javafx.fxml", "javafx.web", "javafx.media")
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.2")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("org.glassfish:jakarta.el:4.0.2")
    implementation(project(":common"))
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "org.exampleMirax.Main"
    }
    archiveClassifier.set("all")
}


tasks.build {
    dependsOn(tasks.shadowJar)
}