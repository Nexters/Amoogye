package cookcook.nexters.com.amoogye.views.tools.add_tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_tools_addutil_main.*

class AddUtilActivity : AppCompatActivity(), OnEditTextClickListener, OnOuterTextClickListener,
    OnCountEnableTrueListener, OnCountEnableFalseListener, AddUtilNameFragment.OnGetNameEditTextListener, OnNameUniqueListener {
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

    override fun onNameUniqueAlert() : Boolean {
        return isNameUniqueFlag
    }


    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is AddUtilNameFragment) {
            fragment.setOnGetNameEditTextListener(this)
        }
    }

    override fun onGetNameEditText() : String {
        val name = findViewById<EditText>(R.id.edit_txt_name_util).text.toString()
        return name
    }

    lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_addutil_main)

        realm = Realm.getDefaultInstance()

        addUtilFragmentAdapter =
            AddUtilViewPagerAdapter(supportFragmentManager, this, this, this, this,this)
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
                        btn_add_util_next_page.isEnabled = false
                        btn_add_util_back.visibility = View.INVISIBLE
                        btn_add_util_next_page.text = "다음"
                        btn_add_util_exit.visibility = View.VISIBLE
                        btn_add_util_next_page.setOnClickListener {
                            isNameUnique()
                            btn_add_util_next_page.isEnabled = isNameUniqueFlag
                        }
                    }
                    1 -> {
                        pageDefault()
                        btn_add_util_next_page.text = "다음"
                        btn_add_util_exit.visibility = View.VISIBLE
                        btn_add_util_next_page.setOnClickListener {
                            view_pager_add_util.setCurrentItem(getItem(1), true)
                        }
                    }
                    2 -> {
                        pageDefault()
                        btn_add_util_next_page.text = "확인"
                        btn_add_util_exit.visibility = View.INVISIBLE
                        btn_add_util_next_page.setOnClickListener {
                            finish()
                        }

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
            exitAlert()
        }

    }

    private fun pageDefault() {
        btn_add_util_back.visibility = View.VISIBLE
        btn_add_util_next_page.isEnabled = true
        layout_main_activity_outer_mid.visibility = View.VISIBLE
    }

    private fun isNameUnique() {
        val nameEditText = onGetNameEditText()
        Log.d("nameEdit", nameEditText)

        try {
            realm.where(MeasureUnit::class.java).equalTo("unitNameBold", nameEditText).findFirst()!!
        } catch(e:Exception){
            view_pager_add_util.setCurrentItem(getItem(1), true)
            isNameUniqueFlag = true
        }

        isNameUniqueFlag = false
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

    override fun onDestroy() {
        addUtilFragmentAdapter.instanceInit()
        super.onDestroy()
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

interface OnNameUniqueListener {
    fun onNameUniqueAlert() : Boolean
}