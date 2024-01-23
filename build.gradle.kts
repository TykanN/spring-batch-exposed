plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("maven-publish")
}

allprojects {
    group = "dev.tykan"
    version = "1.0.15"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    
    publishing{
        publications {
            create<MavenPublication>("mavenJava") {
                groupId = project.group as String
                artifactId = project.name
                version = project.version as String
                from(components["java"])
            }
        }
    }
}
