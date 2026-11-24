package io.github.krawist.detekt

import io.gitlab.arturbosch.detekt.test.lint
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class HardcodedUiTextTest {
    @Nested
    inner class VisitCallExpression {
        @ParameterizedTest
        @ValueSource(strings = ["text", "title", "subtitle", "contentDescription", "placeholder", "message", "label"])
        fun `should report a literal passed as a text parameter`(parameter: String) {
            // given
            val code = """
                fun screen() {
                    Thing($parameter = "Nouvelle chanson")
                }
            """.trimIndent()

            // when
            val findings = HardcodedUiText().lint(code)

            // then
            findings shouldHaveSize 1
        }

        @Test
        fun `should report a literal passed first to Text`() {
            // given
            val code = """
                fun screen() {
                    Text("Tes chansons")
                }
            """.trimIndent()

            // when
            val findings = HardcodedUiText().lint(code)

            // then
            findings shouldHaveSize 1
        }

        @Test
        fun `should report a template holding text`() {
            // given
            val code = """
                fun screen(count: Int) {
                    Text(text = "${'$'}count syllabes")
                }
            """.trimIndent()

            // when
            val findings = HardcodedUiText().lint(code)

            // then
            findings shouldHaveSize 1
        }

        @Test
        fun `should accept a string resource`() {
            // given
            val code = """
                fun screen() {
                    Text(text = stringResource(R.string.library_title))
                }
            """.trimIndent()

            // when
            val findings = HardcodedUiText().lint(code)

            // then
            findings.shouldBeEmpty()
        }

        @Test
        fun `should accept a literal without letters`() {
            // given
            val code = """
                fun screen(count: Int) {
                    Text(text = "${'$'}count · ")
                }
            """.trimIndent()

            // when
            val findings = HardcodedUiText().lint(code)

            // then
            findings.shouldBeEmpty()
        }

        @Test
        fun `should accept text in a preview`() {
            // given
            val code = """
                @Preview
                fun ScreenPreview() {
                    Screen(title = "Tes chansons")
                }
            """.trimIndent()

            // when
            val findings = HardcodedUiText().lint(code)

            // then
            findings.shouldBeEmpty()
        }

        @Test
        fun `should accept the debug label of an animation`() {
            // given
            val code = """
                fun screen() {
                    Crossfade(targetState = 1, label = "launch") {}
                }
            """.trimIndent()

            // when
            val findings = HardcodedUiText().lint(code)

            // then
            findings.shouldBeEmpty()
        }

        @Test
        fun `should accept a literal passed to another parameter`() {
            // given
            val code = """
                fun screen() {
                    Thing(key = "song")
                }
            """.trimIndent()

            // when
            val findings = HardcodedUiText().lint(code)

            // then
            findings.shouldBeEmpty()
        }
    }
}
