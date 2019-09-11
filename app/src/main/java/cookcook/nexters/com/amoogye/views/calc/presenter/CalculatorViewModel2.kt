package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.base.SingleLiveEvent
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository
import cookcook.nexters.com.amoogye.views.calc.entity.EditTextType

class CalculatorViewModel2(private val repo: CalculatorRepository) : ViewModel() {

    lateinit var context: Context

    // 에딧 텍스트
    var humanOne: MutableLiveData<String> = MutableLiveData()
    var humanTwo: MutableLiveData<String> = MutableLiveData()
    var unit: MutableLiveData<String> = MutableLiveData()
    var amount: MutableLiveData<String> = MutableLiveData()
    var ingredient: MutableLiveData<String> = MutableLiveData()
    var tool: MutableLiveData<String> = MutableLiveData()

    var selectedEditBox: MutableLiveData<EditTextType> = MutableLiveData()
    var calcKeyboardType: MutableLiveData<CalcLayoutState> = MutableLiveData() // 키보드 타입
    var currentCalcState: MutableLiveData<CalcTypeState> = MutableLiveData() // 계산 상태 (인원, 재료, 인원 + 재료)
    var isIngredient: MutableLiveData<Boolean> = MutableLiveData() // 무게 단위 사용 여부

    init {
        this.selectedEditBox.value = EditTextType.AMOUNT
        this.currentCalcState.value = CalcTypeState.MATERIAL
        this.isIngredient.value = false
        this.calcKeyboardType.value = CalcLayoutState.NUMBER
    }

    fun onSelectedEditBox(type: EditTextType) {
        this.selectedEditBox.value = type
    }

    fun onChangeSelectedEditBoxText(text: String) {
        when (this.selectedEditBox.value) {
            EditTextType.AMOUNT -> {
                this.amount.value += text
            }
            EditTextType.UNIT -> {
                this.unit.value += text
            }
            EditTextType.HUMAN_ONE -> {
                this.humanOne.value += text
            }
            EditTextType.HUMAN_TWO -> {
                this.humanTwo.value += text
            }
            EditTextType.INGREDIENT -> {
                this.ingredient.value += text
            }
            EditTextType.TOOL -> {
                this.tool.value += text
            }
        }
    }
}