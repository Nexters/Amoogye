package cookcook.nexters.com.amoogye.views.tools.add_tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.activity_tools_addutil_main.*

class AddUtilActivity : AppCompatActivity(), OnEditTextClickListener, OnOuterTextClickListener,
    OnCountEnableTrueListener, OnCountEnableFalseListener {
    override fun onClickEditText() {
        val layout = findViewById<RelativeLayout>(R.id.layout_main_activity_outer_mid)
        layout.visibility = View.GONE
    }

    override fun onClickOuterText() {
        layout_main_activity_outer_mid.visibility = View.VISIBLE
    }

    lateinit var addUtilFragmentAdapter: AddUtilViewPagerAdapter

    override fun onCountTextEnable() {
        btn_add_util_next_page.isEnabled = true
    }

    override fun onCountTextEnableFalse() {
        btn_add_util_next_page.isEnabled = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_addutil_main)

        addUtilFragmentAdapter =
            AddUtilViewPagerAdapter(supportFragmentManager, this, this)
        view_pager_add_util.adapter = addUtilFragmentAdapter
        view_pager_add_util.setSwipePagingEnabled(false)

        indicator_add_util.setupWithViewPager(view_pager_add_util)

        // 프래그먼트 스와이프 시 변동사항
        view_pager_add_util.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        moveNextPage()
                        btn_add_util_next_page.isEnabled = false
                        btn_add_util_back.visibility = View.INVISIBLE
                    }
                    1 -> {
                        moveNextPage()
                        btn_add_util_next_page.isEnabled = true
                        btn_add_util_back.visibility = View.VISIBLE
                    }
                    2 -> {
                        btn_add_util_next_page.setOnClickListener {
                            finish()
                        }
                        btn_add_util_next_page.text = "확인"
                        btn_add_util_next_page.isEnabled = true
                        btn_add_util_back.visibility = View.VISIBLE
                        btn_add_util_exit.visibility = View.INVISIBLE
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
            addUtilFragmentAdapter.instanceInit()
            exitAlert()
        }

    }

    private fun getItem(page: Int): Int {
        return view_pager_add_util.currentItem + page
    }

    private fun exitAlert() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.addutil_alert_dialog, null)
        builder.setView(dialogView)
            .setPositiveButton("예") { _, _ ->
                finish()
            }
            .setNegativeButton("아니오") { _, _ ->
            }
            .show()
    }

    private fun moveNextPage() {
        btn_add_util_next_page.text = "다음"
        btn_add_util_next_page.setOnClickListener {
            view_pager_add_util.setCurrentItem(getItem(1), true)
        }
        btn_add_util_exit.visibility = View.VISIBLE
    }

}

interface OnEditTextClickListener {
    fun onClickEditText()
}

interface OnOuterTextClickListener {
    fun onClickOuterText()
}

interface OnCountEnableTrueListener {
    fun onCountTextEnable()
}

interface OnCountEnableFalseListener {
    fun onCountTextEnableFalse()
}