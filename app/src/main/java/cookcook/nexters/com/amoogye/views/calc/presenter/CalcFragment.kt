package cookcook.nexters.com.amoogye.views.calc.presenter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import cookcook.nexters.com.amoogye.databinding.FragmentCalcBinding
import kotlinx.android.synthetic.main.fragment_calc.*
import kotlinx.android.synthetic.main.layout_ingredient_scroll_wrap.*
import org.koin.android.ext.android.get

class CalcFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc
    override val isUseDataBinding = true
    lateinit var binding: FragmentCalcBinding
    private val calculatorViewModel: CalculatorViewModel = get()

    private val ingredientSelectStatus = arrayOf(true, false, true)
    private val portionSelectStatus = arrayOf(false, true, true)
    private val plusSelectStatus = arrayOf(false, false, true)

    private val calcBottomContainer: Array<Fragment> = arrayOf(
        IngredientFragment(),
        PortionFragment(),
        TwiceFragment()
    )

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
        btn_tip.setOnClickListener { calculatorViewModel.gazuaa("tool_tip 구현 예정") }

        itemChange(calculatorViewModel.flag - 1)

        txt_ingredient.setOnClickListener {
            fragmentChange(1)
        }

        txt_human.setOnClickListener {
            fragmentChange(2)
        }

        val items: ArrayList<String> = arrayListOf(
            "jjjjjjjjj", "mmmmmmm","kkkkkkkk","kjkjkjkjkjk","kkkkkkkkkk","jijijijiji","mkmkmkmkmk","inknknknk","rdrdftft","gtfrdeswasrdf"
        )
        val picker = BaseScrollPicker(view, items)
    }

    private fun itemChange(containerCase: Int) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.calculator_container, calcBottomContainer[containerCase])
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        fragmentTransaction.commit()

        if (ingredientSelectStatus[containerCase]) {
            txt_ingredient.setTextColor(Color.parseColor("#131c32"))
        } else {
            txt_ingredient.setTextColor(Color.parseColor("#33131c32"))
        }

        if (portionSelectStatus[containerCase]) {
            txt_human.setTextColor(Color.parseColor("#131c32"))
        } else {
            txt_human.setTextColor(Color.parseColor("#33131c32"))
        }

        if (plusSelectStatus[containerCase]) {
            txt_calc_plus.setTextColor(Color.parseColor("#131c32"))
        } else {
            txt_calc_plus.setTextColor(Color.parseColor("#33131c32"))
        }


    }

    private fun fragmentChange(number: Int) {
        val containerCase = calculatorViewModel.convertFragment(number)
        itemChange(containerCase)
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