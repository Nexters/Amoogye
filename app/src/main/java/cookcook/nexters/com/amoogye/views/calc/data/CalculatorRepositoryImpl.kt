package cookcook.nexters.com.amoogye.views.calc.data

import android.content.Context
import android.widget.Toast
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository

class CalculatorRepositoryImpl : CalculatorRepository {
    override fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun changeNumberText(number: String, text: String): String {
        var afterText: String = text

        if (number == "delete" && text.isNotEmpty()) {
            afterText = text.substring(0, text.length - 1)
        } else {
            if (number != "delete") {
                if (afterText == "0") {
                    afterText = if (number == ".") {
                        "0."
                    } else {
                        number
                    }
                } else {
                    if (number == ".") {
                        if (afterText.any { it == '.' }) {
                            return afterText
                        }
                    }
                    if (afterText.any{it == '.'}) {
                        val dump = afterText.split('.')
                        if (dump[1].isNotEmpty()) {
                            return afterText
                        }
                    }
                    afterText += number
                }
            }
        }

        if (afterText.isEmpty()) {
            return "0"
        }

        if ((afterText.toCharArray()[afterText.lastIndex] != '.') && afterText.toDouble() > 9999) {
            afterText = "9999"
        }

        return afterText
    }
}