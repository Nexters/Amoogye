package cookcook.nexters.com.amoogye.views.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.utils.SharedPreferences
import cookcook.nexters.com.amoogye.views.main.presenter.MainActivity
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {

    lateinit var onboardingViewPagerAdapter: OnboardingViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onboarding = SharedPreferences(this)
        if(!onboarding.onboarding) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_onboarding)

        onboardingViewPagerAdapter = OnboardingViewPagerAdapter(supportFragmentManager)

        view_pager_onboarding.adapter = onboardingViewPagerAdapter

        onboarding_tab_layout.setupWithViewPager(view_pager_onboarding)
        onboarding_tab_layout.clearOnTabSelectedListeners()

        onboarding_tab_layout.tabSelectedIndicator

        view_pager_onboarding.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        btn_onboarding_ok.visibility = View.GONE
                        onboarding_tab_layout.visibility = View.VISIBLE
                        layout_onboarding.setBackgroundColor(resources.getColor(R.color.onboarding_1))
                        lottieAnimation("android_onboarding01.json", R.id.onboarding_lottie_1)
                    }
                    1 -> {
                        btn_onboarding_ok.visibility = View.GONE
                        onboarding_tab_layout.visibility = View.VISIBLE
                        layout_onboarding.setBackgroundColor(resources.getColor(R.color.onboarding_2))
                        lottieAnimation("android_onboarding02.json", R.id.onboarding_lottie_2)
                    }
                    2 -> {
                        btn_onboarding_ok.visibility = View.GONE
                        layout_onboarding.setBackgroundColor(resources.getColor(R.color.onboarding_3))
                        onboarding_tab_layout.visibility = View.VISIBLE
                        OnboardingFragment3.getInstance().videoStart()
                    }
                    3 -> {
                        btn_onboarding_ok.visibility = View.GONE
                        layout_onboarding.setBackgroundColor(resources.getColor(R.color.onboarding_4))
                        onboarding_tab_layout.visibility = View.VISIBLE
                        OnboardingFragment4.getInstance().videoStart()
                    }
                    4 -> {
                        btn_onboarding_ok.visibility = View.VISIBLE
                        layout_onboarding.setBackgroundColor(resources.getColor(R.color.onboarding_5))
                        onboarding_tab_layout.visibility = View.GONE
                        lottieAnimation("android_onboarding03.json", R.id.onboarding_lottie_3)
                    }
                }
            }
        })

        btn_onboarding_ok.setOnClickListener {
            onboarding.onboarding = false
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun lottieAnimation(jsonName: String, id:Int) {
        val animationView = findViewById<View>(id) as LottieAnimationView
        animationView.setAnimation(jsonName)
        animationView.loop(true)
        animationView.playAnimation()
    }
}
