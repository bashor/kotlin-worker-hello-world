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

        // Workaround for https://youtrack.jetbrains.com/issue/KT-66972
        compilations
            .configureEach {
                binaries.withType<Executable>().configureEach {
                        linkTask.configure {
                            val moduleName = linkTask.flatMap {
                                it.compilerOptions.moduleName
                            }
                            val fileName = moduleName.map { module ->
                                "$module.mjs"
                            }

                            val mjsFile: Provider<RegularFile> = linkTask.flatMap {
                                it.destinationDirectory.file(fileName.get())
                            }

                            doLast {
                                val module = moduleName.get()
                                val file = mjsFile.get().asFile
                                val text = file.readText()
                                val newText = text
                                    .replace("if \\(!\\w+( && !\\w+)*\\) \\{[^\\}]*\\}".toRegex(), "")
                                    .replace(
                                    "(if \\(isBrowser\\) \\{\\s*wasmInstance[^\\}]*\\})".toRegex(),
                                    """
                                   |
                                   |  const isWorker = navigator.userAgent.includes("Workers")
                                   |  if (isWorker) {
                                   |      const { default: wasmModule } = await import('./$module.wasm');
                                   |      wasmInstance = (await WebAssembly.instantiate(wasmModule, importObject, wasmOptions));
                                   |  }
                                   |  """.trimMargin()
                                    )

                                file.writeText(newText)
                            }
                        }
                    }
            }
    }
    
    sourceSets {
        wasmJsMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-browser:0.5.0")
            }
        }
    }
}
