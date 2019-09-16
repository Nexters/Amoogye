package cookcook.nexters.com.amoogye.views.calc.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.baoyz.actionsheet.ActionSheet
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.base.BaseNumberButton
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import cookcook.nexters.com.amoogye.databinding.FragmentCalcBinding
import cookcook.nexters.com.amoogye.views.calc.entity.*
import cookcook.nexters.com.amoogye.views.calc.history.CalcHistoryActivity
import cookcook.nexters.com.amoogye.views.calc.presenter.adapter.AdapterListener
import kotlinx.android.synthetic.main.fragment_calc.*
import kotlinx.android.synthetic.main.layout_calc_visible_edit_text.*
import kotlinx.android.synthetic.main.layout_unit_button_wrap.*
import org.koin.android.ext.android.get


class CalcFragment2 : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc
    override val isUseDataBinding = true
    lateinit var binding: FragmentCalcBinding
    private val calculatorViewModel: CalculatorViewModel2 = get()
    private lateinit var unitRecyclerView: UnitButtonActivity

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: CalcFragment2? = null

        fun getInstance(): CalcFragment2 {
            if (INSTANCE == null) {
                INSTANCE =
                    CalcFragment2()
            }
            return INSTANCE!!
        }
    }

    private fun convertBooleanToVisibleType(isVisible: Boolean): Int {
        return if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun hideOrVisibleLayout(isVisibleHuman: Boolean = false, isVisibleMaterial: Boolean = false) {
        layout_human_top_standard.visibility = convertBooleanToVisibleType(isVisibleHuman)
        layout_human_bottom_standard.visibility = convertBooleanToVisibleType(isVisibleHuman)
        layout_ingredient_bottom_standard.visibility = convertBooleanToVisibleType(isVisibleMaterial)
    }

    private val changeSelectedEditText = Observer<EditTextType> { type ->
        edit_twice_human_one.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_amount.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_unit.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_ingredient.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_human_two.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)
        edit_twice_tool.setBackgroundResource(R.drawable.number_input_non_focus_wrap_rounded_box)

        when (type!!) {
            EditTextType.HUMAN_ONE -> {
                edit_twice_human_one.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                calculatorViewModel.calcKeyboardType.value = CalcLayoutState.NUMBER
            }
            EditTextType.AMOUNT -> {
                edit_twice_amount.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                calculatorViewModel.calcKeyboardType.value = CalcLayoutState.NUMBER
            }
            EditTextType.UNIT -> {
                edit_twice_unit.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                calculatorViewModel.calcKeyboardType.value = CalcLayoutState.UNIT
            }
            EditTextType.INGREDIENT -> {
                edit_twice_ingredient.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                calculatorViewModel.calcKeyboardType.value = CalcLayoutState.INGREDIENT
            }
            EditTextType.HUMAN_TWO -> {
                edit_twice_human_two.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                calculatorViewModel.calcKeyboardType.value = CalcLayoutState.NUMBER
            }
            EditTextType.TOOL -> {
                edit_twice_tool.setBackgroundResource(R.drawable.number_input_wrap_rounded_box)
                calculatorViewModel.calcKeyboardType.value = CalcLayoutState.TOOL
            }
        }
    }

    private val changeKeyboard = Observer<CalcLayoutState> { state ->
        calc_layout_button.visibility = View.GONE
        calc_layout_unit.visibility = View.GONE
        calc_layout_ingredient.visibility = View.GONE

        when (state!!) {
            CalcLayoutState.NUMBER -> {
                calc_layout_button.visibility = View.VISIBLE
            }
            CalcLayoutState.INGREDIENT -> {
                calc_layout_ingredient.visibility = View.VISIBLE
            }
            CalcLayoutState.TOOL,
            CalcLayoutState.UNIT -> {
                calc_layout_unit.visibility = View.VISIBLE
            }
        }
    }

    private val changeUI = Observer<CalcTypeState> { state ->
        when (state!!) {
            CalcTypeState.MATERIAL -> {
                txt_ingredient.setTextColor(Color.parseColor("#131c32"))
                txt_human.setTextColor(Color.parseColor("#33131c32"))
                txt_calc_plus.setTextColor(Color.parseColor("#33131c32"))
                hideOrVisibleLayout(isVisibleHuman = false, isVisibleMaterial = true)
            }
            CalcTypeState.PERSONNEL -> {
                txt_human.setTextColor(Color.parseColor("#131c32"))
                txt_ingredient.setTextColor(Color.parseColor("#33131c32"))
                txt_calc_plus.setTextColor(Color.parseColor("#33131c32"))
                hideOrVisibleLayout(isVisibleHuman = true, isVisibleMaterial = false)
            }
            CalcTypeState.MATERIAL_PERSONNEL -> {
                txt_ingredient.setTextColor(Color.parseColor("#131c32"))
                txt_human.setTextColor(Color.parseColor("#131c32"))
                txt_calc_plus.setTextColor(Color.parseColor("#131c32"))
                hideOrVisibleLayout(isVisibleHuman = true, isVisibleMaterial = true)
            }
        }
    }

    private val visibleWeightEditText = Observer<Boolean> { isVisible ->
        //        layout_weight_standard.visibility = convertBooleanToVisibleType(isVisible)
        layout_ingredient_wrap.visibility = convertBooleanToVisibleType(isVisible)
    }

    private val onClick: (number: String) -> Unit = {
        calculatorViewModel.onNumberButtonClick(it)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun editTextSetting(editText: EditText, type: EditTextType) {
        editText.inputType = InputType.TYPE_NULL
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.showSoftInputOnFocus = false
        }
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                calculatorViewModel.onSelectedEditBox(type)
            }

            return@setOnTouchListener false
        }
    }

    override fun setupViews(view: View) {
        calculatorViewModel.currentCalcState.observe(this, changeUI)
        calculatorViewModel.useIngredient.observe(this, visibleWeightEditText)
        calculatorViewModel.selectedEditBox.observe(this, changeSelectedEditText)
        calculatorViewModel.calcKeyboardType.observe(this, changeKeyboard)

        BaseNumberButton(view, onClick)

        editTextSetting(edit_twice_amount, EditTextType.AMOUNT)
        editTextSetting(edit_twice_human_one, EditTextType.HUMAN_ONE)
        editTextSetting(edit_twice_human_two, EditTextType.HUMAN_TWO)
        editTextSetting(edit_twice_ingredient, EditTextType.INGREDIENT)
        editTextSetting(edit_twice_tool, EditTextType.TOOL)
        editTextSetting(edit_twice_unit, EditTextType.UNIT)

        unitRecyclerView = UnitButtonActivity(view, adapterListener)
        unitRecyclerView.addItems(calculatorViewModel.convertUnitItemList())

        btn_history.setOnClickListener {
            val intent = Intent(context, CalcHistoryActivity::class.java)
            startActivity(intent)

        }

        btn_tip.setOnClickListener { Toast.makeText(this.context, "구현 예정", Toast.LENGTH_SHORT).show() }

        btn_calc_button.setOnClickListener {
            if (calculatorViewModel.checkCalculable()) {
                calculation()
            }
        }

        btn_unit_changer_previous.setOnClickListener {
            if (!calculatorViewModel.isClickPrevButton()) {
                return@setOnClickListener
            }
            calculatorViewModel.reduceIndex()
            unitRecyclerView.addItems(calculatorViewModel.convertUnitItemList())
        }

        btn_unit_changer.setOnClickListener {
            callUnitSelector()
        }

        btn_unit_changer_next.setOnClickListener {

            if (!calculatorViewModel.isClickNextButton()) {
                return@setOnClickListener
            }

            calculatorViewModel.increaseIndex()
            unitRecyclerView.addItems(calculatorViewModel.convertUnitItemList())
        }

        layout_calc_delete_wrap.setOnClickListener {
            btn_calc_button.visibility = View.VISIBLE
            layout_calc_result.visibility = View.GONE
            calc_frame_layout.visibility = View.VISIBLE
        }

        val items: ArrayList<String> = arrayListOf()

        calculatorViewModel.foodList.map { items.add(it.unitNameBold) }
        val picker = BaseScrollPicker(view, items)

        picker.wheelView.setLoopListener {
            calculatorViewModel.onSelectIngredient(items[it])
        }
    }

    private fun callUnitSelector() {
        context!!.setTheme(R.style.ActionSheetStyleiOS7)

        ActionSheet.createBuilder(context, fragmentManager)
            .setCancelButtonTitle("취소")
            .setOtherButtonTitles("생활단위", "일반단위")
            .setCancelableOnTouchOutside(true)
            .setListener(object : ActionSheet.ActionSheetListener {
                override fun onOtherButtonClick(actionSheet: ActionSheet?, index: Int) {
                    if (index == 0) {
                        txt_unit_changer.text = "생활단위"
                        calculatorViewModel.selectedUnitType = CalcUnitType.LIFE
                    } else {
                        txt_unit_changer.text = "일반단위"
                        calculatorViewModel.selectedUnitType = CalcUnitType.NORMAL
                    }
                    unitRecyclerView.addItems(calculatorViewModel.convertUnitItemList())
                }

                override fun onDismiss(actionSheet: ActionSheet?, isCancel: Boolean) {
                    Log.d("", "")
                }
            }).show()
    }

    private fun calculation() {

        txt_calc_result.text = calculatorViewModel.calculation()
        btn_calc_button.visibility = View.GONE
        layout_calc_result.visibility = View.VISIBLE
        calc_frame_layout.visibility = View.INVISIBLE
    }

    private val adapterListener = object : AdapterListener {
        override fun click(item: UnitModel) {
            calculatorViewModel.onSelectUnit(item)
        }
    }


    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc, container, false)
        binding.vm = calculatorViewModel
        calculatorViewModel.context = context!!
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}