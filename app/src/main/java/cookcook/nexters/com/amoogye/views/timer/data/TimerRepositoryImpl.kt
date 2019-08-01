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

    override fun textChange(number: String, text: String): String {
        var afterText: String = text

        if (number == "delete" && text.isNotEmpty()) {
            afterText = text.substring(0, text.length - 1)
        } else {
            if (number != "delete") {
                afterText += number
            }
        }
        Log.d("TAG", "After Number $number")
        Log.d("TAG", "After Text $afterText")
        return afterText
    }

}