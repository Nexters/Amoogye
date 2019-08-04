package cookcook.nexters.com.amoogye.views.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.fragment_tools_normal_recycler.*


class ToolsFragmentLife: Fragment() {

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
        return inflater.inflate(R.layout.fragment_tools_normal_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerAdapter = ToolsRecyclerAdapterLife(context!!, measureUnitList)
        layout_normalRecyclerView.adapter = recyclerAdapter

        val recyclerManager = LinearLayoutManager(context!!)
        layout_normalRecyclerView.layoutManager = recyclerManager
        layout_normalRecyclerView.setHasFixedSize(true)
    }
}