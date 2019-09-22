package cookcook.nexters.com.amoogye.views.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.utils.SharedPreferences
import de.psdev.licensesdialog.LicensesDialogFragment
import kotlinx.android.synthetic.main.fragment_setting.*


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val setting = SharedPreferences(activity!!.applicationContext)

        setting_text_onboarding_init.setOnClickListener {
            val onboarding = SharedPreferences(view.context)
            onboarding.onboarding = true
            Toast.makeText(view.context, "앱을 다시 시작하면 온보딩을 확인할 수 있어요!",Toast.LENGTH_SHORT).show()
        }

        txt_setting_license.setOnClickListener {
            val fragment = LicensesDialogFragment.Builder(view.context)
                .setNotices(R.raw.notices)
                .build()

            fragment.show(childFragmentManager, null)
        }

        setting_text_contact_email.setOnClickListener {
            SweetAlertDialog(view.context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("쿸쿸 팀")
                .setContentText("")
        }

        setting_switch_sound.isChecked = setting.sound
        setting_switch_vibration.isChecked = setting.vibration

        setting_switch_sound.setOnCheckedChangeListener { _, isChecked ->
            setting.sound = isChecked
        }

        setting_switch_vibration.setOnCheckedChangeListener { _, isChecked ->
            setting.vibration = isChecked
        }
    }
}