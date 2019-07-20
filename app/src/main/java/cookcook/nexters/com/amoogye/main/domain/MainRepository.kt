package cookcook.nexters.com.amoogye.main.domain

import android.content.Context

interface MainRepository {
    fun showToast(context: Context)

    fun getMessage(): String
}