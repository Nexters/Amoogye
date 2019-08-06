package cookcook.nexters.com.amoogye.views.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.fragment_addutil_2_volume.*


class AddUtilVolumeFragment : Fragment() {

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: ToolsFragment? = null

        fun getInstance(): ToolsFragment {
            if (INSTANCE == null) {
                INSTANCE = ToolsFragment()
            }
            return INSTANCE!!
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_addutil_2_volume, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        edit_txt_name_util.setOnClickListener{
            getFragment(TestFragment())
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
}