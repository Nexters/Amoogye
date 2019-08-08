package cookcook.nexters.com.amoogye.views.timer.presenter

import android.content.Context
import android.os.Build
import android.text.InputType
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.utils.TimerCode
import cookcook.nexters.com.amoogye.views.timer.domain.TimerRepository

class TimerViewModel(private val repo: TimerRepository) : ViewModel() {

    lateinit var context: Context

    private val _hourText = MutableLiveData<String>()
    private val _minuteText = MutableLiveData<String>()
    private val _secondText = MutableLiveData<String>()
    private val _selectedTimerEditText = MutableLiveData<Int>()
    private val _time = MutableLiveData<Int>()

    val hourText: LiveData<String> get() = _hourText
    val minuteText: LiveData<String> get() = _minuteText
    val secondText: LiveData<String> get() = _secondText
    val time: LiveData<Int> get() = _time

    private var isTimerStart: Boolean = false
    private var isTimerEnd: Boolean = false

    init {
        _hourText.value = "0"
        _minuteText.value = "0"
        _secondText.value = "0"
    }

    /**
     * 타이머 텍스트, 버튼 구분용
     */
    fun timerState(): TimerCode {
        if (isTimerStart && isTimerEnd) {
            return TimerCode.STATE_END // 종료
        }
        if (isTimerStart) {
            return TimerCode.STATE_PROGRESS // 진행 중
        }
        if (isTimerEnd) {
            return TimerCode.STATE_PAUSE // 일시 정지
        }

        return TimerCode.STATE_START // 시작
    }

    fun setSelectedTimerEditText(number: Int) {
        _selectedTimerEditText.value = number
    }

    fun getSelectedTimerEditText() = _selectedTimerEditText.value

    fun timerEditTextSetting(editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.showSoftInputOnFocus = false
        }
    }

    fun onNumberButtonClick(number: String) {
        when (_selectedTimerEditText.value) {
            0 -> {
                _hourText.value = _hourText.value?.let { it ->
                    repo.limitNumber(0, repo.textChange(number, it)).toString()
                } ?: repo.limitNumber(0, repo.textChange(number, "0")).toString()
            }
            1 -> {
                _minuteText.value = _minuteText.value?.let { it ->
                    repo.limitNumber(1, repo.textChange(number, it)).toString()
                } ?: repo.limitNumber(1, repo.textChange(number, "0")).toString()
            }
            2 -> {
                _secondText.value = _secondText.value?.let { it ->
                    repo.limitNumber(2, repo.textChange(number, it)).toString()
                } ?: repo.limitNumber(2, repo.textChange(number, "0")).toString()
            }
        }
    }

    fun onTimerStartButtonClick() {
        isTimerStart = true
        this._time.value =
            repo.timerTime(_hourText.value!!.toInt(), _minuteText.value!!.toInt(), _secondText.value!!.toInt()) * 1000

    }

    fun stopTimerTask() {
        isTimerEnd = true
    }
}