package cookcook.nexters.com.amoogye.views.tools.tools_list

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
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

    internal lateinit var callback: OnClickEditModeListener

    fun setOnClickEditModeListener (callback: OnClickEditModeListener) {
        this.callback = callback
    }

    interface OnClickEditModeListener {
        fun onInvisibleTabLayout()
        fun onVisibleTabLayout()
        fun onDisableSwipe()
        fun onAbleSwipe()
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
        val unitList = realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_LIFE).findAll()
            .sort("unitId", Sort.DESCENDING)

        val recyclerAdapter =
            ToolsRecyclerAdapterLife(context!!, unitList, true)
        layout_lifeRecyclerView.adapter = recyclerAdapter

        val recyclerManager = LinearLayoutManager(context!!)
        layout_lifeRecyclerView.layoutManager = recyclerManager
        layout_lifeRecyclerView.setHasFixedSize(false)

        unitList.addChangeListener { _ -> recyclerAdapter.notifyDataSetChanged() }

        btn_edit_toolList.setOnClickListener {

            if (areAllItemsDefault()) {
                val toastView = layoutInflater.inflate(R.layout.layout_tool_list_toast,  null)
                val toast = Toast.makeText(context!!, "삭제할 수 있는 계량도구가 없습니다", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 270)
                toast.setView(toastView)
                toast.show()
            } else {
                flagIsEditMode = true
                recyclerAdapter.notifyDataSetChanged()
                btn_edit_toolList.visibility = View.GONE
                btn_edit_cancel.visibility = View.VISIBLE
                btn_edit_delete.visibility = View.VISIBLE
                callback.onInvisibleTabLayout()
                callback.onDisableSwipe()
            }

            if (isToggleClickedLife) {
                changeToggleStatus()
                isToggleClickedLife = false
            }
        }

        btn_edit_cancel.setOnClickListener {
            exitEditMode()
            recyclerAdapter.notifyDataSetChanged()
        }

        btn_edit_delete.setOnClickListener {
            if (checkedList.isNotEmpty()){
                deleteData()
                recyclerAdapter.notifyDataSetChanged()
            }
        }

        test_btn_insert_data.setOnClickListener {
            insertData("Leah", "메롱")
        }


    }

    private fun exitEditMode() {
        btn_edit_toolList.visibility = View.VISIBLE
        btn_edit_cancel.visibility = View.GONE
        btn_edit_delete.visibility = View.GONE
        flagIsEditMode = false
        callback.onVisibleTabLayout()
        callback.onAbleSwipe()
    }

    private fun areAllItemsDefault(): Boolean {

        val maxId = realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_LIFE)
            .max("unitId").toInt()

        if (maxId > NUM_DEFAULT_ITEMS) return false
        return true
    }

    private fun changeToggleStatus() {
        realm.beginTransaction()

        if (toggleNotCheckedLife.size > 0) {
            toggleUnitStatus(ITEM_STATUS_OFF, toggleNotCheckedLife)
        }
        if (toggleCheckedLife.size > 0) {
            toggleUnitStatus(ITEM_STATUS_ON, toggleCheckedLife)
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

            if (itemId in toggleCheckedLife) toggleChecked.remove(itemId)
            if (itemId in toggleNotCheckedLife) toggleNotChecked.remove(itemId)
            val deleteItem = realm.where(MeasureUnit::class.java).equalTo("unitId", itemId).findFirst()!!
            deleteItem.deleteFromRealm()

        }

        checkedList.clear()

        realm.commitTransaction()

        exitEditMode()
    }

    override fun onResume() {
        super.onResume()
        flagIsEditMode = false
        if (isToggleClickedLife) {
            changeToggleStatus()
            isToggleClickedLife = false
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}