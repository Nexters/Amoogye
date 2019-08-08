package cookcook.nexters.com.amoogye.views.timer.presenter

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.base.BaseNumberButton
import cookcook.nexters.com.amoogye.databinding.FragmentTimerBinding
import kotlinx.android.synthetic.main.fragment_timer.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class TimerFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_timer

    private val timerViewModel: TimerViewModel by viewModel()
    private lateinit var binding: FragmentTimerBinding

    override val isUseDataBinding: Boolean = true

    override fun setupViews(view: View) {
        timerViewModel.timerEditTextSetting(edit_hour)
        timerViewModel.timerEditTextSetting(edit_minute)
        timerViewModel.timerEditTextSetting(edit_second)

        val editTextList = arrayOf(edit_hour, edit_minute, edit_second)

        for (x in 0 until editTextList.size) {
            editTextClickEvent(editTextList[x], x)
        }

        BaseNumberButton(view, onClick)
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
            val timer = object : CountDownTimer(timerViewModel.time.value!!.toLong(), 1000L) {
                override fun onFinish() {
                    txt_timer_time.visibility = View.GONE
                    btn_timer_start_stop_flag.visibility = View.VISIBLE
                    btn_timer_pause_resume_flag.visibility = View.GONE
                    btn_timer_cancel.visibility = View.GONE
                    txt_timer_next_text.visibility = View.VISIBLE
                    layout_timer_edit.visibility = View.VISIBLE
                    timerViewModel.stopTimerTask()
                }

                override fun onTick(millisUntilFinished: Long) {
                    txt_timer_time.visibility = View.VISIBLE
                    txt_timer_next_text.visibility = View.GONE
                    var time = millisUntilFinished + 1000L
                    txt_timer_time.text = String.format(
                        Locale.getDefault(), "%d 시간 %d 분 %d 초",
                        time / 1000L / 3600L,
                        (time / 1000L % 3600L) / 60L,
                        (time / 1000L % 3600L) % 60L
                    )
                    btn_timer_start_stop_flag.visibility = View.GONE
                    layout_timer_edit.visibility = View.GONE
                    btn_timer_pause_resume_flag.visibility = View.VISIBLE
                    btn_timer_cancel.visibility = View.VISIBLE
                }
            }
            timer.start()
        }
    }

    override fun subscribeUI() {

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