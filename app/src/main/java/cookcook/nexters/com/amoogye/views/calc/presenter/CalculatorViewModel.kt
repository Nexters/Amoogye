package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import android.os.Build
import android.text.InputType
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository
import cookcook.nexters.com.amoogye.views.calc.entity.EditTextType

class CalculatorViewModel(private val repo: CalculatorRepository) : ViewModel() {
    lateinit var context: Context
    var flag: Int = 1

    var index: Int = 0
    var currentSelectedType: Int = 0

    var itemSize: Int = 0

    // flag
    private var ingredientSelected = true
    private var portionSelected = false

    private val _selectedEditText = MutableLiveData<EditTextType>()
    private val _humanOne = MutableLiveData<String>()
    private val _amount = MutableLiveData<String>()
    private val _unit = MutableLiveData<String>()
    private val _ingredient = MutableLiveData<String>()
    private val _humanTwo = MutableLiveData<String>()
    private val _tool = MutableLiveData<String>()

    val humanOne: LiveData<String> get() = _humanOne
    val amount: LiveData<String> get() = _amount
    val unit: LiveData<String> get() = _unit
    val ingredient: LiveData<String> get() = _ingredient
    val humanTwo: LiveData<String> get() = _humanTwo
    val tool: LiveData<String> get() = _tool

    init {
        _humanOne.value = ""
        _amount.value = ""
        _humanTwo.value = ""
    }

    fun init() {
        ingredientSelected = true
        portionSelected = false
        flag = 1
        _humanOne.value = ""
        _amount.value = ""
        _humanTwo.value = ""
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

    fun gazuaa(text: String) = repo.showToast(context, text)

    fun convertFragment(buttonId: Int): Int {
        Log.d("TAG", "minus $buttonId")
        when (buttonId) {
            1 -> {
                ingredientSelected = !ingredientSelected
                if (ingredientSelected) {
                    val dump = this.flag + buttonId
                    if (dump != 1) {
                        this.flag = dump
                    } else {
                        ingredientSelected = !ingredientSelected
                    }
                } else {
                    val dump = this.flag - buttonId
                    if (dump != 0) {
                        this.flag = dump
                    } else {
                        ingredientSelected = !ingredientSelected
                    }
                }
            }
            2 -> {
                portionSelected = !portionSelected
                if (portionSelected) {
                    Log.d("TAG","Portion selected is $portionSelected")
                    val dump = this.flag + buttonId
                    if (dump != 2) {
                        this.flag = dump
                    } else {
                        portionSelected = !portionSelected
                    }
                } else {
                    Log.d("TAG", "Portion selected is $portionSelected")
                    val dump = this.flag - buttonId
                    if (dump != 0) {
                        this.flag = dump
                    } else {
                        portionSelected = !portionSelected
                    }
                }
            }
        }

        Log.d("TAG", "convert is $flag")

        return this.flag - 1
    }

    fun calculatorEditTextSetting(editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.showSoftInputOnFocus = false
        }
    }

    fun setSelectedEditText(value: EditTextType) {
        _selectedEditText.value = value
    }

    fun getSelectedEditText() = _selectedEditText.value

    fun onNumberButtonClick(number: String) {
        when (_selectedEditText.value) {
            EditTextType.HUMAN_ONE -> {
                _humanOne.value = _humanOne.value?.let { it ->
                    repo.changeNumberText(number, it)
                } ?: repo.changeNumberText(number, "0")
            }
            EditTextType.AMOUNT -> {
                _amount.value = _amount.value?.let { it ->
                    repo.changeNumberText(number, it)
                } ?: repo.changeNumberText(number, "0")
            }
            EditTextType.INGREDIENT -> {

            }
            EditTextType.HUMAN_TWO -> {
                _humanTwo.value = _humanTwo.value?.let { it ->
                    repo.changeNumberText(number, it)
                } ?: repo.changeNumberText(number, "0")
            }
            else -> {
                // 아무것도 안한다.
            }
        }
    }

    fun onUnitButtonClick(value: String) {
        when (_selectedEditText.value) {
            EditTextType.UNIT -> {
                _unit.value = value
            }
            EditTextType.TOOL -> {
                _tool.value = value
            }
            else -> {
                // 아무것도 안한다.
            }
        }
    }
}