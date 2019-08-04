package cookcook.nexters.com.amoogye.views.timer.presenter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.databinding.FragmentTimerBinding
import kotlinx.android.synthetic.main.fragment_timer.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TimerFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_timer

    private val timerViewModel: TimerViewModel by viewModel()
    private lateinit var binding: FragmentTimerBinding

    override val isUseDataBinding: Boolean = true

    private var numberButtonList = arrayOf(
        R.id.layout_btn_0,
        R.id.layout_btn_1,
        R.id.layout_btn_2,
        R.id.layout_btn_3,
        R.id.layout_btn_4,
        R.id.layout_btn_5,
        R.id.layout_btn_6,
        R.id.layout_btn_7,
        R.id.layout_btn_8,
        R.id.layout_btn_9,
        R.id.layout_btn_dot,
        R.id.layout_btn_delete
    )

    private var numberButtonTextList = arrayOf(
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        ".",
        "delete"
    )

    override fun setupViews(view: View) {
        timerViewModel.timerEditTextSetting(edit_hour)
        timerViewModel.timerEditTextSetting(edit_minute)
        timerViewModel.timerEditTextSetting(edit_second)

        editTextClickEvent(edit_hour, 0)
        editTextClickEvent(edit_minute, 1)
        editTextClickEvent(edit_second, 2)

        for (x in 0 until numberButtonList.size) {
            var layout = view.findViewById<RelativeLayout>(numberButtonList[x])
            layout.setOnClickListener {
                timerViewModel.onNumberButtonClick(numberButtonTextList[x])
            }
        }
    }

    private fun editTextClickEvent(editText: EditText, number: Int) {
        editText.setOnTouchListener { v, event ->   event.let {
            if(it.action == MotionEvent.ACTION_DOWN) {
                timerViewModel.setSelectedTimerEditText(number)
            }
            editText.setSelection(editText.text.length)
            false
        }}
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