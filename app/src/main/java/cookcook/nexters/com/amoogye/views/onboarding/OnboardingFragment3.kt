package cookcook.nexters.com.amoogye.views.onboarding

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.fragment_onboarding_page_3.*

class OnboardingFragment3: Fragment() {
    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: OnboardingFragment3? = null

        fun getInstance(): OnboardingFragment3 {
            if (INSTANCE == null) {
                INSTANCE =
                    OnboardingFragment3()
            }
            return INSTANCE!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_onboarding_page_3, container, false)

    }

    lateinit var path: Uri
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        path = Uri.parse("android.resource://" + view.context.packageName +"/"+R.raw.android_onboarding_video1)
        videoInit()

        video_view_onboarding_1.setOnCompletionListener {
            it.reset()
            videoInit()
        }

        btn_start_onboarding_1.setOnClickListener {
            videoStart()
        }
    }

    fun videoStart() {
        video_view_onboarding_1.start()
        btn_start_onboarding_1.visibility = View.GONE
    }

    fun videoInit() {
        video_view_onboarding_1.setVideoURI(path)
        video_view_onboarding_1.requestFocus()
        btn_start_onboarding_1.visibility = View.VISIBLE
    }
}