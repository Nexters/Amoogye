package cookcook.nexters.com.amoogye.views.calc.presenter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.baoyz.actionsheet.ActionSheet
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import cookcook.nexters.com.amoogye.databinding.FragmentCalcBinding
import cookcook.nexters.com.amoogye.views.calc.entity.NormalUnitModel
import cookcook.nexters.com.amoogye.views.calc.entity.UnitType
import kotlinx.android.synthetic.main.fragment_calc.*
import kotlinx.android.synthetic.main.layout_unit_button_wrap.*
import kotlinx.android.synthetic.main.layout_ingredient_scroll_wrap.*
import org.koin.android.ext.android.get


class CalcFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc
    override val isUseDataBinding = true
    lateinit var binding: FragmentCalcBinding
    private val calculatorViewModel: CalculatorViewModel = get()
    private lateinit var unitRecyclerView: UnitButtonActivity

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
        unitRecyclerView = UnitButtonActivity(view)
        unitRecyclerView.addItems(makeDummyNormalItems())

        txt_ingredient.setOnClickListener {
            fragmentChange(1)
        }

        txt_human.setOnClickListener {
            fragmentChange(2)
        }

        btn_unit_changer.setOnClickListener {
            callUnitSelector()
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

    private fun callUnitSelector() {
        context!!.setTheme(R.style.ActionSheetStyleiOS7)

        /* TODO: 생활단위, 일반단위를 고정값이 아니게 바꾸자 */
        ActionSheet.createBuilder(context, fragmentManager)
            .setCancelButtonTitle("취소")
            .setOtherButtonTitles("생활단위", "일반단위")
            .setCancelableOnTouchOutside(true)
            .setListener(object: ActionSheet.ActionSheetListener {
                override fun onOtherButtonClick(actionSheet: ActionSheet?, index: Int) {
                    if (index == 0) {
                        txt_unit_changer.text = "생활단위"
                        setRecyclerViewUnitLife()
                        for (item in unitRecyclerView.adapter.getUnitList()) {
                            Log.d("good", item.abbreviation)
                        }
                    } else {
                        txt_unit_changer.text = "일반단위"
                        setRecyclerViewUnitNormal()
                        for (item in unitRecyclerView.adapter.getUnitList()) {
                            Log.d("good", item.abbreviation)
                        }
                    }
                }

                override fun onDismiss(actionSheet: ActionSheet?, isCancel: Boolean) {
                    Log.d("", "")
                }
            }).show()
    }

    private fun makeDummyNormalItems(): ArrayList<NormalUnitModel> {
        return arrayListOf(
            NormalUnitModel("g", "그램", UnitType.NORMAL),
            NormalUnitModel("kg", "킬로그램", UnitType.NORMAL),
            NormalUnitModel("oz", "온즈", UnitType.NORMAL),
            NormalUnitModel("cc", "시시", UnitType.NORMAL),
            NormalUnitModel("ml", "밀리그램", UnitType.NORMAL),
            NormalUnitModel("L", "리터", UnitType.NORMAL),
            NormalUnitModel("Tbsp", "테이블스푼", UnitType.NORMAL),
            NormalUnitModel("Tsp", "티스푼", UnitType.NORMAL),
            NormalUnitModel("pt", "파인트", UnitType.NORMAL)
        )
    }

    private fun makeDummyLifeItems(): ArrayList<NormalUnitModel> {
        return arrayListOf(
            NormalUnitModel("밥숟가락", null, UnitType.LIFE),
            NormalUnitModel("베라스푼",null, UnitType.LIFE),
            NormalUnitModel("종이컵",null, UnitType.LIFE),
            NormalUnitModel("병뚜껑", null, UnitType.LIFE),
            NormalUnitModel("김용기", null, UnitType.LIFE),
            NormalUnitModel("소주잔", null, UnitType.LIFE),
            NormalUnitModel("참치캔", null, UnitType.LIFE),
            NormalUnitModel("햇반그릇", null, UnitType.LIFE),
            NormalUnitModel("my냄비", null, UnitType.LIFE)
        )
    }

    private fun setRecyclerViewUnitNormal() {
        unitRecyclerView.removeAll()
        unitRecyclerView.addItems(makeDummyNormalItems())
    }

    private fun setRecyclerViewUnitLife() {
        unitRecyclerView.removeAll()
        unitRecyclerView.addItems(makeDummyLifeItems())
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