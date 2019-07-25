package cookcook.nexters.com.amoogye.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.base_dialog.*

class DefaultDialog(context: Context, private val ok : () -> Unit, private val  cancel : () -> Unit) : Dialog(context) {
    fun callFunction() {
        this.setCancelable(false)   // 배경 클릭해도 다이얼로그가 꺼지지 않음
        show()                      // 커스텀 다이얼로그를 노출한다.
    }

    // 테마 설정
    init {
        // 액티비티의 타이틀바를 숨긴다.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    open fun setInit(resId: Int, type: Int) {
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        setContentView(resId)

        btn_ok.setOnClickListener{ok}
        btn_cancel.setOnClickListener{cancel}

        // '확인' 버튼만 존재
        if (type == 1) {
            btn_cancel.visibility = View.GONE
            btn_ok.setBackgroundResource(R.drawable.dialog_default_corner)
        }

        // '확인' / '취소' 버튼 존재
        else if (type == 2) {

        }
    }

    // 타이틀 / 서브타이틀 설정
    fun setTitle(titleText: String, subTitle: String = "") {
        if(titleText == "") tv_title.visibility = View.GONE else tv_title.text = titleText
        if(subTitle == "") tv_sub_title.visibility = View.GONE else tv_sub_title.text = subTitle
    }
}