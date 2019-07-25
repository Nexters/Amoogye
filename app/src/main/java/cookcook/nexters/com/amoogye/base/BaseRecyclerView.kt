package cookcook.nexters.com.amoogye.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerView {
    abstract class Adapter<ITEM : Any, B : ViewDataBinding>(
        @LayoutRes private val layoutResId: Int,
        private val bindingVariableId: Int? = null
    ) : RecyclerView.Adapter<Adapter.ViewHolder<B>>() {
        private val items = mutableListOf<ITEM>()

        var a : View ? =null
        override fun onViewRecycled(holder: ViewHolder<B>) {
            super.onViewRecycled(holder)
            //memory leak 방지
            a?.setOnClickListener(null)
        }

        // Create (All)
        fun replaceAll(items: List<ITEM>?) {
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)
                    notifyDataSetChanged()
                }
            }
        }

        // Create (Item) (맨 뒤에 추가하는 경우 index = list.size)
        fun createItem(item: ITEM?, index: Int) {
            item?.let {
                this.items.run {
                    add(index, item)
                    notifyItemInserted(index)
                }
            }
        }

        // Update
        fun replaceItem(item: ITEM?, index: Int) {
            item?.let {
                this.items.run {
                    this[index] = item
                    notifyItemChanged(index)
                }
            }
        }

        // Create (All)
        fun deleteAll() {
            this.items.run {
                clear()
                notifyDataSetChanged()
            }
        }

        // Delete
        fun deleteItem(item: ITEM?, index: Int) {
            item?.let {
                this.items.run {
                    this.removeAt(index)
                    notifyItemRemoved(index)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object : ViewHolder<B>(
            layoutResId = layoutResId,
            parent = parent,
            bindingVariableId = bindingVariableId) {}

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
            holder.onBindViewHolder(items[position])
        }

        abstract class ViewHolder<B : ViewDataBinding>(
            @LayoutRes layoutResId: Int,
            parent: ViewGroup,
            private val bindingVariableId: Int?) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)) {
            private var binding: B = DataBindingUtil.bind(itemView)!!

            fun onBindViewHolder(item: Any?) {
                try {
                    bindingVariableId?.let {
                        binding.setVariable(it, item)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}

// 사용 예
// // 1. layoutManager 설정
// rv_bj_list.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
//
// // 2. adapter 선언
// (Bj : data class / ItemBjBInding : databinding Class)
// layoutResId : 레이아웃 리소스
// bindingVariableId : BR index
// var bjAdapter = object : BaseRecyclerView.Adapter<Bj, ItemBjBinding>(
//         layoutResId = R.layout.item_bj,
//         bindingVariableId = BR.icContent
// ) {
//     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemBjBinding> {
//         return super.onCreateViewHolder(parent, viewType).apply {
//              // onCreatViewHolder 에서 작업할 내용 추가
//         }
//     }
//
//     override fun onBindViewHolder(holder: ViewHolder<ItemBjBinding>, position: Int) {
//         super.onBindViewHolder(holder, position)
//         // binding 이후 처리할 작업 처리 (아이템 클릭 리스너 등)
//         holder.itemView.setOnClickListener {
//             var intent = Intent(activity!!, MypageActivity::class.java)
//             intent.putExtra("name", defaultVM.bjList[position].name)
//             startActivity(intent)
//             activity!!.finish()
//         }
//     }
// }
//
// // 3. 데이터 세팅 및 setAdapter
// defaultVM.bjList = getDefaultBjList()
// bjAdapter.replaceAll(defaultVM.bjList)
// rv_bj_list.adapter = bjAdapter