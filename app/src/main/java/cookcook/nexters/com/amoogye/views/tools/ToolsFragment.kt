package cookcook.nexters.com.amoogye.views.tools

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import cookcook.nexters.com.amoogye.utils.SharedPreferences
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.add_tools.AddUtilActivity
import cookcook.nexters.com.amoogye.views.tools.tools_list.ToolsFragmentLife
import cookcook.nexters.com.amoogye.views.tools.tools_list.ToolsViewPageAdapter
import kotlinx.android.synthetic.main.fragment_tools.*


class ToolsFragment : Fragment() , ToolsFragmentLife.OnClickEditModeListener {

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: ToolsFragment? = null

        fun getInstance(): ToolsFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    ToolsFragment()
            }
            return INSTANCE!!
        }
    }

    override fun onAttachFragment(fragment : Fragment){
        if(fragment is ToolsFragmentLife) {
            fragment.setOnClickEditModeListener(this)
        }
    }


    override fun onInvisibleTabLayout() {
        layout_tools_tab_layout.visibility = View.INVISIBLE
    }

    override fun onVisibleTabLayout() {
        layout_tools_tab_layout.visibility = View.VISIBLE
    }

    override fun onDisableSwipe() {
        layout_Tools_viewPager.setSwipePagingEnabled(false)
    }

    override fun onAbleSwipe() {
        layout_Tools_viewPager.setSwipePagingEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_tools, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val toolsDim = SharedPreferences(context!!)
        if(!toolsDim.tools_dim) {
            layout_tools_dimScreen.visibility = View.GONE
        }


        val toolsFragmentAdapter =
            ToolsViewPageAdapter(childFragmentManager)
        layout_Tools_viewPager.adapter = toolsFragmentAdapter

        layout_tools_tab_layout.setupWithViewPager(layout_Tools_viewPager)
        layout_Tools_viewPager.setSwipePagingEnabled(true)

        layout_tools_dimScreen.setOnClickListener {
            layout_tools_dimScreen.visibility = View.GONE
            img_popup_message.visibility = View.GONE
            toolsDim.tools_dim = false
        }

        btn_convert_add_util_activity.setOnClickListener {
            val intent = Intent(context, AddUtilActivity::class.java)
            startActivity(intent)
        }
    }
}

