package cookcook.nexters.com.amoogye.views.main.domain

import android.content.Context
import androidx.fragment.app.FragmentManager

interface MainRepository {
    fun showToast(context: Context)

    fun onNavigationItemSelected(id: Int, fragmentManager: FragmentManager)

    fun getMessage(): String
}