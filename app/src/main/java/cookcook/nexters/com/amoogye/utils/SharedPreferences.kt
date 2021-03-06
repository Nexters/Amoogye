package cookcook.nexters.com.amoogye.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    val PREFS_FILENAME = "com.cookcook.prefs"
    val ONBOARDING = "Onboarding"
    val TOOLSDIM = "ToolsDim"
    val SOUND = "Sound"
    val VIBRATION = "Vibration"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var onboarding: Boolean
        get() = prefs.getBoolean(ONBOARDING, true)
        set(value) = prefs.edit().putBoolean(ONBOARDING, value).apply()

    var toolsDim: Boolean
        get() = prefs.getBoolean(TOOLSDIM, true)
        set(value) = prefs.edit().putBoolean(TOOLSDIM, value).apply()

    var sound: Boolean
        get () = prefs.getBoolean(SOUND, true)
        set(value) = prefs.edit().putBoolean(SOUND, value).apply()

    var vibration: Boolean
        get () = prefs.getBoolean(VIBRATION, true)
        set(value) = prefs.edit().putBoolean(VIBRATION, value).apply()
}