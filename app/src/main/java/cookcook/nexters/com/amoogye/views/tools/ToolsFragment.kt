package cookcook.nexters.com.amoogye.views.tools

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.fragment_tools.*


class ToolsFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_tools, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val toolsFragmentAdapter = ToolsViewPageAdapter(fragmentManager!!)
        layout_Tools_viewPager.adapter = toolsFragmentAdapter

        layout_tools_tab_layout.setupWithViewPager(layout_Tools_viewPager)


        layout_tools_dimScreen.setOnClickListener {
            layout_tools_dimScreen.visibility = View.GONE
            img_popup_message.visibility = View.GONE
        }

        btn_convert_add_util_activity.setOnClickListener {
            val intent = Intent(context, ToolsAddUtilActivity::class.java)
            startActivity(intent)
        }

        //visibilityEditMode()

        btn_edit_toolList.setOnClickListener {
            btn_edit_toolList.visibility = View.GONE
            btn_edit_cancel.visibility = View.VISIBLE
            layout_tools_tab_layout.visibility = View.GONE
            flag_iseditmode = true
        }

        btn_edit_cancel.setOnClickListener {
            btn_edit_toolList.visibility = View.VISIBLE
            btn_edit_cancel.visibility = View.GONE
            layout_tools_tab_layout.visibility = View.VISIBLE
            flag_iseditmode = false
        }
    }

    //fun visibilityEditMode() {
    //    if (flag_iseditmode) btn_edit_toolList.visibility = View.VISIBLE
    //    else btn_edit_toolList.visibility = View.GONE
    //}
}