package cookcook.nexters.com.amoogye.views.timer.data

import android.util.Log
import cookcook.nexters.com.amoogye.views.timer.domain.TimerRepository

class TimerRepositoryImpl: TimerRepository {
    override fun onEditTextSelected() {
    }

    override fun onTimerButtonClick() {
    }

    override fun timerEvent() {
    }

    override fun limitNumber(type: Int, number: String): Int {
        when (type) {
            0 -> {
                if (number.toInt() > 24) return 24
                return number.toInt()
            }
            1 -> {
                if (number.toInt() > 59) return 59
                return number.toInt()
            }
            2 -> {
                if (number.toInt() > 59) return 59
                return number.toInt()
            }
            else -> return 0
        }
    }

    override fun textChange(number: String, text: String): String {
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

    override fun timerTime(hour: Int, minute: Int, second: Int): Int {
        return hour * 3600 + minute * 60 + second
    }
}
