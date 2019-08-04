package cookcook.nexters.com.amoogye.views.calc.presenter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.databinding.FragmentCalcBinding
import kotlinx.android.synthetic.main.fragment_calc.*
import org.koin.android.viewmodel.ext.android.viewModel


class CalcFragment: BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc
    override val isUseDataBinding = true
    lateinit var binding : FragmentCalcBinding
    private val calculatorViewModel: CalculatorViewModel by viewModel()

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: CalcFragment? = null

        fun getInstance(): CalcFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    CalcFragment()
            }
            return INSTANCE!!
        }
    }

    override fun setupViews(view: View) {

        btn_history.setOnClickListener { calculatorViewModel.gazuaa("history 구현 예정") }
        btn_tip.setOnClickListener { calculatorViewModel.gazuaa("tip 구현 예정") }

        /********************** 모기님 알려주세요~~~ **********************/
        /* TODO: 글자 색상 변경을 어떻게 묶어야할까..? 알아보자 */
        /* TODO: fragmentManger는 이렇게 인자로 넘겨야만할까..? 알아보자 */

        txt_ingredient.setOnClickListener {
            calculatorViewModel.flag = calculatorViewModel.convertFragment(calc_text_container, fragmentManager!!, 1)
        }

        txt_human.setOnClickListener {
            calculatorViewModel.flag = calculatorViewModel.convertFragment(calc_text_container, fragmentManager!!, 2)
        }

        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()

        val ingredientFragment = IngredientFragment()

        fragmentTransaction.add(R.id.calculator_container, ingredientFragment).commit()
    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc, container, false)
        binding.calculatorVM = calculatorViewModel
        calculatorViewModel.context = context!!
        binding.lifecycleOwner = this

        return binding.root
    }
}