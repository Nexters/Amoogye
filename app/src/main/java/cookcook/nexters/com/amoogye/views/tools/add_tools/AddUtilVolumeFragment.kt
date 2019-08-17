package cookcook.nexters.com.amoogye.views.tools.add_tools

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import cookcook.nexters.com.amoogye.views.tools.ToolsFragment
import kotlinx.android.synthetic.main.activity_tools_addutil_main.*
import kotlinx.android.synthetic.main.fragment_addutil_2_volume.*


class AddUtilVolumeFragment : Fragment() {

    interface OnGetNameByUserListener {
        fun onGetNameByUser()
    }


    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: AddUtilVolumeFragment? = null

        fun getInstance(): AddUtilVolumeFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    AddUtilVolumeFragment()
            }
            return INSTANCE!!
        }

        fun instanceInit() {
            INSTANCE = null
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_addutil_2_volume, container, false)
    }


    lateinit var picker: BaseScrollPicker
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = arrayListOf("aa","bb","cc","dd","ee","ff","gg")
        picker = BaseScrollPicker(view, list)
        picker.setColor(resources.getColor(R.color.number_non_focus_wrap_color))
        picker.inVisible()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_txt_name_util.setOnClickListener{
            picker.visible()
        }

        edit_txt_volume_decimal_point.setOnClickListener {

        }

        edit_txt_volume_under_decimal_point.setOnClickListener {

        }
    }

    private fun getFragment(fragment:Fragment) {
        val manager = childFragmentManager

        val transaction = manager.beginTransaction()
        transaction.replace(R.id.container_add_util_volume_choice, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun addUtilCloseKeyboard() {

        val inputMethodManager = context!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    }
}