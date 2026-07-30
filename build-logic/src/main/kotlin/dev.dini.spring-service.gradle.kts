// Shared configuration for every Spring Boot service in this repo.
// Apply with: plugins { id("dev.dini.spring-service") }
// Services then declare only their own unique dependencies.

plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "dev.dini"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.1")
    }
}

dependencies {
    "compileOnly"("org.projectlombok:lombok")
    "annotationProcessor"("org.projectlombok:lombok")
    "implementation"("org.mapstruct:mapstruct:1.6.3")
    "annotationProcessor"("org.mapstruct:mapstruct-processor:1.6.3")
    "runtimeOnly"("org.postgresql:postgresql")
    "testImplementation"("org.springframework.boot:spring-boot-starter-test")
    "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
