package cookcook.nexters.com.amoogye.views.tools.add_tools

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AddUtilViewPagerAdapter (fm:FragmentManager, val editTextItemClickListener: OnEditTextClickListener, val outerTextItemClickListener: OnOuterTextClickListener) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                AddUtilNameFragment.getInstance(editTextItemClickListener, outerTextItemClickListener)
            }
            1 -> {
                AddUtilVolumeFragment.getInstance()
            }
            else -> {
                AddUtilCompleteFragment.getInstance()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    fun instanceInit() {
        AddUtilNameFragment.instanceInit()
        AddUtilVolumeFragment.instanceInit()
        AddUtilCompleteFragment.instanceInit()
    }
}