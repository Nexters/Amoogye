package cookcook.nexters.com.amoogye.views.tools


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R

class ToolsRecyclerAdapterLife (private val context: Context, private val measureUnitList: ArrayList<LifeMeasureUnit>) :
    RecyclerView.Adapter<ToolsRecyclerAdapterLife.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val unitBold = itemView.findViewById<TextView>(R.id.txt_measureUnit_bold)
        private val unitSoft = itemView.findViewById<TextView>(R.id.txt_measureUnit_soft)

        fun bind(measureUnit: LifeMeasureUnit) {
            unitBold?.text = measureUnit.measureUnitBold
            unitSoft?.text = measureUnit.measureUnitSoft
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