package cookcook.nexters.com.amoogye.base

import android.view.View
import android.widget.RelativeLayout
import cookcook.nexters.com.amoogye.R

class BaseNumberButton() {
    private var numberButtonList = arrayOf(
        R.id.layout_btn_0,
        R.id.layout_btn_1,
        R.id.layout_btn_2,
        R.id.layout_btn_3,
        R.id.layout_btn_4,
        R.id.layout_btn_5,
        R.id.layout_btn_6,
        R.id.layout_btn_7,
        R.id.layout_btn_8,
        R.id.layout_btn_9,
        R.id.layout_btn_dot,
        R.id.layout_btn_delete
    )

    private var numberButtonTextList = arrayOf(
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        ".",
        "delete"
    )

    constructor(view: View, onClick:(number: String) -> Unit, useDot: Boolean = true): this() {
        val emptyLayout = view.findViewById<RelativeLayout>(R.id.layout_non_dot)
        if (useDot) {
            emptyLayout.visibility = View.GONE
        } else {
            emptyLayout.visibility = View.VISIBLE

        }

        for (x in 0 until numberButtonList.size) {
            var layout = view.findViewById<RelativeLayout>(numberButtonList[x])
            layout.setOnClickListener {
                onClick(numberButtonTextList[x])
            }
            if (x == 10) {
                if (useDot) {
                    layout.visibility = View.VISIBLE
                } else {
                    layout.visibility = View.GONE
                }
            }
        }
    }
}