package io.github.krawist.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtStringTemplateExpression
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.psiUtil.getParentOfType

/**
 * Finds user-facing text written in the code instead of `strings.xml`: a string literal holding a letter, passed as a
 * text parameter (`text`, `title`, `label`, `contentDescription`…) or as the first argument of `Text(...)`. Previews,
 * which show example text, and the debug labels of animations are left out.
 */
class HardcodedUiText(config: Config = Config.empty) : Rule(config) {
    override val issue = Issue(
        id = "HardcodedUiText",
        severity = Severity.Defect,
        description = "User-facing text belongs in strings.xml, in French and English: use stringResource().",
        debt = Debt.FIVE_MINS,
    )

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        val callee = expression.calleeExpression?.text ?: return
        expression.valueArguments.forEachIndexed { index, argument ->
            if (argument.isUiText(callee = callee, index = index) && !argument.isInPreview()) {
                val message = "Hard-coded text passed to $callee: use a string resource."
                report(CodeSmell(issue = issue, entity = Entity.from(argument), message = message))
            }
        }
    }

    private fun KtValueArgument.isUiText(callee: String, index: Int): Boolean {
        val literal = getArgumentExpression() as? KtStringTemplateExpression
        // Only the written parts count, not the values put in (`"${'$'}count · "` holds no text).
        val written = literal?.entries?.filterIsInstance<KtLiteralStringTemplateEntry>().orEmpty()
        val name = getArgumentName()?.asName?.asString()
        return written.any { entry -> entry.text.any(Char::isLetter) } &&
            when (name) {
                null -> callee == "Text" && index == 0
                "label" -> !AnimationCall.matches(callee)
                else -> name in TextParameters
            }
    }

    private fun KtValueArgument.isInPreview(): Boolean {
        var function = getParentOfType<KtNamedFunction>(strict = true)
        while (function != null) {
            if (function.annotationEntries.any { it.shortName?.asString() == "Preview" }) return true
            function = function.getParentOfType(strict = true)
        }
        return false
    }

    private companion object {
        val TextParameters = setOf(
            "text",
            "title",
            "subtitle",
            "contentDescription",
            "placeholder",
            "message",
            "actionLabel",
        )

        // Animations take a `label` that only shows in the debugging tools.
        val AnimationCall = Regex("animate.*|Crossfade|AnimatedContent|updateTransition|rememberInfiniteTransition")
    }
}
