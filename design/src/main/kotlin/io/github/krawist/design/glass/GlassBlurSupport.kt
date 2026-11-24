package io.github.krawist.design.glass

import android.app.ActivityManager
import android.content.Context
import android.os.Build

/**
 * Whether glass surfaces can blur what is behind them on this phone: `RenderEffect` is available (Android 12+) and the
 * phone is not flagged as low-end. Call it once at startup and pass the result to [GlassTheme].
 */
fun Context.supportsGlassBlur(): Boolean = isGlassBlurSupported(
    sdkInt = Build.VERSION.SDK_INT,
    isLowRamDevice = getSystemService(ActivityManager::class.java)?.isLowRamDevice ?: true,
)

internal fun isGlassBlurSupported(sdkInt: Int, isLowRamDevice: Boolean): Boolean =
    sdkInt >= Build.VERSION_CODES.S && !isLowRamDevice
