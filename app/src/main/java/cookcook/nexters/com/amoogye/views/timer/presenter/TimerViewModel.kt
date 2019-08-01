package cookcook.nexters.com.amoogye.views.timer.presenter

import android.content.Context
import android.os.Build
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.SingleLiveEvent
import cookcook.nexters.com.amoogye.views.timer.domain.TimerRepository

class TimerViewModel(private val repo: TimerRepository) : ViewModel() {

    lateinit var context: Context

    private val _hourText = MutableLiveData<String>()
    private val _minuteText = MutableLiveData<String>()
    private val _secondText = MutableLiveData<String>()
    private val _selectedTimerEditText = MutableLiveData<Int>()
    private val _onSelectTimerEditText = SingleLiveEvent<Any>()

    val selectedTimerEditText: LiveData<Int>
        get() = _selectedTimerEditText // 0 : hour, 1 : minute, 2 : second

//    var selectedTimerEditText = 0

    val hourText: LiveData<String> get() = _hourText
    val minuteText: LiveData<String> get() = _minuteText
    val secondText: LiveData<String> get() = _secondText
    val onSelectTimerEditText: LiveData<Any> get() = _onSelectTimerEditText

    fun onSelectTimerEditText() {
        _onSelectTimerEditText.call()
    }

    fun setSelectedTimerEditText(number: Int) {
        Log.d("TAG", "click " + number)
        _selectedTimerEditText.value = number
    }

    fun timerEditTextSetting(editText: EditText, number: Int) {
        editText.inputType = InputType.TYPE_NULL
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.showSoftInputOnFocus = false
        }
        editText.setOnTouchListener { v, event ->   event.let {
            if(it.action == MotionEvent.ACTION_DOWN) {
                setSelectedTimerEditText(number)
            }
            false
        }}
        editText.setTextIsSelectable(true)
    }

    fun onSelectTimerEditTextClick(number: Int) {
        Log.d("TAG", "click " + number)
        _selectedTimerEditText.value = number
    }

    fun onSelectTimerEditText(number: Int) {
        _selectedTimerEditText.value = number
        Log.d("TAG", "text " + selectedTimerEditText)
    }

    fun onNumberButtonClick(number: String) {
        Log.d("TAG", _selectedTimerEditText.value.toString())
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