package cookcook.nexters.com.amoogye.views.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R

class OnboardingFragment2: Fragment() {
    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: OnboardingFragment2? = null

        fun getInstance(): OnboardingFragment2 {
            if (INSTANCE == null) {
                INSTANCE =
                    OnboardingFragment2()
            }
            return INSTANCE!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_onboarding_page_2, container, false)

    }
}