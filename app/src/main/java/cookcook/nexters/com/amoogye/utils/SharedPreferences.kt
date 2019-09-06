package cookcook.nexters.com.amoogye.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    val PREFS_FILENAME = "com.cookcook.prefs"
    val ONBOARDING = "Onboarding"
    val TOOLSDIM = "ToolsDim"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var onboarding: Boolean
        get() = prefs.getBoolean(ONBOARDING, true)
        set(value) = prefs.edit().putBoolean(ONBOARDING, value).apply()

    var toolsDim: Boolean
        get() = prefs.getBoolean(TOOLSDIM, true)
        set(value) = prefs.edit().putBoolean(TOOLSDIM, value).apply()
}