plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    id("com.ncorti.ktfmt.gradle") version "0.17.0" apply false
}

subprojects {
    apply(plugin = "com.ncorti.ktfmt.gradle")
    configure<com.ncorti.ktfmt.gradle.KtfmtExtension> {
        kotlinLangStyle() 
    }
}

// Hook ktfmtCheck into the standard Gradle 'check' task
tasks.matching { it.name == "check" }.configureEach {
    dependsOn(subprojects.map { it.tasks.named("ktfmtCheck") })
}

