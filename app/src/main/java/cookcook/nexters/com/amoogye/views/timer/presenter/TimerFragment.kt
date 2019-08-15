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
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.base.BaseNumberButton
import cookcook.nexters.com.amoogye.databinding.FragmentTimerBinding
import cookcook.nexters.com.amoogye.utils.TimerStatus
import kotlinx.android.synthetic.main.fragment_timer.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.ceil


class TimerFragment : BaseFragment() {
//    override fun getHandledServiceStates(): Array<TimerStatus> {
//        return arrayOf(
//            TimerStatus.STATE_START,
//            TimerStatus.STATE_PROGRESS,
//            TimerStatus.STATE_WAIT
//        )
//    }
//
//    override fun getFinishingServiceStates(): Array<TimerStatus> {
//        return arrayOf(
//            TimerStatus.STATE_PAUSE,
//            TimerStatus.STATE_END
//        )
//    }
//
//    override fun handleState(timerStatus: TimerStatus) {
//        super.handleState(timerStatus)
//        when (timerStatus) {
//            TimerStatus.STATE_START -> {
//
//            }
//
//            TimerStatus.STATE_WAIT -> {
//
//            }
//
//            TimerStatus.STATE_PAUSE -> {
//
//            }
//
//            TimerStatus.STATE_PROGRESS -> {
//
//            }
//
//            TimerStatus.STATE_END -> {
//
//            }
//        }
//    }

    override val layoutRes: Int = R.layout.fragment_timer

    private val timerViewModel: TimerViewModel by viewModel()

    private lateinit var binding: FragmentTimerBinding

    private lateinit var displayMetrics: DisplayMetrics

    override val isUseDataBinding: Boolean = true

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

        BaseNumberButton(view, onClick)
    }

    override fun subscribeUI() {

    }

    fun onUpdateView() {
        txt_timer_time.visibility = View.GONE
        btn_timer_start_stop_flag.visibility = View.GONE
        btn_timer_pause_resume_flag.visibility = View.GONE
        btn_timer_cancel.visibility = View.GONE
        txt_timer_next_text.visibility = View.GONE
        layout_timer_edit.visibility = View.GONE

        layout_timer_background.visibility = View.GONE
        view_contour.visibility = View.GONE
        layout_number_button_area.visibility = View.GONE

        var layoutParam = layout_timer_background.layoutParams
        layoutParam.width = 0
        layout_timer_background.layoutParams = layoutParam
    }

    var timerRemindTime: Long = 0
    var timerOriginalTime = 0

    fun initTimer(remindTime: Long) {
        timer = object : CountDownTimer(remindTime, 10L) {
            override fun onFinish() {
                txt_timer_time.visibility = View.GONE
                btn_timer_start_stop_flag.visibility = View.VISIBLE
                btn_timer_pause_resume_flag.visibility = View.GONE
                btn_timer_cancel.visibility = View.GONE
                txt_timer_next_text.visibility = View.VISIBLE
                layout_timer_edit.visibility = View.VISIBLE
                var layoutParam = layout_timer_background.layoutParams
                layout_timer_background.visibility = View.GONE
                view_contour.visibility = View.VISIBLE
                layoutParam.width = 0
                layout_timer_background.layoutParams = layoutParam
                timerViewModel.stopTimerTask()
                layout_number_button_area.visibility = View.VISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                txt_timer_time.visibility = View.VISIBLE
                txt_timer_next_text.visibility = View.GONE
                val initialTime = timerViewModel.time.value!!
                layout_number_button_area.visibility = View.GONE
                view_contour.visibility = View.GONE
                val time = millisUntilFinished
                txt_timer_time.text = String.format(
                    Locale.getDefault(), "%d 시간 %d 분 %d 초",
                    time / 1000L / 3600L,
                    (time / 1000L % 3600L) / 60L,
                    (time / 1000L % 3600L) % 60L
                )
                timerViewModel.setTime(time.toInt())
                btn_timer_start_stop_flag.visibility = View.GONE
                layout_timer_edit.visibility = View.GONE
                btn_timer_pause_resume_flag.visibility = View.VISIBLE
                btn_timer_cancel.visibility = View.VISIBLE

                val maxWidth = displayMetrics.widthPixels

                val currentWidth = (maxWidth.toDouble() / (timerOriginalTime.toDouble() / 10))

                val currentK = ceil((millisUntilFinished).toDouble() / 10)

                Log.d(
                    "TAG",
                    "maxWidth $maxWidth currentWidth $currentWidth currentK $currentK initialTIme $initialTime"
                )

                val layoutParam = layout_timer_background.layoutParams
                timerRemindTime = millisUntilFinished
                layout_timer_background.visibility = View.VISIBLE
                layoutParam.width = (currentK * currentWidth).toInt()
                layout_timer_background.layoutParams = layoutParam
            }
        }
    }

    private val onClick: (number: String) -> Unit = {
        timerViewModel.onNumberButtonClick(it)
    }

    private fun editTextClickEvent(editText: EditText, number: Int) {
        editText.setOnTouchListener { v, event ->
            event.let {
                if (it.action == MotionEvent.ACTION_DOWN) {
                    timerViewModel.setSelectedTimerEditText(number)
                    when (timerViewModel.getSelectedTimerEditText()) {
                        0 -> {
                            edit_hour.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                            edit_minute.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
                            edit_second.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
                        }
                        1 -> {
                            edit_hour.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
                            edit_minute.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                            edit_second.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
                        }
                        2 -> {
                            edit_hour.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
                            edit_minute.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
                            edit_second.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                        }
                    }
                }
                false
            }
        }


        btn_timer_start_stop_flag.setOnClickListener {
            timerViewModel.onTimerStartButtonClick()
            initTimer(timerViewModel.time.value!! + 100L)
            timerOriginalTime = timerViewModel.time.value!!
            timer.start()
        }

        btn_timer_pause_resume_flag.setOnClickListener {

            when (timerViewModel.timerState()) {
                TimerStatus.STATE_PAUSE -> {
                    timerViewModel.timerStartTask()
                    initTimer(timerRemindTime)
                    timer.start()
                }
                TimerStatus.STATE_PROGRESS -> {
                    timerViewModel.pauseTimerTask()
                    timer.cancel()
                }
                else -> {
                    Log.e("TAG", "Status error. Timer state is ${timerViewModel.timerState()}")
                }
            }
        }

        btn_timer_cancel.setOnClickListener {
            timerViewModel.stopTimerTask()
            timer.onFinish()
        }
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
}