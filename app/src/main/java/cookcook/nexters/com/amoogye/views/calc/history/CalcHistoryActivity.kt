package cookcook.nexters.com.amoogye.views.calc.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.activity_calc_history.*


class CalcHistoryActivity : AppCompatActivity() {


    private var historyList = arrayListOf<CalcHistory>(
        CalcHistory(0, 20190811, "간장 20ml", "11 밥숟가락"),
        CalcHistory(0, 20190811, "간장 201ml", "111 밥숟가락"),
        CalcHistory(0, 20190811, "간장 202ml", "112 밥숟가락"),
        CalcHistory(0, 20190812, "간장 120ml", "12 밥숟가락"),
        CalcHistory(1, 20190813, "간장 220ml", "13 밥숟가락"),
        CalcHistory(2, 20190814, "간장 320ml", "14 밥숟가락"),
        CalcHistory(3, 20190815, "간장 420ml", "15 밥숟가락"),
        CalcHistory(4, 20190816, "간장 520ml", "16 밥숟가락"),
        CalcHistory(5, 20190817, "간장 620ml", "17 밥숟가락"),
        CalcHistory(6, 20190818, "간장 720ml", "18 밥숟가락"),
        CalcHistory(7, 20190819, "간장 820ml", "19 밥숟가락")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc_history)


        layout_calc_history_recycler.adapter = CalcHistoryAdapter(this, historyList)

        layout_calc_history_recycler.layoutManager = LinearLayoutManager(this)

        layout_calc_history_recycler.setHasFixedSize(true)
    }
}
