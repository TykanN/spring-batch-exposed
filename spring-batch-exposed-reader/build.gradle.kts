import org.springframework.boot.gradle.plugin.SpringBootPlugin

val exposedVersion = "0.46.0"

plugins {
	id("org.springframework.boot") version "3.2.0" apply false
	id("io.spring.dependency-management") version "1.1.4"
}

dependencies {
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
