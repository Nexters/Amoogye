package cookcook.nexters.com.amoogye.views.tools.tools_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import cookcook.nexters.com.amoogye.views.tools.ToolsFragment
import io.realm.Realm
import io.realm.Sort

import kotlinx.android.synthetic.main.fragment_tools_normal_recycler.*


class ToolsFragmentNormal : Fragment() {

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: ToolsFragment? = null

        fun getInstance(): ToolsFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    ToolsFragment()
            }
            return INSTANCE!!
        }
    }

    // Realm
    private val realm = Realm.getDefaultInstance()
    // 일반 계량 데이터 가져오기
    private val unitList = realm.where(MeasureUnit::class.java).equalTo("unitType", 1).findAll().sort("unitId", Sort.DESCENDING)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_tools_normal_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 리사이클러뷰 어댑터
        val recyclerAdapter =
            ToolsRecyclerAdapter(context!!, unitList, true)
        layout_normalRecyclerView.adapter = recyclerAdapter

        // 변경사항 반영
        unitList.addChangeListener { _-> recyclerAdapter.notifyDataSetChanged() }

        // 레이아웃 매니저
        val recyclerManager = LinearLayoutManager(context!!)
        layout_normalRecyclerView.layoutManager = recyclerManager

        // 리사이클러뷰 사이즈 고정 해제
        layout_normalRecyclerView.setHasFixedSize(false)


        insertData()
    }

    private fun insertData(){
        realm.beginTransaction()

        val newItem = realm.createObject(MeasureUnit::class.java, newId())
        newItem.unitNameBold = "일반계량"
        newItem.unitNameSoft = "일반"
        newItem.unitType = 1

        realm.commitTransaction()
    }
    private fun newId() : Long {
        val maxId = realm.where(MeasureUnit::class.java).max("unitId")
        if (maxId != null){
            return maxId.toLong() + 1
        }
        return 0
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}