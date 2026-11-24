package io.github.krawist.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

/** Shared rules of the apps, enabled under `krawist:` in `config/detekt/detekt.yml`. */
class KrawistRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "krawist"

    override fun instance(config: Config): RuleSet = RuleSet(ruleSetId, listOf(HardcodedUiText(config)))
}
