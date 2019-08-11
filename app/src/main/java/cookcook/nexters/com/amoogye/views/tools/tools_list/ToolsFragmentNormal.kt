package cookcook.nexters.com.amoogye.views.tools.tools_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.*
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.Sort

import kotlinx.android.synthetic.main.fragment_tools_normal_recycler.*


class ToolsFragmentNormal : Fragment() {

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: ToolsFragmentNormal? = null

        fun getInstance(): ToolsFragmentNormal {
            if (INSTANCE == null) {
                INSTANCE =
                    ToolsFragmentNormal()
            }
            return INSTANCE!!
        }
    }

    lateinit var realm: Realm
    lateinit var unitList: RealmResults<MeasureUnit>

    override fun onStart() {
        super.onStart()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_tools_normal_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        realm = Realm.getDefaultInstance()

        unitList = realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_LIFE).findAll()
            .sort("unitId", Sort.DESCENDING)

        // 리사이클러뷰 어댑터
        val recyclerAdapter =
            ToolsRecyclerAdapter(context!!, unitList, true)
        layout_normalRecyclerView.adapter = recyclerAdapter

        // 변경사항 반영
        unitList.addChangeListener { _ -> recyclerAdapter.notifyDataSetChanged() }

        // 레이아웃 매니저
        val recyclerManager = LinearLayoutManager(context!!)
        layout_normalRecyclerView.layoutManager = recyclerManager

        // 리사이클러뷰 사이즈 고정 해제
        layout_normalRecyclerView.setHasFixedSize(false)

        changeToggleStatus()


    }

    private fun changeToggleStatus() {
        realm.beginTransaction()

        if (toggleNotChecked.size > 0) {
            toggleUnitStatus(ITEM_STATUS_OFF, toggleNotChecked)
        }
        if (toggleChecked.size > 0) {
            toggleUnitStatus(ITEM_STATUS_ON, toggleChecked)
        }

        toggleChecked.clear()
        toggleNotChecked.clear()

        realm.commitTransaction()
    }

    private fun toggleUnitStatus(status: Int, toggleList: MutableList<Long>) {
        for (itemId in toggleList) {
            val toggleStatus = realm.where(MeasureUnit::class.java).equalTo("unitId", itemId).findFirst()!!
            toggleStatus.unitStatus = status
        }
    }


    private fun insertData() {
        realm.beginTransaction()

        val newItem = realm.createObject(MeasureUnit::class.java, newId())
        newItem.unitNameBold = "일반계량"
        newItem.unitNameSoft = "일반"
        newItem.unitType = TYPE_NORMAL

        realm.commitTransaction()
    }

    private fun newId(): Long {
        val maxId = realm.where(MeasureUnit::class.java).max("unitId")
        if (maxId != null) {
            return maxId.toLong() + 1
        }
        return 0
    }

    override fun onStop() {
        realm.close()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}