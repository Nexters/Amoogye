package cookcook.nexters.com.amoogye.views.calc.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cookcook.nexters.com.amoogye.R
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_calc_history.*


class CalcHistoryActivity : AppCompatActivity() {

    lateinit var realm: Realm

    // 최신순 100개의 아이템만 가져오기
    private val LIMIT: Long = 100
    private lateinit var historyList: RealmResults<CalcHistory>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc_history)

        realm = Realm.getDefaultInstance()

        //val historyLastId = realm.where(CalcHistory::class.java).max("historyId").toLong()
        historyList = realm.where(CalcHistory::class.java).findAll()
                .sort("historyId", Sort.DESCENDING)

        val recyclerAdapter = CalcHistoryAdapter(this, historyList, true)
        layout_calc_history_recycler.adapter = recyclerAdapter
        historyList.addChangeListener { _ -> recyclerAdapter.notifyDataSetChanged() }

        layout_calc_history_recycler.layoutManager = LinearLayoutManager(this)
        layout_calc_history_recycler.setHasFixedSize(false)

    }

    private fun insertData(date: Long, before: String, after: String) {
        realm.beginTransaction()

        val newItem = realm.createObject(CalcHistory::class.java, newId())
        newItem.createDate = date
        newItem.calcResultBefore = before
        newItem.calcResultAfter = after

        realm.commitTransaction()
    }

    private fun newId(): Long {
        val maxId = realm.where(CalcHistory::class.java).max("historyId")
        if (maxId != null) {
            return maxId.toLong() + 1
        }
        return 0
    }

}
