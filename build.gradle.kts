plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    id("com.ncorti.ktfmt.gradle") version "0.17.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.8" apply false
}

subprojects {
    apply(plugin = "com.ncorti.ktfmt.gradle")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    configure<com.ncorti.ktfmt.gradle.KtfmtExtension> {
        kotlinLangStyle()
    }
    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        baseline = file("$rootDir/config/detekt/baseline.xml")
        autoCorrect = false
        source.setFrom("src")
    }
    dependencies {
        add("detektPlugins", "io.nlopez.compose.rules:detekt:0.4.22")
    }
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        reports {
            html.required.set(true)
            xml.required.set(true)
            sarif.required.set(true)
        }
        exclude("**/build/**", "**/generated/**")
    }
}

// Hook ktfmtCheck and detekt into the standard Gradle 'check' task
tasks.matching { it.name == "check" }.configureEach {
    dependsOn(subprojects.map { it.tasks.named("ktfmtCheck") })
    dependsOn(tasks.named("detektAll"))
}

tasks.register("detektAll") {
    group = "verification"
    description = "Aggregates all Detekt tasks for KMP targets and subprojects."

    allprojects {
        this@register.dependsOn(tasks.withType<io.gitlab.arturbosch.detekt.Detekt>())
    }
}

val detektBaselineCaptureTasks =
    listOf(
        ":shared:detektBaselineMetadataCommonMain" to "common",
        ":shared:detektBaselineMetadataIosMain" to "ios",
        ":shared:detektBaseline" to "aggregate",
        ":shared:detektBaselineAndroidHostTest" to "androidHostTest",
        ":androidApp:detektBaseline" to "androidApp",
    ).mapIndexed { index, (taskPath, name) ->
        tasks.register("captureDetektBaseline$name") {
            group = "verification"
            dependsOn(taskPath)
            if (index > 0) {
                val previousName = listOf("common", "ios", "aggregate", "androidHostTest", "androidApp")[index - 1]
                dependsOn("captureDetektBaseline$previousName")
            }
            doLast {
                val baselineDir = rootProject.layout.projectDirectory.dir("config/detekt/baselines").asFile
                baselineDir.mkdirs()
                rootProject.file("config/detekt/baseline.xml")
                    .copyTo(baselineDir.resolve("$name.xml"), overwrite = true)
            }
        }
    }

tasks.register("detektBaselineAll") {
    group = "verification"
    description =
        "Regenerates the shared Detekt baseline by capturing findings from each module and merging them."

    dependsOn(detektBaselineCaptureTasks)
    doLast {
        val baselineDir = rootProject.layout.projectDirectory.dir("config/detekt/baselines").asFile
        val mergedIds = linkedSetOf<String>()
        baselineDir.listFiles { file -> file.extension == "xml" }?.sorted()?.forEach { file ->
            file.readLines().forEach { line ->
                val trimmed = line.trim()
                if (trimmed.startsWith("<ID>") && trimmed.endsWith("</ID>")) {
                    mergedIds.add(trimmed)
                }
            }
        }
        val mergedBaseline =
            buildString {
                appendLine("""<?xml version="1.0" ?>""")
                appendLine("<SmellBaseline>")
                appendLine("  <ManuallySuppressedIssues/>")
                appendLine("  <CurrentIssues>")
                mergedIds.sorted().forEach { appendLine("    $it") }
                appendLine("  </CurrentIssues>")
                appendLine("</SmellBaseline>")
            }
        rootProject.file("config/detekt/baseline.xml").writeText(mergedBaseline)
    }
}
