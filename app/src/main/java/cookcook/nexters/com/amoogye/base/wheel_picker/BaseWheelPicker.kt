package cookcook.nexters.com.amoogye.base.wheel_picker

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R

class BaseWheelPicker(view: View, data: ArrayList<String>, onClick: (value: String) -> Unit) {

    val rcvWheelPicker: RecyclerView = view.findViewById(R.id.rcv_wheel_picker)
    val context: Context = view.context
    val adapter: WheelPickerAdapter

    init {
        val wheelPickerHeight = ScreenUtils.pxToDp(context, ScreenUtils.getScreenHeight(context) / 2)
        rcvWheelPicker.setPadding(0, wheelPickerHeight, 0, wheelPickerHeight)
        val fontSize = wheelPickerHeight / 10

        adapter = WheelPickerAdapter(fontSize, context).apply {
            setData(data)
            callback = object : WheelPickerAdapter.Callback {
                override fun onItemClicked(view: View) {
                    rcvWheelPicker.smoothScrollToPosition(rcvWheelPicker.getChildLayoutPosition(view))
                }
            }
        }

        rcvWheelPicker.layoutManager = WheelPickerLayoutManager(context).apply {
            callback = object : WheelPickerLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    adapter.setPosition(layoutPosition)
                    onClick(adapter.getData()[layoutPosition])
                }
            }
        }

        rcvWheelPicker.adapter = adapter
    }

    fun wheelPickerDataChange(data: ArrayList<String>) {
        adapter.setData(data)
    }

    fun wheelPickerAddDate(data: String) {
        adapter.addData(data)
    }

}