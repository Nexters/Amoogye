package cookcook.nexters.com.amoogye.views.tools.add_tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.activity_tools_addutil_main.*

class AddUtilActivity : AppCompatActivity(), OnEditTextClickListener, OnOuterTextClickListener {
    override fun onClickEditText() {
        layout_main_activity_outer_mid.visibility = View.GONE
    }

    override fun onClickOuterText() {
        layout_main_activity_outer_mid.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_addutil_main)

        val addUtilFragmentAdapter =
            AddUtilViewPagerAdapter(supportFragmentManager, this, this)
        view_pager_add_util.adapter = addUtilFragmentAdapter

        indicator_add_util.setupWithViewPager(view_pager_add_util)

        // 프래그먼트 스와이프 시 변동사항
        view_pager_add_util.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position % 3) {
                    1 -> {
                        btn_add_util_next_page.text = "다음"
                        btn_add_util_back.visibility = View.VISIBLE
                        btn_add_util_next_page.setOnClickListener {
                            view_pager_add_util.setCurrentItem(getItem(1), true)
                        }
                    }
                    2 -> {
                        btn_add_util_next_page.setOnClickListener {
                            finish()
                        }
                        btn_add_util_next_page.text = "확인"
                        btn_add_util_back.visibility = View.VISIBLE

                    }
                    else -> {
                        btn_add_util_next_page.text = "다음"
                        btn_add_util_back.visibility = View.INVISIBLE
                    }
                }
            }
        })

        // 버튼 눌렀을 때 이전, 다음 프래그먼트 이동
        btn_add_util_back.setOnClickListener {
            view_pager_add_util.setCurrentItem(getItem(-1), true)
        }

        btn_add_util_next_page.setOnClickListener {
            view_pager_add_util.setCurrentItem(getItem(1), true)
        }

        // 종료
        btn_add_util_exit.setOnClickListener {
            finish()
        }

    }

    private fun getItem(page: Int): Int {
        return view_pager_add_util.currentItem + page
    }

}

interface OnEditTextClickListener {
    fun onClickEditText()
}

interface OnOuterTextClickListener {
    fun onClickOuterText()
}