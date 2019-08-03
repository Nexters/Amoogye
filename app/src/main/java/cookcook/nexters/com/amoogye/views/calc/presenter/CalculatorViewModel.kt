package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository

class CalculatorViewModel(private val repo: CalculatorRepository) : ViewModel() {
    lateinit var context: Context

    fun gazuaa(text: String) = repo.showToast(context, text)
}