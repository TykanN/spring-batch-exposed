plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("maven-publish")
}

dependencies{
    implementation(project(":spring-batch-exposed-reader"))
}

allprojects {
    group = "dev.tykan"
    version = "1.0.17"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
}
