package cookcook.nexters.com.amoogye.views.timer.domain

interface TimerRepository {

    fun onEditTextSelected()
    fun onTimerButtonClick()
    fun timerEvent()

    fun textChange(number: String, text: String): String
}
