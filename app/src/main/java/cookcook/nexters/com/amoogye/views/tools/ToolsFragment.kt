package cookcook.nexters.com.amoogye.views.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.fragment_tools.*


class ToolsFragment: Fragment() {

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

    private var measureUnitList = arrayListOf<MeasureUnit>(
        MeasureUnit("cc", "씨씨"),
        MeasureUnit("ml", "밀리미터"),
        MeasureUnit("L", "리터"),
        MeasureUnit("mg", "밀리그램"),
        MeasureUnit("kg", "킬로그램"),
        MeasureUnit("큰술", "tbsp"),
        MeasureUnit("작은술", "tsp"),
        MeasureUnit("컵", "cup"),
        MeasureUnit("밥숟갈", "15cc"),
        MeasureUnit("베라스푼", "5cc"),
        MeasureUnit("물뚜껑", "7cc"),
        MeasureUnit("소주잔", "50ml")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_tools, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerAdapter = ToolsRecyclerAdapter(context!!, measureUnitList)
        layout_toolsRecyclerView.adapter = recyclerAdapter

        val recyclerManager = LinearLayoutManager(context!!)
        layout_toolsRecyclerView.layoutManager = recyclerManager
        layout_toolsRecyclerView.setHasFixedSize(true)
    }
}