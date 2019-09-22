package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.views.calc.entity.*
import cookcook.nexters.com.amoogye.views.calc.history.CalcHistory
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import cookcook.nexters.com.amoogye.views.tools.TYPE_FOOD
import cookcook.nexters.com.amoogye.views.tools.TYPE_LIFE
import cookcook.nexters.com.amoogye.views.tools.TYPE_NORMAL
import io.realm.Realm
import io.realm.RealmResults
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList

const val MAX_ITEM_VIEWING_SIZE = 9

class CalculatorViewModel2 : ViewModel() {

    lateinit var context: Context

    val TAG: String = CalculatorViewModel2::class.java.simpleName!!

    // 에딧 텍스트
    var humanOne: MutableLiveData<String> = MutableLiveData() // 1명 기준
    var humanTwo: MutableLiveData<String> = MutableLiveData() // 2명 으로
    var unit: MutableLiveData<String> = MutableLiveData() // ml
    var amount: MutableLiveData<String> = MutableLiveData() // 10
    var ingredient: MutableLiveData<String> = MutableLiveData() // 굴소스
    var tool: MutableLiveData<String> = MutableLiveData() // 아빠숟가락

    var selectedEditBox: MutableLiveData<EditTextType> = MutableLiveData()
    var calcKeyboardType: MutableLiveData<CalcLayoutState> = MutableLiveData() // 키보드 타입
    var currentCalcState: MutableLiveData<CalcTypeState> = MutableLiveData() // 계산 상태 (인원, 재료, 인원 + 재료)
    var useIngredient: MutableLiveData<Boolean> = MutableLiveData() // 무게 단위 사용 여부

    var alertText: MutableLiveData<String> = MutableLiveData()

    var beforeCalcState: MutableLiveData<CalcTypeState> = MutableLiveData() // TODO 인터렉션
    var selectedUnitType: CalcUnitType

    private var selectedUnitObject: UnitModel? = null
    private var selectedToolObject: UnitModel? = null
    private var selectedIngredientObject: MeasureUnit? = null

    private var index: Int
    private var itemSize: Int

    var realm: Realm = Realm.getDefaultInstance()

    private var unitLifeList: RealmResults<MeasureUnit>
    private var unitNormalList: RealmResults<MeasureUnit>
    var foodList: RealmResults<MeasureUnit>

    init {

        this.selectedEditBox.value = EditTextType.AMOUNT
        this.currentCalcState.value = CalcTypeState.MATERIAL
        this.calcKeyboardType.value = CalcLayoutState.NUMBER
        this.useIngredient.value = false
        this.selectedUnitType = CalcUnitType.NORMAL
        this.amount.value = "0"

        this.index = 0
        this.itemSize = 0

        unitLifeList = realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_LIFE).findAll()
        unitNormalList = realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_NORMAL).findAll()
        foodList = realm.where(MeasureUnit::class.java).equalTo("unitType", TYPE_FOOD).findAll()

        val initData = unitNormalList.find { it.unitId == 15L }!!

        val unitModel = UnitModel(
            initData.unitNameBold,
            initData.unitNameSoft,
            initData.unitType,
            initData.isWeight,
            initData.unitValue
        )

        this.selectedUnitObject = unitModel
        this.unit.value = unitModel.abbreviation
        this.useIngredient.value = unitModel.isWeight
    }

    fun onSelectUnit(unitModel: UnitModel) {
        when {
            selectedEditBox.value == EditTextType.UNIT -> {
                this.selectedUnitObject = unitModel
                this.unit.value = unitModel.abbreviation
                this.useIngredient.value = unitModel.isWeight
            }
            selectedEditBox.value === EditTextType.TOOL -> {
                this.selectedToolObject = unitModel
                this.tool.value = unitModel.abbreviation
            }
            else -> {
                Log.e(TAG, "잘못된 타입입니다.")
            }
        }
    }

    private fun unitItemList(): RealmResults<MeasureUnit> {
        return if (this.selectedUnitType == CalcUnitType.NORMAL) {
            unitNormalList
        } else {
            unitLifeList
        }
    }

    fun convertUnitItemList(): ArrayList<UnitModel> {
        val result: ArrayList<UnitModel> = arrayListOf()

        val list = unitItemList()
        itemSize = list.size

        val max = (itemSize / 10) + 1

        if (index > max - 1) {
            index = max - 1
        }

        val startIndex = index * MAX_ITEM_VIEWING_SIZE
        var endIndex = index * MAX_ITEM_VIEWING_SIZE + MAX_ITEM_VIEWING_SIZE

        if (endIndex > itemSize) {
            endIndex = itemSize
        }


        for (x in startIndex until endIndex) {
            result.add(
                UnitModel(
                    list[x].unitNameBold,
                    list[x].unitNameSoft,
                    list[x].unitType,
                    list[x].isWeight,
                    list[x].unitValue
                )
            )
        }
        return result
    }

    fun onSelectedEditBox(type: EditTextType) {
        this.selectedEditBox.value = type
    }

    fun onSelectIngredient(ingredient: String) {
        val ingredientObject = foodList.find { it.unitNameBold == ingredient }
        this.selectedIngredientObject = ingredientObject
        this.ingredient.value = ingredient
    }

    fun onNumberButtonClick(number: String) {
        when (selectedEditBox.value) {
            EditTextType.HUMAN_ONE -> {
                humanOne.value = humanOne.value?.let { it ->
                    changeNumberText(number, it)
                } ?: changeNumberText(number, "0")
            }
            EditTextType.HUMAN_TWO -> {
                humanTwo.value = humanTwo.value?.let { it ->
                    changeNumberText(number, it)
                } ?: changeNumberText(number, "0")
            }
            EditTextType.AMOUNT -> {
                amount.value = amount.value?.let { it ->
                    changeNumberText(number, it)
                } ?: changeNumberText(number, "0")
            }
            else ->
                Log.e("Error", "Number Button Click Error : Invalid Type")
        }
    }

    private fun changeNumberText(number: String, text: String): String {
        var afterText: String = text

        if (number == "delete" && text.isNotEmpty()) {
            afterText = text.substring(0, text.length - 1)
        } else {
            if (number != "delete") {
                if (afterText == "0") {
                    afterText = if (number == ".") {
                        "0."
                    } else {
                        number
                    }
                } else {
                    if (number == ".") {
                        if (afterText.any { it == '.' }) {
                            return afterText
                        }
                    }
                    if (afterText.any { it == '.' }) {
                        val dump = afterText.split('.')
                        if (dump[1].isNotEmpty()) {
                            alertText.value = "숫자는 소수점 첫째자리까지 입력할 수 있습니다."
                            return afterText
                        }
                    }
                    afterText += number
                }
            }
        }

        if (afterText.isEmpty()) {
            return "0"
        }

        if ((afterText.toCharArray()[afterText.lastIndex] != '.') && afterText.toDouble() > 9999) {
            alertText.value = "숫자는 9,999까지 입력할 수 있습니다."
            afterText = "9999"
        }

        return afterText
    }

    // 선택한 계산 상태
    fun onChangeCalcState(type: CalcTypeState) {
        beforeCalcState.value = type
        when (type) {
            // 재료를 클릭 했다면
            CalcTypeState.MATERIAL -> {
                when (currentCalcState.value) {
                    CalcTypeState.MATERIAL -> {
                        currentCalcState.value = CalcTypeState.PERSONNEL
                    }
                    CalcTypeState.PERSONNEL -> {
                        currentCalcState.value = CalcTypeState.MATERIAL_PERSONNEL
                    }
                    CalcTypeState.MATERIAL_PERSONNEL -> {
                        currentCalcState.value = CalcTypeState.PERSONNEL
                    }
                }
            }
            CalcTypeState.PERSONNEL -> {
                when (currentCalcState.value) {
                    CalcTypeState.MATERIAL -> {
                        currentCalcState.value = CalcTypeState.MATERIAL_PERSONNEL
                    }
                    CalcTypeState.PERSONNEL -> {
                        currentCalcState.value = CalcTypeState.MATERIAL
                    }
                    CalcTypeState.MATERIAL_PERSONNEL -> {
                        currentCalcState.value = CalcTypeState.MATERIAL
                    }
                }
            }
            else -> {
                Log.e(TAG, "잘못된 계산 타입입니다.")
            }
        }
    }

    fun checkCalculable(): Boolean {

        if (amount.value.isNullOrEmpty() || unit.value.isNullOrEmpty()) {
            return false
        }
        if (useIngredient.value!!) {
            if (ingredient.value.isNullOrEmpty()) {
                return false
            }
        }

        when (currentCalcState.value) {
            CalcTypeState.MATERIAL -> {
                if (tool.value.isNullOrEmpty()) {
                    return false
                }
            }
            CalcTypeState.PERSONNEL -> {
                if (humanOne.value.isNullOrEmpty() || humanTwo.value.isNullOrEmpty()) {
                    return false
                }
            }
            CalcTypeState.MATERIAL_PERSONNEL -> {
                if (tool.value.isNullOrEmpty()) {
                    return false
                }
                if (humanOne.value.isNullOrEmpty() || humanTwo.value.isNullOrEmpty()) {
                    return false
                }
            }
            else -> {
                return false
            }
        }
        return true
    }

    private fun removeDecimalPoint(text: String): String {
        return if (text.toCharArray()[text.lastIndex] == '.') {
            text.substring(0, text.lastIndex)
        } else {
            text
        }
    }

    fun calculation(): String {

        val amount = removeDecimalPoint(amount.value!!).toDouble()
        val unit = selectedUnitObject!!.oneMLValue
        val ingredient = if (useIngredient.value!!) {
            selectedIngredientObject!!.unitValue
        } else {
            1.0
        }
        val text: String
        when (currentCalcState.value) {
            CalcTypeState.MATERIAL -> {

                val tool = selectedToolObject!!.oneMLValue

                val result: BigDecimal = (
                        BigDecimal((amount * unit * ingredient) / tool)
                            .setScale(1, RoundingMode.HALF_UP)
                        )
                text = "$result ${this.tool.value}이다."
            }
            CalcTypeState.PERSONNEL -> {
                val humanOne = removeDecimalPoint(humanOne.value!!).toDouble()
                val humanTwo = removeDecimalPoint(humanTwo.value!!).toDouble()
                val result: BigDecimal = (
                        BigDecimal(((amount * unit * ingredient) / humanOne) * humanTwo)
                            .setScale(1, RoundingMode.HALF_UP)
                        )
                text = "$result ${this.unit.value}이다."
            }
            CalcTypeState.MATERIAL_PERSONNEL -> {
                val humanOne = removeDecimalPoint(humanOne.value!!).toDouble()
                val humanTwo = removeDecimalPoint(humanTwo.value!!).toDouble()
                val tool = selectedToolObject!!.oneMLValue
                val result: BigDecimal = (
                        BigDecimal((((amount * unit * ingredient) / humanOne) * humanTwo) / tool)
                            .setScale(1, RoundingMode.HALF_UP)
                        )
                text = "$result ${this.tool.value}이다."
            }
            else -> {
                Log.e(TAG, "잘못된 상태입니다.")
                text = ""
            }
        }

        realm.beginTransaction()
        val newItemId = newId()
        val newItem = realm.createObject(CalcHistory::class.java, newItemId)
        newItem.calcResultBefore = "$amount${selectedUnitObject!!.abbreviation}"
        newItem.calcResultAfter = text
        newItem.createDate = Date().time
        realm.commitTransaction()
        return text
    }

    private fun newId(): Long {
        val maxId = realm.where(CalcHistory::class.java).max("historyId")
        if (maxId != null) {
            return maxId.toLong() + 1
        }
        return 0
    }

    fun reduceIndex() {
        this.index--
    }

    fun increaseIndex() {
        this.index++
    }

    // 이거 사용해서 버튼 disable 구분
    fun isClickNextButton(): Boolean {
        if (itemSize / 10 == index) {
            return false
        }
        return true
    }

    fun isClickPrevButton(): Boolean {
        if (index == 0) {
            return false
        }
        return true
    }
}