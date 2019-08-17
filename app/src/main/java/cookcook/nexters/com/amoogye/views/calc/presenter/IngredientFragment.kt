package cookcook.nexters.com.amoogye.views.calc.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.databinding.FragmentCalcIngredientBinding
import cookcook.nexters.com.amoogye.views.calc.entity.CalcLayoutState
import kotlinx.android.synthetic.main.fragment_calc_ingredient.*
import org.koin.android.ext.android.get

class IngredientFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc_ingredient
    override val isUseDataBinding = true

    private lateinit var calcFragment: CalcFragment
    private lateinit var binding : FragmentCalcIngredientBinding
    private val calculatorViewModel : CalculatorViewModel = get()

    override fun setupViews(view: View) {
        initialize()

        edit_ingredient_amount.setOnFocusChangeListener { v, isFocus ->
            if (isFocus) {
                convertCalcLayoutState(CalcLayoutState.NUMBER)
                v.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            } else {
                v.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
            }
        }

        edit_ingredient_tool.setOnFocusChangeListener { v, isFocus ->
            if (isFocus) {
                convertCalcLayoutState(CalcLayoutState.TOOL)
                v.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            } else {
                v.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
            }
        }

        edit_ingredient_unit.setOnFocusChangeListener { v, isFocus ->
            if (isFocus) {
                convertCalcLayoutState(CalcLayoutState.UNIT)
                v.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            } else {
                v.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
            }
        }
    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc_ingredient, container, false)

        calcFragment = CalcFragment.getInstance()

        return binding.root
    }

    private fun initialize() {
//        edit_ingredient_amount.requestFocus()
        // 키보드 제거
        calculatorViewModel.calculatorEditTextSetting(edit_ingredient_amount)
        calculatorViewModel.calculatorEditTextSetting(edit_ingredient_tool)
        calculatorViewModel.calculatorEditTextSetting(edit_ingredient_unit)


        convertCalcLayoutState(CalcLayoutState.NUMBER)
    }

    private fun convertCalcLayoutState(state: CalcLayoutState) {
        calcFragment.binding.calcLayoutButton.visibility = View.GONE
        calcFragment.binding.calcLayoutTool.visibility = View.GONE
        calcFragment.binding.calcLayoutUnit.visibility = View.GONE
        calcFragment.binding.calcLayoutIngredient.visibility = View.GONE

        when (state) {
            CalcLayoutState.NUMBER -> calcFragment.binding.calcLayoutButton.visibility = View.VISIBLE
            CalcLayoutState.TOOL ->  calcFragment.binding.calcLayoutTool.visibility = View.VISIBLE
            CalcLayoutState.UNIT -> calcFragment.binding.calcLayoutUnit.visibility = View.VISIBLE
            CalcLayoutState.INGREDIENT -> calcFragment.binding.calcLayoutIngredient.visibility = View.VISIBLE
        }
    }
}