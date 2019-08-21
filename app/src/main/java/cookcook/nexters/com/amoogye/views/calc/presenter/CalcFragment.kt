package cookcook.nexters.com.amoogye.views.calc.presenter

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.baoyz.actionsheet.ActionSheet
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.base.BaseNumberButton
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import cookcook.nexters.com.amoogye.databinding.FragmentCalcBinding
import cookcook.nexters.com.amoogye.views.calc.entity.EditTextType
import cookcook.nexters.com.amoogye.views.calc.entity.UnitModel
import cookcook.nexters.com.amoogye.views.calc.entity.UnitType
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import cookcook.nexters.com.amoogye.utils.realmData
import io.realm.Realm
import io.realm.RealmResults
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

    private lateinit var realm: Realm

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

    private val onClick: (number: String) -> Unit = {
        calculatorViewModel.onNumberButtonClick(it)
    }

    private fun editTextWrapChange(type: EditTextType) {
        edit_twice_human_one.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_amount.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_unit.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_ingredient.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_human_two.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_tool.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)

        when (type) {
            EditTextType.HUMAN_ONE -> edit_twice_human_one.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            EditTextType.AMOUNT -> edit_twice_amount.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            EditTextType.UNIT -> edit_twice_unit.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            EditTextType.INGREDIENT -> edit_twice_ingredient.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            EditTextType.HUMAN_TWO -> edit_twice_human_two.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
            EditTextType.TOOL -> edit_twice_tool.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
        }
    }

    private fun convertBottomButton(type: EditTextType) {
        calc_layout_button.visibility = View.GONE
        calc_layout_unit.visibility = View.GONE
        calc_layout_ingredient.visibility = View.GONE

        when (type) {
            EditTextType.HUMAN_ONE,
            EditTextType.HUMAN_TWO,
            EditTextType.AMOUNT -> calc_layout_button.visibility = View.VISIBLE

            EditTextType.UNIT,
            EditTextType.TOOL -> calc_layout_unit.visibility = View.VISIBLE

            EditTextType.INGREDIENT -> calc_layout_ingredient.visibility = View.VISIBLE
        }
    }

    private fun editTextClickEvent(editText: EditText, type: EditTextType) {
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                calculatorViewModel.setSelectedEditText(type)
                editTextWrapChange(calculatorViewModel.getSelectedEditText()!!)
                convertBottomButton(calculatorViewModel.getSelectedEditText()!!)
            }

            return@setOnTouchListener false
        }
    }

    override fun setupViews(view: View) {
        btn_history.setOnClickListener { calculatorViewModel.gazuaa("history 구현 예정") }
        btn_tip.setOnClickListener { calculatorViewModel.gazuaa("tool_tip 구현 예정") }

        // edittext setting
        /* TODO: 묶어서 초기화하자 */
        calculatorViewModel.calculatorEditTextSetting(edit_twice_human_one)
        editTextClickEvent(edit_twice_human_one, EditTextType.HUMAN_ONE)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_amount)
        editTextClickEvent(edit_twice_amount, EditTextType.AMOUNT)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_unit)
        editTextClickEvent(edit_twice_unit, EditTextType.UNIT)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_ingredient)
        editTextClickEvent(edit_twice_ingredient, EditTextType.INGREDIENT)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_human_two)
        editTextClickEvent(edit_twice_human_two, EditTextType.HUMAN_TWO)
        calculatorViewModel.calculatorEditTextSetting(edit_twice_tool)
        editTextClickEvent(edit_twice_tool, EditTextType.TOOL)

        BaseNumberButton(view, onClick)

        itemChange(calculatorViewModel.flag - 1)
        unitRecyclerView = UnitButtonActivity(view)

        /* TODO: 현재는 default로 일반 단위로 초기화되어있음 이후에는 선택 된 것을 기준으로 초기화하자. */
        unitRecyclerView.addItems(selectUnitItems(UnitType.NORMAL))

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

            val face : Typeface = ResourcesCompat.getFont(context!!, R.font.nanum_square_b)!!
            txt_ingredient.typeface = face
        } else {
            txt_ingredient.setTextColor(Color.parseColor("#33131c32"))

            val face : Typeface = ResourcesCompat.getFont(context!!, R.font.nanum_square_r)!!
            txt_ingredient.typeface = face
        }

        if (portionSelectStatus[containerCase]) {
            txt_human.setTextColor(Color.parseColor("#131c32"))

            val face : Typeface = ResourcesCompat.getFont(context!!, R.font.nanum_square_b)!!
            txt_human.typeface = face
        } else {
            txt_human.setTextColor(Color.parseColor("#33131c32"))

            val face : Typeface = ResourcesCompat.getFont(context!!, R.font.nanum_square_r)!!
            txt_human.typeface = face
        }

        if (plusSelectStatus[containerCase]) {
            txt_calc_plus.setTextColor(Color.parseColor("#131c32"))

            val face : Typeface = ResourcesCompat.getFont(context!!, R.font.nanum_square_b)!!
            txt_calc_plus.typeface = face
        } else {
            txt_calc_plus.setTextColor(Color.parseColor("#33131c32"))

            val face : Typeface = ResourcesCompat.getFont(context!!, R.font.nanum_square_r)!!
            txt_calc_plus.typeface = face
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
                        setRecyclerViewUnitModel(UnitType.LIFE)
                    } else {
                        txt_unit_changer.text = "일반단위"
                        setRecyclerViewUnitModel(UnitType.NORMAL)
                    }
                }

                override fun onDismiss(actionSheet: ActionSheet?, isCancel: Boolean) {
                    Log.d("", "")
                }
            }).show()
    }

    private fun selectUnitItems(type: UnitType): ArrayList<UnitModel> {
        val list: RealmResults<MeasureUnit> = when (type) {
            UnitType.LIFE -> realm.where(MeasureUnit::class.java).equalTo("unitType", 0).findAll()
            UnitType.NORMAL -> realm.where(MeasureUnit::class.java).equalTo("unitType", 1).findAll()
        }

/*
        for (a in realmData) {
            realm.beginTransaction()

            val temp = realm.createObject(MeasureUnit::class.java, newId())
            temp.unitNameBold = a.unitNameBold
            temp.unitNameSoft = a.unitNameSoft
            temp.unitStatus = a.unitStatus
            temp.unitType = a.unitType
            temp.unitValue = a.unitValue
            temp.unit = a.unit

            realm.commitTransaction()
        }
*/
        val result: ArrayList<UnitModel> = arrayListOf()

        for (item in list) {
            result.add(UnitModel(item.unitNameBold, item.unitNameSoft, type))
        }

        return result
    }

    private fun setRecyclerViewUnitModel(type: UnitType) {
        unitRecyclerView.removeAll()
        unitRecyclerView.addItems(selectUnitItems(type))
    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc, container, false)
        binding.calculatorVM = calculatorViewModel
        calculatorViewModel.context = context!!
        binding.lifecycleOwner = this

        realm = Realm.getDefaultInstance()

        return binding.root
    }

    override fun onDestroy() {
        calculatorViewModel.init()
        super.onDestroy()
    }
}