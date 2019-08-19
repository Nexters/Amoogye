package cookcook.nexters.com.amoogye.views.calc.presenter

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baoyz.actionsheet.ActionSheet
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import cookcook.nexters.com.amoogye.databinding.FragmentCalcBinding
import cookcook.nexters.com.amoogye.views.calc.entity.NormalUnitModel
import cookcook.nexters.com.amoogye.views.calc.entity.UnitType
import kotlinx.android.synthetic.main.fragment_calc.*
import kotlinx.android.synthetic.main.layout_unit_button_wrap.*
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

    private var currentContainerCase: Int = 3

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

        // edittext setting
        calculatorViewModel.calculatorEditTextSetting(edit_twice_human_one)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_amount)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_unit)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_ingredient)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_human_two)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_tool)

        itemChange(calculatorViewModel.flag - 1)
        unitRecyclerView = UnitButtonActivity(view)
        unitRecyclerView.addItems(makeDummyNormalItems())

        txt_ingredient.setOnClickListener {
            changeCalcContainerLayout(1)
        }

        txt_human.setOnClickListener {
            changeCalcContainerLayout(2)
        }

        btn_unit_changer.setOnClickListener {
            callUnitSelector()
        }

        val items: ArrayList<String> = arrayListOf(
            "jjjjjjjjj",
            "mmmmmmm",
            "kkkkkkkk",
            "kjkjkjkjkjk",
            "kkkkkkkkkk",
            "jijijijiji",
            "mkmkmkmkmk",
            "inknknknk",
            "rdrdftft",
            "gtfrdeswasrdf"
        )
        val picker = BaseScrollPicker(view, items)
    }

    private fun itemChange(containerCase: Int) {

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
        updateContents(containerCase)
    }

    var humanTopWidth = 0
    var humanBottomWidth = 0
    var ingredientTopWidth = 0
    var ingredientBottomWidth = 0

    private fun updateContents(number: Int) {

        var currentContainerCase = this.currentContainerCase
        when (number) {
            0 -> {
                // ㅁ -> 재료
                when (currentContainerCase) {
                    0 -> {
                        // 아무런 변화가 일어나지 않는다.
                    }
                    1 -> {
                        Log.e("Error", "가능한 케이스가 아닙니다.")
                        return
                    }
                    2 -> {
                        humanTopWidth = layout_human_top_standard.measuredWidth
                        humanBottomWidth = layout_human_bottom_standard.measuredWidth

                        val animation = ValueAnimator.ofInt(layout_human_top_standard.measuredWidth, 0).setDuration(200)
                        animation.addUpdateListener {
                            val value = it.animatedValue as Int
                            layout_human_top_standard.layoutParams.width = value
                            layout_human_top_standard.requestLayout()
                        }

                        val animation2 =
                            ValueAnimator.ofInt(layout_human_bottom_standard.measuredWidth, 0).setDuration(200)
                        animation2.addUpdateListener {
                            val value = it.animatedValue as Int
                            layout_human_bottom_standard.layoutParams.width = value
                            layout_human_bottom_standard.requestLayout()
                        }

                        val set = AnimatorSet()
                        set.playSequentially(listOf(animation, animation2))
                        set.setInterpolator(AccelerateDecelerateInterpolator())
                        set.start()
                    }

                }

            }
            1 -> {
                // ㅁ -> 인원
                when (currentContainerCase) {
                    0 -> {
                        Log.e("Error", "가능한 케이스가 아닙니다.")
                        return
                    }
                    1 -> {
                        // 아무런 변화가 일어나지 않는다.
                    }
                    2 -> {
                        ingredientTopWidth = layout_ingredient_top_standard.measuredWidth
                        ingredientBottomWidth = layout_ingredient_bottom_standard.measuredWidth
                        val animation2 =
                            ValueAnimator.ofInt(layout_ingredient_bottom_standard.measuredWidth, 0).setDuration(200)
                        animation2.addUpdateListener {
                            val value = it.animatedValue as Int
                            layout_ingredient_bottom_standard.layoutParams.width = value
                            layout_ingredient_bottom_standard.requestLayout()
                        }

                        val set = AnimatorSet()
//                        set.playSequentially(listOf(animation, animation2))
                        set.play(animation2)
                        set.setInterpolator(AccelerateDecelerateInterpolator())
                        set.start()
                    }
                }

            }
            2 -> {
                // 재료 + 인원
                when (currentContainerCase) {
                    0 -> {
                        val animation =
                            ValueAnimator.ofInt(0, humanTopWidth).setDuration(200)
                        animation.addUpdateListener {
                            val value = it.animatedValue as Int
                            layout_human_top_standard.layoutParams.width = value
                            layout_human_top_standard.requestLayout()
                        }

                        val animation2 =
                            ValueAnimator.ofInt(0, humanBottomWidth).setDuration(200)
                        animation2.addUpdateListener {
                            val value = it.animatedValue as Int
                            layout_human_bottom_standard.layoutParams.width = value
                            layout_human_bottom_standard.requestLayout()
                        }


                        val set = AnimatorSet()
                        set.playSequentially(listOf(animation, animation2))
                        set.setInterpolator(AccelerateDecelerateInterpolator())
                        set.start()
                    }

                    1 -> {
                        val animation2 =
                            ValueAnimator.ofInt(0, ingredientBottomWidth).setDuration(200)
                        animation2.addUpdateListener {
                            val value = it.animatedValue as Int
                            layout_ingredient_bottom_standard.layoutParams.width = value
                            layout_ingredient_bottom_standard.requestLayout()
                        }

                        val set = AnimatorSet()
                        set.play(animation2)
                        set.setInterpolator(AccelerateDecelerateInterpolator())
                        set.start()
                    }

                    2 -> {
                        // 아무 일도 일어나지 않는다.
                    }
                }
            }
            3 -> {
                // 질량
                layout_weight_standard.visibility = View.VISIBLE
            }
            else -> {

            }
        }

        this.currentContainerCase = number
    }

    private fun changeCalcContainerLayout(number: Int) {
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
            .setListener(object : ActionSheet.ActionSheetListener {
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
            NormalUnitModel("베라스푼", null, UnitType.LIFE),
            NormalUnitModel("종이컵", null, UnitType.LIFE),
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

    override fun onDestroy() {
        calculatorViewModel.init()
        super.onDestroy()
    }
}