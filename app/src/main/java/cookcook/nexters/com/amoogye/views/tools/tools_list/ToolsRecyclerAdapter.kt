package cookcook.nexters.com.amoogye.views.tools.tools_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.ITEM_STATUS_ON
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class ToolsRecyclerAdapter(
    private val context: Context,
    measureUnitList: RealmResults<MeasureUnit>,
    autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<MeasureUnit, ToolsRecyclerAdapter.Holder>(measureUnitList, autoUpdate) {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val unitBold = itemView.findViewById<TextView>(R.id.txt_measureUnit_bold)
        private val unitSoft = itemView.findViewById<TextView>(R.id.txt_measureUnit_soft)
        private val toggleOnOff = itemView.findViewById<ToggleButton>(R.id.toggle_tools_item_onoff)

        fun bind(unit: MeasureUnit) {
            unitBold?.text = unit.unitNameBold
            unitSoft?.text = unit.unitNameSoft
            toggleOnOff.isChecked = isToggleOn()
        }

        private fun isToggleOn() : Boolean{
            if (data!![adapterPosition].unitStatus == ITEM_STATUS_ON) return true
            else return false
        }

        fun isToggleChecked() {
            toggleOnOff.setOnCheckedChangeListener{ toggleOnOff, _ ->
                isToggleClicked = true

                val dataId = data!![adapterPosition].unitId
                if(toggleOnOff.isChecked){
                    toggleChecked.add(dataId)
                } else {
                    if (dataId in toggleChecked){
                        toggleChecked.remove(dataId)
                    }
                    toggleNotChecked.add(dataId)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_tools_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data!![position])
        holder.isToggleChecked()
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }
}