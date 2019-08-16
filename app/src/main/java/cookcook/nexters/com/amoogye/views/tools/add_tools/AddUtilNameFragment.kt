package cookcook.nexters.com.amoogye.views.tools.add_tools

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
    outerTextItemClickListener: OnOuterTextClickListener,
    countEnableTrueListener: OnCountEnableTrueListener,
    countEnableFalseListener: OnCountEnableFalseListener,
    nameUniqueListener: OnNameUniqueListener
) : Fragment() {

    var onItemClickListener: OnEditTextClickListener? = editTextItemClickListener
        private set

    var onOuterItemClickListener: OnOuterTextClickListener? = outerTextItemClickListener
        private set

    var onCountEnableTrueListener: OnCountEnableTrueListener? = countEnableTrueListener
        private set

    var onCountEnableFalseListener: OnCountEnableFalseListener? = countEnableFalseListener
        private set

    var onNameUniqueListener: OnNameUniqueListener? = nameUniqueListener
        private set

    lateinit var callback: OnGetNameEditTextListener

    fun setOnGetNameEditTextListener(callback: OnGetNameEditTextListener) {
        this.callback = callback
    }

    interface OnGetNameEditTextListener {
        fun onGetNameEditText(): String
    }

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: AddUtilNameFragment? = null

        fun getInstance(
            editTextItemClickListener: OnEditTextClickListener,
            outerTextItemClickListener: OnOuterTextClickListener,
            countEnableTrueListener: OnCountEnableTrueListener,
            countEnableFalseListener: OnCountEnableFalseListener,
            nameUniqueListener: OnNameUniqueListener
        ): AddUtilNameFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    AddUtilNameFragment(
                        editTextItemClickListener,
                        outerTextItemClickListener,
                        countEnableTrueListener,
                        countEnableFalseListener,
                        nameUniqueListener
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
                sameNameShowAlert()
                if (p0!!.length > 10) {
                    txt_alert_below_ten_letter.visibility = View.VISIBLE
                    onCountEnableFalseListener?.onCountTextEnableFalse()
                } else {
                    txt_alert_below_ten_letter.visibility = View.INVISIBLE
                    onCountEnableTrueListener?.onCountTextEnable()
                    if (p0.isEmpty()) onCountEnableFalseListener?.onCountTextEnableFalse()
                }

            }

        })

    }

    private fun addUtilCloseKeyboard() {

        val inputMethodManager = context!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    }

    private fun sameNameShowAlert() {
        Log.d("uniqueAlert", ""+onNameUniqueListener!!.onNameUniqueAlert())
        if (onNameUniqueListener!!.onNameUniqueAlert()) txt_alert_same_name.visibility = View.INVISIBLE
        else txt_alert_same_name.visibility = View.VISIBLE
    }
}