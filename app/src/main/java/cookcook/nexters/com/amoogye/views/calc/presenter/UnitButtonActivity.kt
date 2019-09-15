package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.calc.entity.UnitModel
import cookcook.nexters.com.amoogye.views.calc.presenter.adapter.AdapterListener
import cookcook.nexters.com.amoogye.views.calc.presenter.adapter.UnitAdapter

const val GRID_COLUMN = 3

class UnitButtonActivity(view: View, adapterListener: AdapterListener) {
    var adapter: UnitAdapter
    var context: Context = view.context
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview_unit)

    init {
        adapter = UnitAdapter(context, adapterListener)
        recyclerView.layoutManager = GridLayoutManager(context, GRID_COLUMN)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.isLayoutFrozen = true
    }

    fun addItems(item: ArrayList<UnitModel>) {
        recyclerView.isLayoutFrozen = false
        adapter.setAdapterItems(item)
        recyclerView.isLayoutFrozen = true
    }

    fun addItem(item: UnitModel) {
        recyclerView.isLayoutFrozen = false
        adapter.addAdapterItem(item)
        recyclerView.isLayoutFrozen = true
    }

    fun removeAll() {
        adapter.setAdapterItems(arrayListOf())
    }
}