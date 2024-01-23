plugins {
    java
    kotlin("jvm") version "1.9.22"
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "dev.tykan"
            artifactId = "spring-batch-exposed"
            from(components["java"])
        }
    }
    
    repositories {
        maven {
        }
    }
}

allprojects {
    group = "dev.tykan"
    version = "1.0.20"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
}
