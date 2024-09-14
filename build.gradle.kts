/**
 * + publish
 * 1. gradle publish
 * 2. https://oss.sonatype.org/
 * 3. Staging Repositories
 * 4. Close -> Release
 *
 * + publish setting
 * 1. create gpg
 * 2. set gradle.properties
 *    - ex windows path) C:/Users/<USER_NAME>/.gradle/gradle.properties
 *    sonatype.username=<username>
 *    sonatype.password=<password>
 *    signing.keyId=<last 8/16 chars in key>
 *    signing.password=<secret>
 *    signing.secretKeyRingFile=<path of secring.gpg>
 *

 *
 * @See
 * https://github.com/saro-lab/jwt
 * https://docs.gradle.org/current/userguide/publishing_maven.html
 * https://docs.gradle.org/current/userguide/signing_plugin.html#signing_plugin
 * windows -> gpg4win
 * gpg --gen-key
 * gpg --list-keys --keyid-format short
 * gpg --export-secret-keys -o secring.gpg
 * gpg --keyserver keys.gnupg.net --send-keys <keyid 8>
 *
 * you must use "User Token" instead of id & password.
 *     - https://oss.sonatype.org -> profile -> User Token
 */

plugins {
	val kotlinVersion = "2.0.0"
	id("org.jetbrains.kotlin.jvm") version kotlinVersion
	signing
	`maven-publish`
}

val kitGroupId = "me.saro"
val kitArtifactId = "kit"
val kitVersion = "0.2.1"

repositories {
	mavenCentral()
}


dependencies {
	// test
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

publishing {
	publications {
		create<MavenPublication>("maven") {

			groupId = kitGroupId
			artifactId = kitArtifactId
			version = kitVersion

			from(components["java"])

			repositories {
				maven {
					credentials {
						try {
							username = project.property("sonatype.username").toString()
							password = project.property("sonatype.password").toString()
						} catch (e: Exception) {
							println("warn: " + e.message)
						}
					}
					val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
					val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
					url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
				}
			}

			pom {
				name.set("SARO KIT")
				description.set("SARO KIT")
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

java {
	withJavadocJar()
	withSourcesJar()
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(11))
	}
}


configure<JavaPluginExtension> {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<Test> {
	useJUnitPlatform()
}
