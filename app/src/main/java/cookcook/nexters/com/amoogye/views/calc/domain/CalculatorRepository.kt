package cookcook.nexters.com.amoogye.views.calc.domain

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager

interface CalculatorRepository {
    fun showToast(context: Context, text: String)

    fun changeText(number: String, text: String): String
}