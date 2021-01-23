import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.4.20"
	kotlin("jvm") version kotlinVersion
	kotlin("kapt") version kotlinVersion
}

allprojects {
	group = "me.saro"
	version = "0.1.0"

	repositories {
		mavenCentral()
	}
}

dependencies {
	// koltin
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// test
	testImplementation("org.junit.jupiter:junit-jupiter-engine:+")
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