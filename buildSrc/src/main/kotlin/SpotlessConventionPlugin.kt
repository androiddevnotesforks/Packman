import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

class SpotlessConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(SpotlessPlugin::class.java)
            extensions.getByType(SpotlessExtension::class.java).apply {
                format("misc") {
                    target(
                        fileTree(
                            mapOf(
                                "dir" to ".",
                                "include" to listOf("**/*.md", "**/.gitignore", "**/*.yaml", "**/*.yml", "**/*.toml", "**/*.properties", "**/*.pro"),
                                "exclude" to listOf(
                                    "**/.gradle/**",
                                    "**/.gradle-cache/**",
                                    "**/tools/**",
                                    "**/build/**"
                                )
                            )
                        )
                    )
                    trimTrailingWhitespace()
                    endWithNewline()
                }

                format("xml") {
                    target("**/res/**/*.xml")
                    targetExclude("**/build/**")
                    trimTrailingWhitespace()
                    endWithNewline()
                }

                kotlin {
                    target("**/*.kt")
                    ktlint()
                        .editorConfigOverride(
                            mapOf(
                                "disabled_rules" to "no-wildcard-imports,no-unused-imports,no-blank-line-before-rbrace",
                                "android" to "true"
                            )
                        )
                    trimTrailingWhitespace()
                    endWithNewline()
                }

                kotlinGradle {
                    target("**/*.kts")
                    ktlint()
                        .editorConfigOverride(
                            mapOf(
                                "disabled_rules" to "no-wildcard-imports,no-unused-imports,no-blank-line-before-rbrace",
                                "android" to "true"
                            )
                        )
                    trimTrailingWhitespace()
                    endWithNewline()
                }
            }
        }
    }

}