plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

tasks.withType<JavaCompile>().configureEach {
    options.isFork = true
    options.forkOptions.memoryInitialSize = "512m"
    options.forkOptions.memoryMaximumSize = "4096m"
}

subprojects {
    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:unchecked")
    }
}
