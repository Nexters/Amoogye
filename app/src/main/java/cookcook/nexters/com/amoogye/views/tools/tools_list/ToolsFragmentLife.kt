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
import cookcook.nexters.com.amoogye.views.tools.flagIsEditMode
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
        val unitList = realm.where(MeasureUnit::class.java).equalTo("unitType", 0).findAll().sort("unitId", Sort.DESCENDING)

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
        }

        btn_edit_cancel.setOnClickListener {
            btn_edit_toolList.visibility = View.VISIBLE
            btn_edit_cancel.visibility = View.GONE
            btn_edit_delete.visibility = View.GONE
            flagIsEditMode = false
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    private fun insertData(){
        realm.beginTransaction()

        val newItem = realm.createObject(MeasureUnit::class.java, newId())
        newItem.unitNameBold = "생활계량"
        newItem.unitNameSoft = "150ml"
        newItem.unitType = 0

        realm.commitTransaction()
    }
    private fun newId() : Long {
        val maxId = realm.where(MeasureUnit::class.java).max("unitId")
        if (maxId != null){
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