package cookcook.nexters.com.amoogye.views.calc.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.databinding.FragmentCalcIngredientBinding
import kotlinx.android.synthetic.main.fragment_calc.*
import org.koin.android.viewmodel.ext.android.getViewModel

class IngredientFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc_ingredient
    override val isUseDataBinding = true

    private lateinit var binding : FragmentCalcIngredientBinding
    private lateinit var calculatorViewModel : CalculatorViewModel

    override fun setupViews(view: View) {

    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc_ingredient, container, false)
        /* TODO: viewModel을 CalcFragment에서 초기화 한 것을 가져와서 쓰자 */
        calculatorViewModel = getViewModel()
        calculatorViewModel.context = context!!

        return binding.root
    }
}