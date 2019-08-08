package cookcook.nexters.com.amoogye.views.tools.add_tools

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AddUtilViewPagerAdapter (fm:FragmentManager, val editTextItemClickListener: OnEditTextClickListener) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                AddUtilNameFragment(editTextItemClickListener)

            }
            1 -> {
                AddUtilVolumeFragment()
            }
            else -> {
                AddUtilCompleteFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}