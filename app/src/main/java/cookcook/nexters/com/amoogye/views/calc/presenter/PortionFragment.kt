package cookcook.nexters.com.amoogye.views.calc.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.databinding.FragmentCalcPortionBinding
import kotlinx.android.synthetic.main.fragment_calc_portion.*

class PortionFragment: BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc_portion
    override val isUseDataBinding = true

    private lateinit var calcFragment: CalcFragment
    private lateinit var binding: FragmentCalcPortionBinding

    override fun setupViews(view: View) {
        initialize()

        edit_portion_human_one.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                calcFragment.binding.calcLayoutButton.visibility = View.VISIBLE
                calcFragment.binding.calcLayoutTool.visibility = View.GONE
                calcFragment.binding.calcLayoutUnit.visibility = View.GONE
                calcFragment.binding.calcLayoutIngredient.visibility = View.GONE
            }
        }

        edit_portion_amount.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                calcFragment.binding.calcLayoutButton.visibility = View.VISIBLE
                calcFragment.binding.calcLayoutTool.visibility = View.GONE
                calcFragment.binding.calcLayoutUnit.visibility = View.GONE
                calcFragment.binding.calcLayoutIngredient.visibility = View.GONE
            }
        }

        edit_portion_unit.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                calcFragment.binding.calcLayoutButton.visibility = View.GONE
                calcFragment.binding.calcLayoutTool.visibility = View.GONE
                calcFragment.binding.calcLayoutUnit.visibility = View.VISIBLE
                calcFragment.binding.calcLayoutIngredient.visibility = View.GONE
            }
        }

        edit_portion_human_two.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                calcFragment.binding.calcLayoutButton.visibility = View.VISIBLE
                calcFragment.binding.calcLayoutTool.visibility = View.GONE
                calcFragment.binding.calcLayoutUnit.visibility = View.GONE
                calcFragment.binding.calcLayoutIngredient.visibility = View.GONE
            }
        }
    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc_portion, container, false)
        calcFragment = CalcFragment.getInstance()

        return binding.root
    }

    private fun initialize() {
        edit_portion_human_one.requestFocus()

        calcFragment.binding.calcLayoutButton.visibility = View.VISIBLE
        calcFragment.binding.calcLayoutTool.visibility = View.GONE
        calcFragment.binding.calcLayoutUnit.visibility = View.GONE
        calcFragment.binding.calcLayoutIngredient.visibility = View.GONE
    }
}