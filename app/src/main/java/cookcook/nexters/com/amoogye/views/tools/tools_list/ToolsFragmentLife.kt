package cookcook.nexters.com.amoogye.views.tools.tools_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.*
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_tools_life_recycler.*

class ToolsFragmentLife : Fragment() {

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: ToolsFragmentLife? = null

        fun getInstance(): ToolsFragmentLife {
            if (INSTANCE == null) {
                INSTANCE =
                    ToolsFragmentLife()
            }
            return INSTANCE!!
        }
    }

    lateinit var realm: Realm

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_tools_life_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        realm = Realm.getDefaultInstance()
        val unitList = realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_NORMAL).findAll().sort("unitId", Sort.DESCENDING)

        val recyclerAdapter =
            ToolsRecyclerAdapterLife(context!!, unitList, true)
        layout_lifeRecyclerView.adapter = recyclerAdapter

        val recyclerManager = LinearLayoutManager(context!!)
        layout_lifeRecyclerView.layoutManager = recyclerManager
        layout_lifeRecyclerView.setHasFixedSize(false)

        unitList.addChangeListener { _-> recyclerAdapter.notifyDataSetChanged() }

        btn_edit_toolList.setOnClickListener {
            btn_edit_toolList.visibility = View.GONE
            btn_edit_cancel.visibility = View.VISIBLE
            btn_edit_delete.visibility = View.VISIBLE
            flagIsEditMode = true
            recyclerAdapter.notifyDataSetChanged()

            if (isToggleClicked) {
                changeToggleStatus()
                isToggleClicked = false
            }
        }

        btn_edit_cancel.setOnClickListener {
            exitEditMode()
            recyclerAdapter.notifyDataSetChanged()
        }

        btn_edit_delete.setOnClickListener {
            deleteData()
            if (utilCantDelete){
                Toast.makeText(context!!, "기본 데이터는 삭제할 수 없습니다", Toast.LENGTH_LONG).show()
                utilCantDelete = false
            }
            exitEditMode()
            recyclerAdapter.notifyDataSetChanged()

        }

        test_btn_insert_data.setOnClickListener {
            insertData("yyyy", "메롱")
        }


    }

    private fun exitEditMode() {
        btn_edit_toolList.visibility = View.VISIBLE
        btn_edit_cancel.visibility = View.GONE
        btn_edit_delete.visibility = View.GONE
        flagIsEditMode = false
    }

    private fun changeToggleStatus() {
        realm.beginTransaction()

        if (toggleNotChecked.size > 0) {
            toggleUnitStatus(ITEM_STATUS_OFF, toggleNotChecked)
        }
        if (toggleChecked.size > 0) {
            toggleUnitStatus(ITEM_STATUS_ON, toggleChecked)
        }

        realm.commitTransaction()
    }

    private fun toggleUnitStatus(status: Int, toggleList: MutableSet<Long>) {

        for (itemId in toggleList) {
            val toggleStatus = realm.where(MeasureUnit::class.java).equalTo("unitId", itemId).findFirst()!!
            toggleStatus.unitStatus = status
        }

        toggleList.clear()
    }

    private fun insertData(nameBold: String, nameSoft: String) {
        realm.beginTransaction()

        val newItem = realm.createObject(MeasureUnit::class.java, newId())
        newItem.unitNameBold = nameBold
        newItem.unitNameSoft = nameSoft
        newItem.unitType = 0

        realm.commitTransaction()
    }

    private fun newId(): Long {
        val maxId = realm.where(MeasureUnit::class.java).max("unitId")
        if (maxId != null) {
            return maxId.toLong() + 1
        }
        return 0
    }


    private fun deleteData() {
        realm.beginTransaction()

        for (itemId in checkedList) {
            // 삭제불가 도구 판별 (일단 임의로 조건 설정해놓음)
            if (itemId < 5) {
                utilCantDelete = true
            } else {
                if (itemId in toggleChecked) toggleChecked.remove(itemId)
                if (itemId in toggleNotChecked) toggleNotChecked.remove(itemId)
                val deleteItem = realm.where(MeasureUnit::class.java).equalTo("unitId", itemId).findFirst()!!
                deleteItem.deleteFromRealm()
            }
        }

        checkedList.clear()

        realm.commitTransaction()
    }

    override fun onResume() {
        super.onResume()
        flagIsEditMode = false
        if (isToggleClicked) {
            changeToggleStatus()
            isToggleClicked = false
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}