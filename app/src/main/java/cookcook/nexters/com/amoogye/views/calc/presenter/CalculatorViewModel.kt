package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository

class CalculatorViewModel(private val repo: CalculatorRepository) : ViewModel() {
    lateinit var context: Context
    var flag: Int = 1

    private var ingredientSelected = true
    private var portionSelected = false

    fun gazuaa(text: String) = repo.showToast(context, text)

    fun convertFragment(buttonId: Int): Int {
        when (buttonId) {
            1 -> {
                ingredientSelected = !ingredientSelected
                if (ingredientSelected) {
                    val dump = this.flag + buttonId
                    if (dump != 1) {
                        this.flag = dump
                    } else {
                        ingredientSelected = !ingredientSelected
                    }
                } else {
                    val dump = this.flag - buttonId
                    if (dump != 0) {
                        this.flag = dump
                    } else {
                        ingredientSelected = !ingredientSelected
                    }
                }
            }
            2 -> {
                portionSelected = !portionSelected
                if (portionSelected) {
                    val dump = this.flag + buttonId
                    if (dump != 2) {
                        this.flag = dump
                    } else {
                        portionSelected = !portionSelected
                    }
                } else {
                    val dump = this.flag - buttonId
                    if (dump != 0) {
                        this.flag = dump
                    } else {
                        portionSelected = !portionSelected
                    }
                }
            }
        }

        return this.flag - 1
    }
}