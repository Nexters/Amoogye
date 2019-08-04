package cookcook.nexters.com.amoogye.views.tools

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ToolsViewPageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> { ToolsFragmentNormal() }
            else -> { ToolsFragmentLife() }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "일반 계량도구"
            else -> "생활 계량도구"
        }
    }
}