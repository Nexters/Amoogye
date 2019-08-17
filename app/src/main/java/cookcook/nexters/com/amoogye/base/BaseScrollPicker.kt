package cookcook.nexters.com.amoogye.base

import android.view.View
import cookcook.nexters.com.amoogye.R
import io.blackbox_vision.wheelview.view.WheelView

class BaseScrollPicker {

    private var items: ArrayList<String> = ArrayList()

    var wheelView: WheelView

    constructor(view: View, items: ArrayList<String>) {
        wheelView = view.findViewById(R.id.wheelView)
        this.items = items
        wheelView.setItems(items)
        wheelView.setInitPosition(2)
        wheelView.setCanLoop(false)

    }

    fun addItem(item: String) {
        this.items.add(item)
    }

    fun addItems(items: ArrayList<String>) {
        this.items = items
    }

    fun visible() {
        wheelView.visibility = View.VISIBLE
    }

    fun inVisible() {
        wheelView.visibility = View.GONE
    }

    fun setColor(color: Int) {
        wheelView.setBackgroundColor(color)
    }
}