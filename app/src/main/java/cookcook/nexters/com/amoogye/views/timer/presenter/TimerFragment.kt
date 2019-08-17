package cookcook.nexters.com.amoogye.views.timer.presenter

import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseNumberButton
import cookcook.nexters.com.amoogye.databinding.FragmentTimerBinding
import cookcook.nexters.com.amoogye.utils.TimerStatus
import kotlinx.android.synthetic.main.fragment_timer.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.ceil


class TimerFragment : TimerBaseFragment() {
    override fun getHandledServiceStates(): Array<TimerStatus> {
        return arrayOf(
            TimerStatus.STATE_START,
            TimerStatus.STATE_PROGRESS,
            TimerStatus.STATE_WAIT
        )
    }

    override fun getFinishingServiceStates(): Array<TimerStatus> {
        return arrayOf(
            TimerStatus.STATE_PAUSE,
            TimerStatus.STATE_END
        )
    }

    override fun handleState(timerStatus: TimerStatus) {
        super.handleState(timerStatus)
        onUpdateView(timerStatus)
    }

    override val layoutRes: Int = R.layout.fragment_timer

    private val timerViewModel: TimerViewModel by viewModel()

    private lateinit var binding: FragmentTimerBinding

    private lateinit var displayMetrics: DisplayMetrics

    override val isUseDataBinding: Boolean = true

    lateinit var layoutParam: ViewGroup.LayoutParams

    lateinit var timer: CountDownTimer

    override fun setupViews(view: View) {
        timerViewModel.timerEditTextSetting(edit_hour)
        timerViewModel.timerEditTextSetting(edit_minute)
        timerViewModel.timerEditTextSetting(edit_second)

        displayMetrics = view.context.applicationContext.resources.displayMetrics

        val editTextList = arrayOf(edit_hour, edit_minute, edit_second)

        for (x in 0 until editTextList.size) {
            editTextClickEvent(editTextList[x], x)
        }

        layoutParam = layout_timer_background.layoutParams
        layoutParam.width = 0
        layout_timer_background.layoutParams = layoutParam

        BaseNumberButton(view, onClick)
    }

    override fun subscribeUI() {

    }

    fun onUpdateView(state: TimerStatus) {
        visibleFlag(txt_timer_time, false)
        visibleFlag(btn_timer_start_stop_flag, false)
        visibleFlag(btn_timer_pause_resume_flag, false)
        visibleFlag(btn_timer_cancel, false)
        visibleFlag(txt_timer_next_text, false)
        visibleFlag(layout_timer_edit, false)
        visibleFlag(layout_timer_background, false)
        visibleFlag(view_contour, false)
        visibleFlag(layout_number_button_area, false)

        when (state) {
            TimerStatus.STATE_WAIT, TimerStatus.STATE_START -> {
                visibleFlag(layout_timer_edit, true)
                visibleFlag(txt_timer_next_text, true)
                visibleFlag(layout_timer_button, true)
                visibleFlag(view_contour, true)
                visibleFlag(layout_number_button_area, true)
                visibleFlag(btn_timer_start_stop_flag, true)
                txt_timer_next_text.text = "알려드릴게요!"
                btn_timer_start_stop_flag.text = "시작하기"
            }

            TimerStatus.STATE_PROGRESS -> {
                val time = this.getTimerService()!!.getRemindTimes()
                visibleFlag(txt_timer_time, true)
                visibleFlag(btn_timer_cancel, true)
                visibleFlag(btn_timer_pause_resume_flag, true)
                btn_timer_pause_resume_flag.text = "일시정지"
                visibleTimerBackgroundImage()
            }

            TimerStatus.STATE_PAUSE -> {
                visibleFlag(txt_timer_time, true)
                visibleFlag(layout_timer_background, true)
                visibleFlag(btn_timer_cancel, true)
                visibleFlag(btn_timer_pause_resume_flag, true)
                btn_timer_pause_resume_flag.text = "다시시작"
                visibleTimerBackgroundImage()
            }

            TimerStatus.STATE_END -> {
                visibleFlag(layout_timer_edit, true)
                visibleFlag(txt_timer_next_text, true)
                visibleFlag(layout_timer_button, true)
                visibleFlag(view_contour, true)
                visibleFlag(btn_timer_start_stop_flag, true)
                txt_timer_next_text.text = "끝났습니다!"
                btn_timer_start_stop_flag.text = "종료"
            }
        }
    }

    fun visibleTimerBackgroundImage() {
        val time = this.getTimerService()!!.getRemindTimes()
        val maxWidth = displayMetrics.widthPixels
        val currentWidth = (maxWidth.toDouble() / ((getTimerService()!!.getInitialTimes()).toDouble() / 10))
        val currentK = ceil(time.toDouble() / 10)
        layout_timer_background.visibility = View.VISIBLE
        layoutParam.width = (currentK * currentWidth).toInt()
        layout_timer_background.layoutParams = layoutParam
        txt_timer_time.text = String.format(
            Locale.getDefault(), "%d 시간 %d 분 %d 초",
            time / 1000L / 3600L,
            (time / 1000L % 3600L) / 60L,
            (time / 1000L % 3600L) % 60L
        )
    }

    fun visibleFlag(component: View, isVisible: Boolean) {
        if (isVisible) {
            component.visibility = View.VISIBLE
        } else {
            component.visibility = View.GONE
        }
    }

    private val onClick: (number: String) -> Unit = {
        timerViewModel.onNumberButtonClick(it)
    }

    private fun editTextWrapChange(number: Int) {
        edit_hour.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_minute.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_second.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)

        when (number) {
            0 -> edit_hour.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            1 -> edit_minute.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            2 -> edit_second.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
        }
    }

    private fun editTextClickEvent(editText: EditText, number: Int) {
        editText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                timerViewModel.setSelectedTimerEditText(number)
                editTextWrapChange(timerViewModel.getSelectedTimerEditText()!!)
            }
            false
        }


        btn_timer_start_stop_flag.setOnClickListener {
            if (getTimerService()!!.getState() == TimerStatus.STATE_WAIT || getTimerService()!!.getState() == TimerStatus.STATE_START) {
                timerViewModel.onTimerStartButtonClick()
                getTimerService()!!.setInitialTimes(timerViewModel.time.value!! + 100L)
                getTimerService()!!.onStartCountdown()
                visibleTimerStart()
            } else if (getTimerService()!!.getState() == TimerStatus.STATE_END) {
                getTimerService()!!.timerStatus = TimerStatus.STATE_WAIT
                onUpdateView(getTimerService()!!.getState())
            }
        }

        btn_timer_pause_resume_flag.setOnClickListener {

            val timerService = getTimerService()!!
            if (timerService.getState() == TimerStatus.STATE_PROGRESS) {
                timerService.onPauseCountdown()
                timer.cancel()
                onUpdateView(timerService.getState())
            } else if (timerService.getState() == TimerStatus.STATE_PAUSE) {
                timerService.onRestartCountdown()
                visibleTimerStart()
            }
        }

        btn_timer_cancel.setOnClickListener {
            val timerService = getTimerService()!!
            timerService.onCancelCountdown()
            timer.cancel()
            onUpdateView(timerService.getState())
        }
    }

    fun visibleTimerStart() {

        timer = object : CountDownTimer(getTimerService()!!.getRemindTimes(), 10L) {
            override fun onFinish() {
                onUpdateView(TimerStatus.STATE_END)
            }

            override fun onTick(millisUntilFinished: Long) {
                onUpdateView(getTimerService()!!.getState())
            }
        }

        timer.start()
    }


    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: TimerFragment? = null

        fun getInstance(): TimerFragment {
            if (INSTANCE == null) {
                INSTANCE = TimerFragment()
            }
            return INSTANCE!!
        }
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.timerVM = timerViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (isServiceBound()) {
            val timerService = getTimerService()!!;
            if (timerService.getState() == TimerStatus.STATE_PROGRESS) {
                visibleTimerStart()
            } else {
                onUpdateView(timerService.getState())
            }
        }
    }

    override fun onPause() {
        if (getTimerService()!!.getState() === TimerStatus.STATE_PROGRESS) {
            timer.cancel()
        }
        super.onPause()
    }

    override fun onStop() {
        if (getTimerService()!!.getState() === TimerStatus.STATE_PROGRESS) {
            timer.cancel()
        }
        super.onStop()
    }
}