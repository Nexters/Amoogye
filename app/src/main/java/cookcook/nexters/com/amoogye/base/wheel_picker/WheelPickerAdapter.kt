package cookcook.nexters.com.amoogye.base.wheel_picker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R

class WheelPickerAdapter(val fontSize: Int, val context: Context) : RecyclerView.Adapter<WheelPickerAdapter.WheelPickerViewHolder>() {

    private val data: ArrayList<String> = ArrayList()

    private var currentPosition: Int = 0

    fun setPosition(position: Int) {
        this.currentPosition = position
        notifyDataSetChanged()
    }

    var callback: Callback? = null

    private val clickListener = View.OnClickListener { v ->
        v?.let {
            callback?.onItemClicked(it)
        }
    }

    fun setData(data: ArrayList<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: String) {
        this.data.add(data)
    }

    fun getData() = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WheelPickerViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_wheel_picker, parent, false)

        itemView.setOnClickListener(clickListener)

        return WheelPickerViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: WheelPickerViewHolder, position: Int) {
        holder.bind(data[position])
    }


    inner class WheelPickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItemName: TextView = itemView.findViewById(R.id.txt_item_wheel_picker)

        fun bind(value: String) {
            txtItemName.text = value
            txtItemName.setPadding(0,fontSize, 0, fontSize)
        }
    }

    interface Callback {
        fun onItemClicked(view: View)
    }
}