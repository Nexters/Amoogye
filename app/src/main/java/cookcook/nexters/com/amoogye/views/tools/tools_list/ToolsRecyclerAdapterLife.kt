package cookcook.nexters.com.amoogye.views.tools.tools_list


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.LifeMeasureUnit
import cookcook.nexters.com.amoogye.views.tools.flagIsEditMode

class ToolsRecyclerAdapterLife (private val context: Context, private val measureUnitList: ArrayList<LifeMeasureUnit>) :
    RecyclerView.Adapter<ToolsRecyclerAdapterLife.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val unitBold = itemView.findViewById<TextView>(R.id.txt_measureUnit_bold)
        private val unitSoft = itemView.findViewById<TextView>(R.id.txt_measureUnit_soft)
        private val unitCheckBox = itemView.findViewById<CheckBox>(R.id.checkBox_lifeTool_Item)
        private val toggleOnOff = itemView.findViewById<ToggleButton>(R.id.toggle_tools_item_onoff)

        fun bind(measureUnit: LifeMeasureUnit) {
            unitBold?.text = measureUnit.measureUnitBold
            unitSoft?.text = measureUnit.measureUnitSoft
            unitCheckBox.visibility = getVisibility()
            toggleOnOff.visibility = getToggleVisibility()
        }

        private fun getVisibility(): Int {
            if (flagIsEditMode) return View.VISIBLE
            return View.GONE
        }

        private fun getToggleVisibility(): Int {
            if (flagIsEditMode) return View.GONE
            return View.VISIBLE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_tools_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(measureUnitList[position])
    }

    override fun getItemCount(): Int {
        return measureUnitList.size
    }
}