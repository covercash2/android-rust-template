import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.covercash.rust"
    compileSdk = 33

    defaultConfig {
        applicationId = "dev.covercash.rust"
        minSdk = 31
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val composeVersion = "1.3.1"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.0-alpha02")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
}

sealed class Abi {
    abstract val android: String
    abstract val rust: String

    data class ArmV7(
        override val android: String = "armeabi-v7a",
        override val rust: String = "armv7-linux-androideabi",
    ): Abi()

    data class ArmV8(
        override val android: String = "arm64-v8a",
        override val rust: String = "aarch64-linux-android",
    ): Abi()

    companion object {
        val All = listOf(ArmV7(), ArmV8())
    }
}

val rustProjectDir = "../rust"
val rustTargetDir = "$rustProjectDir/target"
val targetJniDir = "src/main/jniLibs"

Abi.All.forEach { abi ->
    val cargoBuildTask = "cargoBuild-${abi.rust}"
    task<Exec>(cargoBuildTask) {
        description = "build static object for ${abi.rust}"

        workingDir(rustProjectDir)
        commandLine("cargo", "build", "--release", "--target", abi.rust)
    }

    val copyRustLibsTask = "copyRustLibs-${abi.android}"
    val rustOutputDir = "$rustTargetDir/${abi.rust}/release"
    val targetAbiDir = "$targetJniDir/${abi.android}"

    task<Copy>(copyRustLibsTask) {
        description = "copy compiled Rust binaries to the Android project"

        dependsOn(cargoBuildTask)
        from(rustOutputDir)
        include("*.so")
        into(targetAbiDir)
    }

    tasks.withType<JavaCompile> {
        dependsOn(copyRustLibsTask)
    }

    val cleanJniLibsTask = "cleanJniLibs-${abi.android}"

    task<Delete>(cleanJniLibsTask) {
        delete(targetAbiDir)
    }

    tasks.named("clean").dependsOn(cleanJniLibsTask)
}

task<Exec>("cargoClean") {
    workingDir(rustProjectDir)
    commandLine("cargo", "clean")
}

tasks.named("clean").dependsOn("cargoClean")