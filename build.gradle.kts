plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("maven-publish")
}

publishing {
    publications {
    }
}

allprojects{
    group = "dev.tykan"
    version = "1.0.6"
    
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "maven-publish")
    
    dependencies {
        val exposedVersion = "0.44.1"
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-batch")
        implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
        implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.batch:spring-batch-test")
    }

    tasks {
        jar {
            enabled = true
        }
        bootJar {
            enabled = false
        }
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
            dependsOn(processResources) // kotlin 에서 ConfigurationProperties
        }
        
        
        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }
    }
    
   
}

project("spring-batch-exposed-reader") {
 
    
    dependencies {
    
    }
}
