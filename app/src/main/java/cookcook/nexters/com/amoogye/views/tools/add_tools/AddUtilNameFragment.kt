package cookcook.nexters.com.amoogye.views.tools.add_tools

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.ToolsFragment
import kotlinx.android.synthetic.main.fragment_addutil_1_name_util.*
import kotlinx.android.synthetic.main.fragment_addutil_1_name_util.layout_outer_top


class AddUtilNameFragment(
    editTextItemClickListener: OnEditTextClickListener,
    outerTextItemClickListener: OnOuterTextClickListener
) : Fragment() {

    var onItemClickListener: OnEditTextClickListener? = editTextItemClickListener
        private set

    var onOuterItemClickListener: OnOuterTextClickListener? = outerTextItemClickListener
        private set

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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_addutil_1_name_util, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edit_txt_name_util.setOnClickListener {
            onItemClickListener?.onClickEditText()
        }

        layout_outer_top.setOnClickListener {
            onOuterItemClickListener?.onClickOuterText()
            addUtilCloseKeyboard()
        }

    }


    private fun addUtilCloseKeyboard() {

        val inputMethodManager = context!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    }
}