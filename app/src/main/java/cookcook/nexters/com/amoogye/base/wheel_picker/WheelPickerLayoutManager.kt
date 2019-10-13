package cookcook.nexters.com.amoogye.base.wheel_picker

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.sqrt

class WheelPickerLayoutManager(context: Context?) : LinearLayoutManager(context) {
    init {
        orientation = RecyclerView.VERTICAL
    }

    var callback: OnItemSelectedListener? = null
    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        recyclerView = view!!

        LinearSnapHelper().attachToRecyclerView(recyclerView)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        scaleDownView()
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return if (orientation == RecyclerView.VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            scaleDownView()
            scrolled
        } else {
            0
        }
    }

    private fun scaleDownView() {
        val mid = height / 2.0f
        for (i in 0 until childCount) {

            val child = getChildAt(i)!!
            val childMid = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.0f
            val distanceFromCenter = abs(mid - childMid)

            val scale = 1 - sqrt((distanceFromCenter / height).toDouble()).toFloat() * 0.66f

            child.scaleX = scale
            child.scaleY = scale
            child.alpha = scale
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        if (state == RecyclerView.SCROLL_STATE_IDLE) {

            val recyclerViewCenterY = getRecyclerViewCenterY()
            var minDistance = recyclerView.height
            var position = -1
            for (i in 0 until recyclerView.childCount) {
                val child = recyclerView.getChildAt(i)
                val childCenterY = getDecoratedTop(child) + (getDecoratedBottom(child) - getDecoratedTop(child)) / 2
                val newDistance = abs(childCenterY - recyclerViewCenterY)
                if (newDistance < minDistance) {
                    minDistance = newDistance
                    position = recyclerView.getChildLayoutPosition(child)
                }
            }

            callback?.onItemSelected(position)
        }
    }

    private fun getRecyclerViewCenterY(): Int {
        return (recyclerView.top - recyclerView.bottom) / 2 + recyclerView.bottom
    }

    interface OnItemSelectedListener {
        fun onItemSelected(layoutPosition: Int)
    }
}