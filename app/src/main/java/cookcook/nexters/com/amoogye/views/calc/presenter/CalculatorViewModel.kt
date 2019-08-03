package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository

class CalculatorViewModel(private val repo: CalculatorRepository) : ViewModel() {
    lateinit var context: Context

    fun gazuaa(text: String) = repo.showToast(context, text)

    fun convertFragment(id: Int, destination: BaseFragment, fragmentManager: FragmentManager) = repo.changeFragment(id, destination, fragmentManager)
}