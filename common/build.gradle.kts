plugins {
    kotlin("jvm") version "1.8.21"
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.8.21"
    id("nu.studer.jooq") version "8.2"
}

group = "org.uorri"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    jooqGenerator("org.jooq:jooq-meta-extensions")
    api("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    runtimeOnly("org.postgresql:postgresql")
    api("org.jooq:jooq")
    api("org.jooq:jooq-meta")
    api("org.jooq:jooq-codegen")
    implementation("org.liquibase:liquibase-core")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}


jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                generator.apply {

                    //for more configuration options see https://www.jooq.org/doc/latest/manual/code-generation/codegen-ddl/
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                        properties.add(org.jooq.meta.jaxb.Property().withKey("scripts").withValue("src/main/resources/db/changelog/*.sql"))
                        properties.add(org.jooq.meta.jaxb.Property().withKey("sort").withValue("alphanumeric"))
                        properties.add(org.jooq.meta.jaxb.Property().withKey("defaultNameCase").withValue("lower")) //for PostgreSQL
                    }

                    generate.apply {
                        isFluentSetters = false
                    }

                    target.apply {
                        packageName = "com.uorri.common.db"
                    }
                }
            }
        }
    }
}