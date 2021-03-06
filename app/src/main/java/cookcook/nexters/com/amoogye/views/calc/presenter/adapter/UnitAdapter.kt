package cookcook.nexters.com.amoogye.views.calc.presenter.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.calc.entity.UnitModel
import cookcook.nexters.com.amoogye.views.tools.TYPE_LIFE
import cookcook.nexters.com.amoogye.views.tools.TYPE_NORMAL
import kotlinx.android.synthetic.main.item_unit_recyclerview.view.*

class UnitAdapter(val context: Context, onClick: AdapterListener) : RecyclerView.Adapter<UnitAdapter.Holder>() {
    private var unitList: ArrayList<UnitModel> = arrayListOf()

    private var itemClickEvent: AdapterListener = onClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_unit_recyclerview, parent, false)

        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = parent.height / 3
        view.layoutParams = layoutParams

        return Holder(view)
    }

    fun setAdapterItems(list: ArrayList<UnitModel>) {
        unitList.clear()
        for (item in list) {
            unitList.add(item)
        }

        notifyDataSetChanged()
    }

    fun addAdapterItem(item: UnitModel) {
        unitList.add(item)
        notifyDataSetChanged()
    }

    fun getUnitList(): ArrayList<UnitModel> {
        return unitList
    }

    override fun getItemCount(): Int {
        return unitList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(unitList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind (unitModel: UnitModel, context: Context) {
            if (unitModel.viewType == TYPE_NORMAL) {
                itemView.txt_unit_abbreviation.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.item_normal_abbreviation_size))
                itemView.txt_unit_korean.visibility = View.VISIBLE
            } else if (unitModel.viewType == TYPE_LIFE){
                itemView.txt_unit_abbreviation.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.item_life_abbreviation_size))
                itemView.txt_unit_korean.visibility = View.GONE
            }

            itemView.txt_unit_abbreviation.text = unitModel.abbreviation
            itemView.txt_unit_korean.text = unitModel.korean

            itemView.setOnClickListener {
                itemClickEvent.click(unitModel)
            }
        }
    }
}