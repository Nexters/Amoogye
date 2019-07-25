package cookcook.nexters.com.amoogye.views.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R


class SettingFragment: Fragment() {

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: SettingFragment? = null

        fun getInstance(): SettingFragment {
            if (INSTANCE == null) {
                INSTANCE = SettingFragment()
            }
            return INSTANCE!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }
}