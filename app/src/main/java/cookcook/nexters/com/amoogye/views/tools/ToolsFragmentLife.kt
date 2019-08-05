package cookcook.nexters.com.amoogye.views.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.fragment_tools_life_recycler.*

class ToolsFragmentLife : Fragment() {

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

    private var measureUnitList = arrayListOf<LifeMeasureUnit>(
        LifeMeasureUnit("큰술", "tbsp"),
        LifeMeasureUnit("작은술", "tsp"),
        LifeMeasureUnit("컵", "cup"),
        LifeMeasureUnit("밥숟갈", "15cc"),
        LifeMeasureUnit("베라스푼", "5cc"),
        LifeMeasureUnit("물뚜껑", "7cc"),
        LifeMeasureUnit("소주잔", "50ml")

    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_tools_life_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerAdapter = ToolsRecyclerAdapterLife(context!!, measureUnitList)
        layout_lifeRecyclerView.adapter = recyclerAdapter

        val recyclerManager = LinearLayoutManager(context!!)
        layout_lifeRecyclerView.layoutManager = recyclerManager
        layout_lifeRecyclerView.setHasFixedSize(true)


        btn_edit_toolList.setOnClickListener {
            btn_edit_toolList.visibility = View.GONE
            btn_edit_cancel.visibility = View.VISIBLE
            btn_edit_delete.visibility = View.VISIBLE
            flag_iseditmode = true
            recyclerAdapter.notifyDataSetChanged()
        }

        btn_edit_cancel.setOnClickListener {
            btn_edit_toolList.visibility = View.VISIBLE
            btn_edit_cancel.visibility = View.GONE
            btn_edit_delete.visibility = View.GONE
            flag_iseditmode = false
            recyclerAdapter.notifyDataSetChanged()
        }



    }
}