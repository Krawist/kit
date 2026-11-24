package io.github.krawist.design.glass

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class GlassBlurSupportTest {
    @Nested
    inner class IsGlassBlurSupported {
        @ParameterizedTest
        @ValueSource(ints = [31, 34, 36])
        fun `should blur from Android 12 on a regular phone`(sdkInt: Int) {
            // when
            val supported = isGlassBlurSupported(sdkInt = sdkInt, isLowRamDevice = false)

            // then
            supported.shouldBeTrue()
        }

        @ParameterizedTest
        @ValueSource(ints = [29, 30])
        fun `should not blur before Android 12`(sdkInt: Int) {
            // when
            val supported = isGlassBlurSupported(sdkInt = sdkInt, isLowRamDevice = false)

            // then
            supported.shouldBeFalse()
        }

        @Test
        fun `should not blur on a phone flagged as low-end`() {
            // when
            val supported = isGlassBlurSupported(sdkInt = 36, isLowRamDevice = true)

            // then
            supported.shouldBeFalse()
        }
    }
}
