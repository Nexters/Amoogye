package cookcook.nexters.com.amoogye.views.tools.add_tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_tools_addutil_main.*

class AddUtilActivity : AppCompatActivity(), OnEditTextClickListener,
    OnCountEnableListener, AddUtilNameFragment.OnGetNameEditTextListener,
    AddUtilVolumeFragment.OnGetNameByUserListener,
    AddUtilCompleteFragment.OnAddUtilResultListener {
    override fun onClickEditText() {
        val layout = findViewById<RelativeLayout>(R.id.layout_main_activity_outer_mid)
        layout.visibility = View.GONE
    }

    override fun onClickOuterText() {
        layout_main_activity_outer_mid.visibility = View.VISIBLE
    }

    lateinit var addUtilFragmentAdapter: AddUtilViewPagerAdapter

    override fun onCountTextEnableTrue() {
        btn_add_util_next_page.isEnabled = true
    }

    override fun onCountTextEnableFalse() {
        btn_add_util_next_page.isEnabled = false
    }


    override fun onGetNameEditText(): String {
        val name = findViewById<EditText>(R.id.edit_txt_name_util).text.toString()
        return name
    }

    override fun onUniqueNameShowAlert() {
        val alertMessage = findViewById<TextView>(R.id.txt_alert_same_name)
        alertMessage.visibility = View.VISIBLE
    }


    var itemName = ""
    override fun onGetNameByUser() {
        itemName = findViewById<EditText>(R.id.edit_txt_name_util).text.toString()
        val comment = findViewById<TextView>(R.id.txt_2_user_name)
        comment.text = itemName
    }

    override fun onAddUtilResult() {
        val nameResult = findViewById<TextView>(R.id.txt_3_user_name)
        nameResult.text = itemName
    }


    lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_addutil_main)

        realm = Realm.getDefaultInstance()

        addUtilFragmentAdapter =
            AddUtilViewPagerAdapter(supportFragmentManager, this, this, this, this)
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
                        firstPageDefault()
                    }
                    1 -> {
                        restPageDefault()
                        onGetNameByUser()
                        btn_add_util_next_page.text = "다음"
                        btn_add_util_exit.visibility = View.VISIBLE
                        btn_add_util_next_page.setOnClickListener {
                            view_pager_add_util.setCurrentItem(getItem(1), true)
                        }
                    }
                    2 -> {
                        restPageDefault()
                        onAddUtilResult()
                        btn_add_util_next_page.text = "확인"
                        btn_add_util_exit.visibility = View.INVISIBLE
                        btn_add_util_next_page.setOnClickListener {
                            finish()
                        }


                    }
                }
            }
        })

        firstPageDefault()

        btn_add_util_back.setOnClickListener {
            view_pager_add_util.setCurrentItem(getItem(-1), true)
        }

        btn_add_util_exit.setOnClickListener {
            exitAlert()
        }

    }

    private fun firstPageDefault() {
        btn_add_util_next_page.isEnabled = false
        btn_add_util_back.visibility = View.INVISIBLE
        btn_add_util_next_page.text = "다음"
        btn_add_util_exit.visibility = View.VISIBLE
        btn_add_util_next_page.setOnClickListener {
            btn_add_util_next_page.isEnabled = isNameUnique()
        }
    }

    private fun restPageDefault() {
        btn_add_util_back.visibility = View.VISIBLE
        btn_add_util_next_page.isEnabled = true
        layout_main_activity_outer_mid.visibility = View.VISIBLE
    }

    private fun isNameUnique(): Boolean {
        val nameEditText = onGetNameEditText()
        Log.d("nameEdit", nameEditText)

        try {
            realm.where(MeasureUnit::class.java).equalTo("unitNameBold", nameEditText).findFirst()!!
        } catch (e: Exception) {
            view_pager_add_util.setCurrentItem(getItem(1), true)
            return true
        }

        onUniqueNameShowAlert()
        return false
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
    fun onClickOuterText()
}

interface OnCountEnableListener {
    fun onCountTextEnableTrue()
    fun onCountTextEnableFalse()
}