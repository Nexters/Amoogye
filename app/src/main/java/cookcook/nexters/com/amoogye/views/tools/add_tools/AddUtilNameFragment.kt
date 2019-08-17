package cookcook.nexters.com.amoogye.views.tools.add_tools

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.fragment_addutil_1_name_util.*
import kotlinx.android.synthetic.main.fragment_addutil_1_name_util.layout_outer_top


class AddUtilNameFragment(
    editTextItemClickListener: OnEditTextClickListener,
    outerTextItemClickListener: OnEditTextClickListener,
    countEnableListener: OnCountEnableListener,
    countEnableFalseListener: OnCountEnableListener
) : Fragment() {

    var onItemClickListener: OnEditTextClickListener? = editTextItemClickListener
        private set

    var onOuterItemClickListener: OnEditTextClickListener? = outerTextItemClickListener
        private set

    var onCountEnableListener: OnCountEnableListener? = countEnableListener
        private set

    var onCountEnableFalseListener: OnCountEnableListener? = countEnableFalseListener
        private set


    interface OnGetNameEditTextListener {
        fun onGetNameEditText(): String
        fun onUniqueNameShowAlert()
    }

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: AddUtilNameFragment? = null

        fun getInstance(
            editTextItemClickListener: OnEditTextClickListener,
            outerTextItemClickListener: OnEditTextClickListener,
            countEnableListener: OnCountEnableListener,
            countEnableFalseListener: OnCountEnableListener
        ): AddUtilNameFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    AddUtilNameFragment(
                        editTextItemClickListener,
                        outerTextItemClickListener,
                        countEnableListener,
                        countEnableFalseListener
                    )
            }
            return INSTANCE!!
        }

        fun instanceInit() {
            INSTANCE = null
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_addutil_1_name_util, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edit_txt_name_util.setOnTouchListener { _, motionEvent ->
            motionEvent.let {
                if (it.action == MotionEvent.ACTION_DOWN) {
                    onItemClickListener?.onClickEditText()
                }
                false
            }
        }

        layout_outer_top.setOnClickListener {
            onOuterItemClickListener?.onClickOuterText()
            addUtilCloseKeyboard()
        }

        edit_txt_name_util.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, count: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, count: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                txt_alert_same_name.visibility = View.INVISIBLE
                btn_addutil_name_complete.visibility = View.VISIBLE
                if (p0!!.length > 10) {
                    txt_alert_below_ten_letter.visibility = View.VISIBLE
                    onCountEnableFalseListener?.onCountTextEnableFalse()
                } else {
                    txt_alert_below_ten_letter.visibility = View.INVISIBLE
                    onCountEnableListener?.onCountTextEnableTrue()
                    if (p0.isEmpty()) {
                        onCountEnableFalseListener?.onCountTextEnableFalse()
                        btn_addutil_name_complete.visibility = View.INVISIBLE
                    }
                }

            }

        })

        btn_addutil_name_complete.setOnClickListener {
            edit_txt_name_util.text.clear()
        }

    }

    private fun addUtilCloseKeyboard() {

        val inputMethodManager = context!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    }

}



