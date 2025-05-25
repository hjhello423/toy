plugins {
    id("org.springframework.boot") version "3.4.6" // spring boot 플러그인
    id("io.spring.dependency-management") version "1.1.7" // spring 의존성 관리 플러그인
    kotlin("jvm") version "1.9.25" // Kotlin 코드를 JVM 바이트코드로 컴파일하기 위한 플러그인
    kotlin("plugin.spring") version "1.9.25" // spring 플러그인 (Kotlin의 No-arg, All-open 등을 위해)
    kotlin("plugin.jpa") version "1.9.25" // JPA 플러그인 (Kotlin의 All-open 등을 위해)
}

group = "org.example"
version = "0.0.1"

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17 // 소스 코드를 JDK 17에 맞춰 컴파일하도록
}

repositories {
    mavenCentral() // Maven Central 리포지토리 사용 (의존성 다운로드)
}

dependencies {
    // 2. Spring Boot Starter 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // Spring Data JPA
    implementation("org.springframework.boot:spring-boot-starter-web") // Spring Web

    // 3. Kotlin 관련 의존성
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // Kotlin 직렬화/역직렬화 지원
    implementation("org.jetbrains.kotlin:kotlin-reflect") // Kotlin 리플렉션 지원

    // 4. 데이터베이스 드라이버 및 개발 도구
    runtimeOnly("com.h2database:h2") // H2 데이터베이스 드라이버 (런타임에만 필요)
    developmentOnly("org.springframework.boot:spring-boot-devtools") // 개발 도구 (개발 환경에서만 필요) // TODO 어떻게 동작하는지 체크해보기

    // 5. 테스트 의존성
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") // Spring Boot 테스트 기본 의존성
    testImplementation("org.springframework.boot:spring-boot-testcontainers") // Testcontainers 연동
    testImplementation("org.testcontainers:junit-jupiter") // Testcontainers JUnit 5 지원
    testImplementation("io.mockk:mockk:1.13.11") // MockK
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") // JUnit 5 테스트 런처
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform() // JUnit 5 사용 설정
}
