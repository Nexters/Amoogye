package cookcook.nexters.com.amoogye.views.calc.presenter

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
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
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import cookcook.nexters.com.amoogye.views.tools.TYPE_FOOD
import cookcook.nexters.com.amoogye.views.tools.TYPE_LIFE
import cookcook.nexters.com.amoogye.views.tools.TYPE_NORMAL
import cookcook.nexters.com.amoogye.utils.realmData
import cookcook.nexters.com.amoogye.views.calc.history.CalcHistoryActivity
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

    private var currentContainerCase: Int = 0

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
        btn_history.setOnClickListener {
            val intent = Intent(context, CalcHistoryActivity::class.java)
            startActivity(intent)

        }
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

//        itemChange(calculatorViewModel.flag - 1)
//        unitRecyclerView = UnitButtonActivity(view)

        /* TODO: 현재는 default로 일반 단위로 초기화되어있음 이후에는 선택 된 것을 기준으로 초기화하자. */
//        unitRecyclerView.addItems(selectUnitItems(UnitType.NORMAL))

        txt_ingredient.setOnClickListener {
            changeCalcContainerLayout(1)
        }

        txt_human.setOnClickListener {
            changeCalcContainerLayout(2)
        }

        btn_unit_changer.setOnClickListener {
            callUnitSelector()
        }

        btn_unit_changer_previous.setOnClickListener {
            if (!calculatorViewModel.isClickPrevButton()) {
                return@setOnClickListener
            }
            calculatorViewModel.reduceIndex()
            unitRecyclerView.addItems(selectUnitItems(calculatorViewModel.currentSelectedType))
        }

        btn_unit_changer_next.setOnClickListener {

            if (!calculatorViewModel.isClickNextButton()) {
                return@setOnClickListener
            }

            calculatorViewModel.increaseIndex()
            unitRecyclerView.addItems(selectUnitItems(calculatorViewModel.currentSelectedType))
        }

        val data = realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_FOOD).findAll()


        val items: ArrayList<String> = arrayListOf()

        data.map {items.add(it.unitNameBold)}

        val picker = BaseScrollPicker(view, items)

        picker.wheelView.setLoopListener {
            calculatorViewModel.setIngredient(items.get(it))
            calculatorViewModel.ingredientObject = data.find { j -> j.unitNameBold == items[it]}
        }

        itemChange(calculatorViewModel.flag - 1)
        unitRecyclerView = UnitButtonActivity(view)
        unitRecyclerView.addItems(selectUnitItems(calculatorViewModel.currentSelectedType))

        val initData = realm.where(MeasureUnit::class.java).equalTo("unitId", 5).findFirst()
        calculatorViewModel.unitObject = UnitModel(initData.unitNameBold, initData.unitNameSoft, initData.unitType, initData.isWeight, initData.unitValue)
        calculatorViewModel.setUnit(initData.unitNameBold)

        btn_calc_button.setOnClickListener {
            var humanOne = calculatorViewModel.humanOne.value
            var humanOneValue = 1
            if (!humanOne!!.isEmpty()) {
                humanOneValue = humanOne.toInt()
            }

            var humanTwo = calculatorViewModel.humanTwo.value
            var humanTwoValue = 1
            if (!humanOne.isEmpty()) {
                humanTwoValue = humanTwo!!.toInt()
            }

            var amount = calculatorViewModel.amount.value
            var amoutValue = 1
            if (!amount!!.isEmpty()) {
                amoutValue = amount.toInt()
            }

            var unit = calculatorViewModel.unitObject
            var tool = calculatorViewModel.toolObject
            var ingredient = calculatorViewModel.ingredientObject


            var beforeValue = unit!!.oneMLValue * amoutValue // 10(amount) L (unit)를
            var changeValue = tool!!.oneMLValue // ml로 바꾸면

            var result = beforeValue / changeValue

            Log.d("TAG", "human: ${humanOneValue} human2 ${humanTwoValue} unit ${unit.oneMLValue} tool: ${tool.oneMLValue}")

            Log.d("TAG", result.toString())
            txt_calc_result.text = "$result ${tool.abbreviation}이다."

            btn_calc_button.visibility = View.GONE
            layout_calc_result.visibility = View.VISIBLE
            calc_frame_layout.visibility = View.INVISIBLE
        }

        layout_calc_delete_wrap.setOnClickListener {
            btn_calc_button.visibility = View.VISIBLE
            layout_calc_result.visibility = View.GONE
            calc_frame_layout.visibility = View.VISIBLE
        }
    }

    private fun itemChange(containerCase: Int) {

        Log.d("TAG", "item change case $containerCase")
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
                        humanTopWidth = hide_layout_human_top_standard.measuredWidth
                        humanBottomWidth = hide_layout_human_bottom_standard.measuredWidth

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
                        set.interpolator = AccelerateDecelerateInterpolator()
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
                        ingredientTopWidth = hide_layout_ingredient_top_standard.measuredWidth
                        ingredientBottomWidth = hide_layout_ingredient_bottom_standard.measuredWidth
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
                        set.interpolator = AccelerateDecelerateInterpolator()
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
                        set.interpolator = AccelerateDecelerateInterpolator()
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
                        set.interpolator = AccelerateDecelerateInterpolator()
                        set.start()
                    }

                    2 -> {
                        // 아무 일도 일어나지 않는다.
                    }
                }
            }
            else -> {

            }
        }

        this.currentContainerCase = number
    }

    fun showWeight() {

        val weightWidth = hide_layout_ingredient_wrap.layoutParams.width

        val animation =
            ValueAnimator.ofInt(0, weightWidth).setDuration(200)
        animation.addUpdateListener {
            val value = it.animatedValue as Int
            layout_ingredient_wrap.layoutParams.width = value
            layout_ingredient_wrap.requestLayout()
        }

        val set = AnimatorSet()
        set.play(animation)
        set.interpolator = AccelerateDecelerateInterpolator()
        set.start()
    }

    fun hideWeight() {

        val weightWidth = hide_layout_ingredient_wrap.layoutParams.width

        val animation =
            ValueAnimator.ofInt(weightWidth, 0).setDuration(200)
        animation.addUpdateListener {
            val value = it.animatedValue as Int
            layout_ingredient_wrap.layoutParams.width = value
            layout_ingredient_wrap.requestLayout()
        }

        val set = AnimatorSet()
        set.play(animation)
        set.interpolator = AccelerateDecelerateInterpolator()
        set.start()
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
                        calculatorViewModel.currentSelectedType = 1
                        setRecyclerViewUnitModel(TYPE_LIFE)
                    } else {
                        txt_unit_changer.text = "일반단위"
                        calculatorViewModel.currentSelectedType = 0
                        setRecyclerViewUnitModel(TYPE_NORMAL)
                    }
                }

                override fun onDismiss(actionSheet: ActionSheet?, isCancel: Boolean) {
                    Log.d("", "")
                }
            }).show()
    }

    private fun selectUnitItems(type: Int): ArrayList<UnitModel> {
        val list: RealmResults<MeasureUnit> = when (type) {
            TYPE_LIFE -> {
                realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_LIFE).findAll()
            }
            TYPE_NORMAL -> {
                realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_NORMAL).findAll()
            }
            else -> {
                Log.e("TAG", "선언되지 않은 타입입니다.")
                return arrayListOf()
            }
        }

        val result: ArrayList<UnitModel> = arrayListOf()

        calculatorViewModel.itemSize = list.size

        var index = calculatorViewModel.index
        var size = 9
        var max = (list.size / 10) + 1

        if (index > max - 1) {
            index = max - 1
        }

        var startIndex = index * size
        var endIndex = index * size + size

        if (endIndex > list.size) {
            endIndex = list.size
        }


        for (x in startIndex until endIndex) {
            Log.d("TAG", list[x].unitNameSoft + " : " + list[x].isWeight)
            result.add(UnitModel(list[x].unitNameBold, list[x].unitNameSoft, list[x].unitType, list[x].isWeight, list[x].unitValue))
        }

//        for (item in list) {
//            result.add(UnitModel(item.unitNameBold, item.unitNameSoft, type))
//        }

        return result
    }

    private fun setRecyclerViewUnitModel(type: Int) {
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