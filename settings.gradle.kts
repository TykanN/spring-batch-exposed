plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "spring-batch-exposed"

include("spring-batch-exposed-reader")
include("spring-batch-exposed-integration-test")
