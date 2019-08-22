package cookcook.nexters.com.amoogye.views.calc.history

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*

class CalcHistoryAdapter(
    private val context: Context,
    calcHistoryList: RealmResults<CalcHistory>,
    autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<CalcHistory, CalcHistoryAdapter.Holder>(calcHistoryList, autoUpdate) {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val createDate = itemView.findViewById<TextView>(R.id.calc_history_date)
        private val resultBefore = itemView.findViewById<TextView>(R.id.calc_history_result_before)
        private val resultAfter = itemView.findViewById<TextView>(R.id.calc_history_result_after)

        fun bind(unit: CalcHistory) {
            val formatter = SimpleDateFormat("yyyy년 M월 d일 E요일", Locale.KOREA)
            createDate?.text = formatter.format(unit.createDate)
            createDate.visibility = isDateSame()
            resultBefore?.text = unit.calcResultBefore
            resultAfter?.text = unit.calcResultAfter
        }


        private fun isDateSame(): Int {

            if (adapterPosition == 0) return View.VISIBLE

            val pastDate = (data!![pastDataId].createDate / 10000000).toInt()
            val nowDate = (data!![adapterPosition].createDate / 10000000).toInt()

            if (pastDate == nowDate) return View.GONE

            if (adapterPosition == data!!.size - 1) pastDataId = 0
            else pastDataId = adapterPosition

            return View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.calc_history_item_layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data!![position])
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }
}