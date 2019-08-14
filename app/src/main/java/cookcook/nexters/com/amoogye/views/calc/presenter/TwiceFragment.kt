package cookcook.nexters.com.amoogye.views.calc.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.databinding.FragmentCalcTwiceBinding
import cookcook.nexters.com.amoogye.views.calc.entity.CalcLayoutState
import kotlinx.android.synthetic.main.fragment_calc_twice.*

class TwiceFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc_twice
    override val isUseDataBinding = true

    private lateinit var calcFragment: CalcFragment
    private lateinit var binding: FragmentCalcTwiceBinding

    override fun setupViews(view: View) {
        initialize()

        edit_twice_human_one.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                convertCalcLayoutState(CalcLayoutState.NUMBER)
            }
        }

        edit_twice_amount.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                convertCalcLayoutState(CalcLayoutState.NUMBER)
            }
        }

        edit_twice_unit.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                convertCalcLayoutState(CalcLayoutState.UNIT)
            }
        }

        edit_twice_human_two.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                convertCalcLayoutState(CalcLayoutState.NUMBER)
            }
        }

        edit_twice_tool.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                convertCalcLayoutState(CalcLayoutState.TOOL)
            }
        }
    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc_twice, container, false)
        calcFragment = CalcFragment.getInstance()

        return binding.root
    }

    private fun initialize() {
        edit_twice_human_one.requestFocus()

        convertCalcLayoutState(CalcLayoutState.NUMBER)
    }

    private fun convertCalcLayoutState(state: CalcLayoutState) {
        calcFragment.binding.calcLayoutButton.visibility = View.GONE
        calcFragment.binding.calcLayoutTool.visibility = View.GONE
        calcFragment.binding.calcLayoutUnit.visibility = View.GONE
        calcFragment.binding.calcLayoutIngredient.visibility = View.GONE

        when (state) {
            CalcLayoutState.NUMBER -> calcFragment.binding.calcLayoutButton.visibility = View.VISIBLE
            CalcLayoutState.TOOL -> calcFragment.binding.calcLayoutTool.visibility = View.VISIBLE
            CalcLayoutState.UNIT -> calcFragment.binding.calcLayoutUnit.visibility = View.VISIBLE
            CalcLayoutState.INGREDIENT -> calcFragment.binding.calcLayoutIngredient.visibility = View.VISIBLE
        }
    }
}