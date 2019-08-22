package cookcook.nexters.com.amoogye.views.calc.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R

class CalcHistoryAdapter (private val context: Context, private val calcHistoryList:ArrayList<CalcHistory>) :
    RecyclerView.Adapter<CalcHistoryAdapter.Holder>() {
    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val createDate = itemView.findViewById<TextView>(R.id.calc_history_date)
        private val resultBefore = itemView.findViewById<TextView>(R.id.calc_history_result_before)
        private val resultAfter = itemView.findViewById<TextView>(R.id.calc_history_result_after)

        fun bind(unit : CalcHistory) {
            createDate?.text = unit.createDate.toString()
            createDate.visibility = isDateSame()
            resultBefore?.text = unit.calcResultBefore
            resultAfter?.text = unit.calcResultAfter
        }

        private fun isDateSame() : Int{
            if (historyFlag) return View.VISIBLE
            return View.GONE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.calc_history_item_layout, parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(calcHistoryList[position])
    }

    override fun getItemCount(): Int {
        return calcHistoryList.size
    }
}