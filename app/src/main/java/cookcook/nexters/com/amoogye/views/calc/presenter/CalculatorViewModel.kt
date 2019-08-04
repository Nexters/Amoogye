package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository

class CalculatorViewModel(private val repo: CalculatorRepository) : ViewModel() {
    lateinit var context: Context
    var flag = 1

    fun gazuaa(text: String) = repo.showToast(context, text)

    fun convertFragment(view: View, fragmentManager: FragmentManager, click: Int) = repo.changeFragment(view, fragmentManager, flag, click)
}