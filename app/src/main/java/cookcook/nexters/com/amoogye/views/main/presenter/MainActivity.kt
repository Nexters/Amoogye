package cookcook.nexters.com.amoogye.views.main.presenter

import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseActivity
import cookcook.nexters.com.amoogye.databinding.ActivityMainBinding
import cookcook.nexters.com.amoogye.views.calc.presenter.CalculatorViewModel
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override val layoutRes = R.layout.activity_main
    override val isUseDatabinding = true

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun setupViews() {
        mainViewModel.onNavigationItemSelected(R.id.navigation_main_calc)
        bottom_navigation_main.setOnNavigationItemSelectedListener {
            mainViewModel.onNavigationItemSelected(it.itemId)
        }
//        bottom_navigation_main.itemIconTintList = null
    }

    override fun subscribeUI() {
    }

    override fun onDataBinding() {
        super.onDataBinding()
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.mainVM = mainViewModel
        mainViewModel.context = this
        mainViewModel.supportFragmentManager = supportFragmentManager
    }

    override fun onDestroy() {
        Realm.getDefaultInstance().close()
        super.onDestroy()
    }
}
