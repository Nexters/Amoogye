package cookcook.nexters.com.amoogye.views.tools.tools_list


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import cookcook.nexters.com.amoogye.views.tools.NUM_DEFAULT_ITEMS
import cookcook.nexters.com.amoogye.views.tools.add_tools.MeasureUnitSaveData
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class ToolsRecyclerAdapterLife(
    private val context: Context,
    measureUnitList: RealmResults<MeasureUnit>,
    autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<MeasureUnit, ToolsRecyclerAdapterLife.Holder>(measureUnitList, autoUpdate) {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val unitBold = itemView.findViewById<TextView>(R.id.txt_measureUnit_bold)
        private val unitSoft = itemView.findViewById<TextView>(R.id.txt_measureUnit_soft)
        private val unitCheckBox = itemView.findViewById<CheckBox>(R.id.checkBox_lifeTool_Item)
        private val toggleOnOff = itemView.findViewById<ToggleButton>(R.id.toggle_tools_item_onoff)
        private val imageNewItem = itemView.findViewById<ImageView>(R.id.img_new_item)

        fun bind(unit: MeasureUnit) {
            unitBold?.text = unit.unitNameBold
            unitSoft?.text = unit.unitNameSoft
            unitCheckBox.visibility = getVisibility()
            unitCheckBox.isChecked = false
            toggleOnOff.visibility = getToggleVisibility()
            toggleOnOff.isChecked = isToggleOn()
            imageNewItem.visibility = isItemNew()
        }

        private fun getVisibility(): Int {
            val dataId = data!![adapterPosition].unitId

            if (flagIsEditMode && (dataId > NUM_DEFAULT_ITEMS)) {
                return View.VISIBLE
            }
            return View.GONE
        }

        private fun getToggleVisibility(): Int {
            if (flagIsEditMode) return View.GONE
            return View.VISIBLE
        }

        fun isBoxChecked() {
            unitCheckBox.setOnCheckedChangeListener{ unitCheckBox, _ ->
                val dataId = data!![adapterPosition].unitId
                if(unitCheckBox.isChecked){
                    deleteCheckedList.add(dataId)
                } else {
                    if (dataId in deleteCheckedList){
                        deleteCheckedList.remove(dataId)
                    }
                }

            }
        }

        private fun isToggleOn() : Boolean{
            if (data!![adapterPosition].unitStatus == 1) return true
            else return false
        }

        fun isToggleChecked() {
            toggleOnOff.setOnCheckedChangeListener{ toggleOnOff, _ ->
                isToggleClickedLife = true

                val dataId = data!![adapterPosition].unitId
                if(toggleOnOff.isChecked){
                    toggleCheckedLife.add(dataId)
                } else {
                    if (dataId in toggleCheckedLife){
                        toggleCheckedLife.remove(dataId)
                    } else {
                        toggleNotCheckedLife.add(dataId)
                    }

                }

            }
        }

        private fun isItemNew() : Int {
            val newItemId = MeasureUnitSaveData.getInstance().newItemId
            val dataId = data!![adapterPosition].unitId
            if (dataId == newItemId) {
                MeasureUnitSaveData.getInstance().newItemId = -1
                return View.VISIBLE
            }
            return View.GONE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_tools_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data!![position])
        holder.isBoxChecked()
        holder.isToggleChecked()
    }

    override fun getItemCount(): Int {
        return if (data == null) return 0 else data!!.size
    }
}