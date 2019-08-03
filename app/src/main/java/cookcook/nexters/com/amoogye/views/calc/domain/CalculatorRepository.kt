package cookcook.nexters.com.amoogye.views.calc.domain

import android.content.Context
import androidx.fragment.app.FragmentManager
import cookcook.nexters.com.amoogye.base.BaseFragment

interface CalculatorRepository {
    fun showToast(context: Context, text: String)

    fun changeFragment(id: Int, destination: BaseFragment, fragmentManager: FragmentManager)
}