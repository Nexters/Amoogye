package cookcook.nexters.com.amoogye.views.tools

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AddUtilViewPagerAdapter (fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> { AddUtilNameFragment() }
            1 -> {AddUtilVolumeFragment()}
            else -> { AddUtilCompleteFragment() }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}