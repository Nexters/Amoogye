package cookcook.nexters.com.amoogye.views.calc.data

import android.content.Context
import android.widget.Toast
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository

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