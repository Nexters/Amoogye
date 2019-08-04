package cookcook.nexters.com.amoogye.views.tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cookcook.nexters.com.amoogye.R
import kotlinx.android.synthetic.main.activity_tools_addutil_main.*

class ToolsAddUtilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_addutil_main)

        val addToolsFragment = AddUtilNameFragment()
        val addToolsManager = supportFragmentManager

        val addToolsTransaction = addToolsManager.beginTransaction()
        addToolsTransaction.replace(R.id.layout_outer_mid_container, addToolsFragment)
        addToolsTransaction.addToBackStack(null)
        addToolsTransaction.commit()


        btn_add_util_next_step.setOnClickListener {
            val addToolsFragment = AddUtilVolumeFragment()
            val addToolsManager = supportFragmentManager

            val addToolsTransaction = addToolsManager.beginTransaction()
            addToolsTransaction.replace(R.id.layout_outer_mid_container, addToolsFragment)
            addToolsTransaction.addToBackStack(null)
            addToolsTransaction.commit()
        }
    }

}
