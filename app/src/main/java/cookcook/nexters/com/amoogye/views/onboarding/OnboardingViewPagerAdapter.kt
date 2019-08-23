package cookcook.nexters.com.amoogye.views.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class OnboardingViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                OnboardingFragment1.getInstance()
            }
            1 -> {
                OnboardingFragment2.getInstance()
            }
            2 -> {
                OnboardingFragment3.getInstance()
            }
            3 -> {
                OnboardingFragment4.getInstance()
            }
            else -> {
                OnboardingFragment5.getInstance()
            }
        }
    }

    override fun getCount(): Int {
        return 5
    }

    fun instanceInit() {

    }
}