plugins {
    `java-library`
}

group = "dev.dini"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

// Pure POJO event contracts shared across services — no Spring/Kafka dependencies
// on purpose, so every service can deserialize the same fully-qualified type.
