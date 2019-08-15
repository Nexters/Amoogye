package cookcook.nexters.com.amoogye.views.calc.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.calc.entity.NormalUnitModel
import kotlinx.android.synthetic.main.item_unit_recyclerview.view.*

class UnitAdapter(val context: Context) : RecyclerView.Adapter<UnitAdapter.Holder>() {
    private lateinit var unitList: ArrayList<NormalUnitModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_unit_recyclerview, parent, false)

        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = parent.height / 3
        view.layoutParams = layoutParams

        return Holder(view)
    }

    fun setAdapterItems(list: ArrayList<NormalUnitModel>) {
        unitList = list
    }

    fun addAdapterItem(item: NormalUnitModel) {
        unitList.add(item)
    }

    override fun getItemCount(): Int {
        return unitList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(unitList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind (normalUnitModel: NormalUnitModel, context: Context) {
            itemView.txt_unit_abbreviation.text = normalUnitModel.abbreviation
            itemView.txt_unit_korean.text = normalUnitModel.korean
        }
    }
}