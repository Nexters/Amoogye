package cookcook.nexters.com.amoogye.views.calc.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class CalcHistoryAdapter (private val context: Context, calcHistoryList: RealmResults<CalcHistory>, autoUpdate:Boolean) :
    RealmRecyclerViewAdapter<CalcHistory, CalcHistoryAdapter.Holder>(calcHistoryList, autoUpdate) {
    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val createDate = itemView.findViewById<TextView>(R.id.calc_history_date)
        private val resultBefore = itemView.findViewById<TextView>(R.id.calc_history_result_before)
        private val resultAfter = itemView.findViewById<TextView>(R.id.calc_history_result_after)

        fun bind(unit : CalcHistory) {
            createDate?.text = unit.createDate.toString()
            resultBefore?.text = unit.calcResultBefore
            resultAfter?.text = unit.calcResultAfter
        }

        private fun isDateSame() : Int{

            val nowId = data!![adapterPosition].historyId

            if (nowId == 0L){
                return View.VISIBLE
            } else {
                val past = data!![adapterPosition-1].historyId
                if (past == nowId) return View.GONE
                return View.VISIBLE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.calc_history_item_layout, parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data!![position])
    }

    override fun getItemCount(): Int {
        return if(data == null) 0 else data!!.size
    }
}