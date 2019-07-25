package cookcook.nexters.com.amoogye.views.main.data

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.main.domain.MainRepository
import cookcook.nexters.com.amoogye.utils.addFragmentToActivity
import cookcook.nexters.com.amoogye.views.calc.CalcFragment

class MainRepositoryImpl : MainRepository {

    override fun showToast(context: Context) {
        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
    }

    override fun getMessage(): String {
        return "hello"
    }

    override fun onNavigationItemSelected(id: Int, fragmentManager: FragmentManager) {
        when (id) {
            R.id.navigation_main_calc -> {
                addFragmentToActivity(fragmentManager, CalcFragment(), R.id.layout_container)
            }
        }
    }
}