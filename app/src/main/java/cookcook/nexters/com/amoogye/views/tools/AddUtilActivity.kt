package cookcook.nexters.com.amoogye.views.tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.activity_tools_addutil_main.*

class AddUtilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_addutil_main)

        val addUtilFragmentAdapter = AddUtilViewPagerAdapter(supportFragmentManager)
        view_pager_add_util.adapter = addUtilFragmentAdapter

        indicator_add_util.setupWithViewPager(view_pager_add_util)

        view_pager_add_util.addOnPageChangeListener (object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    btn_add_util_next_page.text = "확인"
                } else {
                    btn_add_util_next_page.text = "다음"
                }
            }
        })

        btn_add_util_back.setOnClickListener {
            view_pager_add_util.setCurrentItem(getItem(-1), true)
        }

        btn_add_util_next_page.setOnClickListener {
            view_pager_add_util.setCurrentItem(getItem(1), true)
        }
    }


    private fun getItem(page : Int) : Int{
        return view_pager_add_util.currentItem+page
    }
}
