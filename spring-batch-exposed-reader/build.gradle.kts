import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.9.22"
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("maven-publish")
}

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

dependencyManagement {
	imports {
		mavenBom(SpringBootPlugin.BOM_COORDINATES)
	}
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

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			groupId = project.group as String
			artifactId = project.name
			version = project.version as String
			from(components["java"])
			
			versionMapping {
				usage("java-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
		}
	}

//        repositories {
//            maven {
//                // Nexus 관련 정보
//            }
//        }
}
