package cookcook.nexters.com.amoogye.views.main.presenter

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseActivity
import cookcook.nexters.com.amoogye.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity(override val layoutRes: Int, override val isUseDatabinding: Boolean) : BaseActivity() {
    override fun setupViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subscribeUI() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val mainViewModel: MainViewModel by viewModel()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainVM = mainViewModel
        mainViewModel.context = this
        mainViewModel.supportFragmentManager = supportFragmentManager

        mainViewModel.onNavigationItemSelected(R.id.navigation_main_calc)

        bottom_navigation_main.setOnNavigationItemSelectedListener {
            mainViewModel.onNavigationItemSelected(it.itemId)
        }

    }
}
