plugins {
    java
    kotlin("jvm") version "1.9.22"
    id("maven-publish")
}

allprojects {
    group = "dev.tykan"
    version = "1.0.18"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    
    publishing{
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group as String
                artifactId = project.name
                version = project.version as String
                
                from(components["java"])
            }
        }
    }
}
