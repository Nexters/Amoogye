package cookcook.nexters.com.amoogye.views.tools.add_tools

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import kotlinx.android.synthetic.main.fragment_addutil_2_volume.*

enum class ButtonSelectedState {
    TOOL,
    INTEGER,
    DECIMALPOINT
}


class AddUtilVolumeFragment : Fragment() {

    lateinit var state: ButtonSelectedState
    lateinit var picker: BaseScrollPicker
    lateinit var getView:View

    private val toolList: ArrayList<String> = arrayListOf()
    private val integerList: ArrayList<String> = arrayListOf()
    private val decimalPointList: ArrayList<String> = arrayListOf()

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

    private fun initializeLists() {
        /* TODO: toolList의 값을 디비에서 가져와서 갱신하자. */
        toolList.add("종이컵")
        toolList.add("소주컵")
        toolList.add("맥주컵")
        toolList.add("머그컵")
        toolList.add("양치컵")

        for (i in 1..50) {
            integerList.add(i.toString())
        }

        for (i in 0..9) {
            decimalPointList.add(i.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getView = view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeLists()

        btn_add_util_tool.setOnClickListener{
            state = ButtonSelectedState.TOOL
            picker = BaseScrollPicker(getView, toolList)
            layout_container_scroll_picker.visibility = View.VISIBLE
            btn_add_util_confirm.visibility = View.VISIBLE
            (activity!!.findViewById<Button>(R.id.btn_add_util_next_page) as View).visibility = View.GONE
        }

        btn_add_util_integer.setOnClickListener {
            state = ButtonSelectedState.INTEGER
            picker = BaseScrollPicker(getView, integerList)
            btn_add_util_confirm.visibility = View.VISIBLE
            layout_container_scroll_picker.visibility = View.VISIBLE
            (activity!!.findViewById<Button>(R.id.btn_add_util_next_page) as View).visibility = View.GONE
        }

        btn_add_util_decimal_point.setOnClickListener {
            state = ButtonSelectedState.DECIMALPOINT
            picker = BaseScrollPicker(getView, decimalPointList)
            btn_add_util_confirm.visibility = View.VISIBLE
            layout_container_scroll_picker.visibility = View.VISIBLE
            (activity!!.findViewById<Button>(R.id.btn_add_util_next_page) as View).visibility = View.GONE
        }

        btn_add_util_confirm.setOnClickListener {
            layout_container_scroll_picker.visibility = View.GONE
            btn_add_util_confirm.visibility = View.INVISIBLE
            (activity!!.findViewById<Button>(R.id.btn_add_util_next_page) as View).visibility = View.VISIBLE

            when (state) {
                ButtonSelectedState.TOOL -> btn_add_util_tool.text = toolList[picker.getItem()]
                ButtonSelectedState.INTEGER -> btn_add_util_integer.text = integerList[picker.getItem()]
                ButtonSelectedState.DECIMALPOINT -> btn_add_util_decimal_point.text = decimalPointList[picker.getItem()]
            }
        }

        // 임의로 값넣기
        btn_add_util_tool.text = toolList[0]
        btn_add_util_integer.text = integerList[0]
        btn_add_util_decimal_point.text = decimalPointList[0]
    }

    private fun addUtilCloseKeyboard() {
        val inputMethodManager = context!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}