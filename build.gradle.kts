@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import org.jetbrains.kotlin.gradle.targets.js.ir.*

plugins {
    kotlin("multiplatform") version "2.5.0-Beta2"
}

repositories {
    mavenCentral()
}

kotlin {
    wasmJs {
        nodejs()
        binaries.executable()
    }
    
    sourceSets {
        wasmJsMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-browser:0.5.0")
            }
        }
    }
}
