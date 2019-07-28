package cookcook.nexters.com.amoogye.views.timer.presenter

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment


class TimerFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_timer

    override fun setupViews(view: View) {

    }

    override fun subscribeUI() {
    }

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: TimerFragment? = null

        fun getInstance(): TimerFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    TimerFragment()
            }
            return INSTANCE!!
        }
    }
}