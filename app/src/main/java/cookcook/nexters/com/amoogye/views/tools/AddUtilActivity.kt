package cookcook.nexters.com.amoogye.views.tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.activity_tools_addutil_main.*

class AddUtilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_addutil_main)

        val addUtilFragmentAdapter = AddUtilViewPagerAdapter(supportFragmentManager)
        view_pager_add_util.adapter = addUtilFragmentAdapter

        indicator_add_util.setupWithViewPager(view_pager_add_util)
    }

}
