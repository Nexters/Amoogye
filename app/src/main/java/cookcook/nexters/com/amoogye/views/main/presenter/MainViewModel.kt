package cookcook.nexters.com.amoogye.views.main.presenter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.views.main.domain.MainRepository


class MainViewModel(private val repo : MainRepository) : ViewModel() {

    lateinit var context: Context
    lateinit var supportFragmentManager: FragmentManager

    fun sayHello() = repo.showToast(context)

    fun onNavigationItemSelected(id: Int): Boolean = repo.onNavigationItemSelected(id, supportFragmentManager)

    val text = repo.getMessage()
}