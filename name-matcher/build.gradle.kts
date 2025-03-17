plugins {
    java
    id("org.springframework.boot") version("3.4.3")
    id("io.spring.dependency-management") version("1.1.7")
}

group = "com.assignment"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.h2database:h2:2.3.232")
    implementation("org.apache.opennlp:opennlp-tools:2.5.3")
    implementation("org.apache.commons:commons-text:1.13.0")
    implementation("com.google.guava:guava:33.4.0-jre")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}
