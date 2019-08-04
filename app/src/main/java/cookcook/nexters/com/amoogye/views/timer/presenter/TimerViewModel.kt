package cookcook.nexters.com.amoogye.views.timer.presenter

import android.content.Context
import android.os.Build
import android.text.InputType
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.views.timer.domain.TimerRepository

class TimerViewModel(private val repo: TimerRepository) : ViewModel() {

    lateinit var context: Context

    private val _hourText = MutableLiveData<String>()
    private val _minuteText = MutableLiveData<String>()
    private val _secondText = MutableLiveData<String>()
    private val _selectedTimerEditText = MutableLiveData<Int>()

    val hourText: LiveData<String> get() = _hourText
    val minuteText: LiveData<String> get() = _minuteText
    val secondText: LiveData<String> get() = _secondText

    fun setSelectedTimerEditText(number: Int) {
        _selectedTimerEditText.value = number
    }

    fun timerEditTextSetting(editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.showSoftInputOnFocus = false
        }
//        editText.setTextIsSelectable(true)
    }

    fun onNumberButtonClick(number: String) {
        when (_selectedTimerEditText.value) {
            0 -> {
                _hourText.value = _hourText.value?.let { it ->
                    repo.textChange(number, it)
                } ?: repo.textChange(number, "")
            }
            1 -> {
                _minuteText.value = _minuteText.value?.let { it ->
                    repo.textChange(number, it)
                } ?: repo.textChange(number, "")
            }
            2 -> {
                _secondText.value = _secondText.value?.let { it ->
                    repo.textChange(number, it)
                } ?: repo.textChange(number, "")
            }
        }
    }
}