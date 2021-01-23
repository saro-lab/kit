import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * https://docs.gradle.org/current/userguide/publishing_maven.html
 * https://docs.gradle.org/current/userguide/signing_plugin.html#signing_plugin
 *
 * C:/Users/<USER_NAME>/.gradle/gradle.properties
 */

plugins {
	val kotlinVersion = "1.4.20"
	kotlin("jvm") version kotlinVersion
	kotlin("kapt") version kotlinVersion
	java
	signing
	`java-library`
	`maven-publish`
}

repositories {
	mavenCentral()
}

java {
	withJavadocJar()
	withSourcesJar()
}

dependencies {
	// koltin
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// test
	testImplementation("org.junit.jupiter:junit-jupiter-engine:+")
}

publishing {
	publications {
		create<MavenPublication>("maven") {

			groupId = "me.saro"
			artifactId = "kit"
			version = "0.1.1"

			from(components["java"])

			repositories {
				maven {
					credentials {
						username = project.property("SONATYPE_NEXUS_USERNAME").toString()
						password = project.property("SONATYPE_NEXUS_PASSWORD").toString()
					}
					val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
					val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
					url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
				}
			}

			pom {
				name.set("SARO KIT")
				url.set("https://saro.me")

				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						name.set("PARK Yong Seo")
						email.set("j@saro.me")
					}
				}
				scm {
					connection.set("scm:git:git://github.com/saro-lab/kit.git")
					developerConnection.set("scm:git:git@github.com:saro-lab/kit.git")
					url.set("https://github.com/saro-lab/kit")
				}
			}
		}
	}
}

signing {
	sign(publishing.publications["maven"])
}

tasks.withType<Javadoc>().configureEach {
	options {
		this as StandardJavadocDocletOptions
		addBooleanOption("Xdoclint:none", true)
	}
}

configure<JavaPluginExtension> {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
