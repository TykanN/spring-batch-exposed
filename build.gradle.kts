plugins {
    java
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("kapt") version "1.9.22"
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "dev.tykan"
            artifactId = "spring-batch-exposed"
            version = project.version as String
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
    version = "1.1.0"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "maven-publish")
}
