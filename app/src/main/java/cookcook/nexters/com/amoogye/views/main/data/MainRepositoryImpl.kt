package cookcook.nexters.com.amoogye.views.main.data

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.main.domain.MainRepository
import cookcook.nexters.com.amoogye.utils.addFragmentToActivity
import cookcook.nexters.com.amoogye.views.calc.CalcFragment
import cookcook.nexters.com.amoogye.views.setting.SettingFragment
import cookcook.nexters.com.amoogye.views.timer.TimerFragment
import cookcook.nexters.com.amoogye.views.tools.ToolsFragment

class MainRepositoryImpl : MainRepository {

    override fun showToast(context: Context) {
        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
    }

    override fun getMessage(): String {
        return "hello"
    }

    override fun onNavigationItemSelected(id: Int, fragmentManager: FragmentManager): Boolean {
        val container: Int = R.id.layout_container
        when (id) {
            R.id.navigation_main_calc -> {
                addFragmentToActivity(fragmentManager, CalcFragment.getInstance(), container)
                return true
            }
            R.id.navigation_main_timer -> {
                addFragmentToActivity(fragmentManager, TimerFragment.getInstance(), container)
                return true
            }
            R.id.navigation_main_setting -> {
                addFragmentToActivity(fragmentManager, SettingFragment.getInstance(), container)
                return true
            }
            R.id.navigation_main_tools -> {
                addFragmentToActivity(fragmentManager, ToolsFragment.getInstance(), container)
                return true
            }
        }
        return false
    }
}