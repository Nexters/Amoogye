package cookcook.nexters.com.amoogye.views.calc.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository
import cookcook.nexters.com.amoogye.views.calc.presenter.IngredientFragment
import cookcook.nexters.com.amoogye.views.calc.presenter.PortionFragment
import cookcook.nexters.com.amoogye.views.calc.presenter.TwiceFragment

class CalculatorRepositoryImpl : CalculatorRepository {
    override fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun changeText(number: String, text: String): String {
        var afterText: String = text

        if (number == "delete" && text.isNotEmpty()) {
            afterText = text.substring(0, text.length - 1)
        } else {
            if (number != "delete") {
                if (afterText == "0") {
                    afterText = number
                } else {
                    afterText += number
                }
            }
        }

        if (afterText.isEmpty()) {
            return "0"
        }

        return afterText
    }
}