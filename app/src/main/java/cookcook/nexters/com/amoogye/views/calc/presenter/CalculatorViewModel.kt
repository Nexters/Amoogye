package cookcook.nexters.com.amoogye.views.calc.presenter

import android.content.Context
import android.os.Build
import android.text.InputType
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository
import cookcook.nexters.com.amoogye.views.calc.entity.EditTextType

class CalculatorViewModel(private val repo: CalculatorRepository) : ViewModel() {
    lateinit var context: Context
    var flag: Int = 3

    // flag
    private var ingredientSelected = true
    private var portionSelected = true

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
        _humanOne.value = "0"
        _amount.value = "0"
        _humanTwo.value = "0"
    }

    fun init() {
        ingredientSelected = true
        portionSelected = true
        flag = 3
    }

    fun gazuaa(text: String) = repo.showToast(context, text)

    fun convertFragment(buttonId: Int): Int {
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
                    val dump = this.flag + buttonId
                    if (dump != 2) {
                        this.flag = dump
                    } else {
                        portionSelected = !portionSelected
                    }
                } else {
                    val dump = this.flag - buttonId
                    if (dump != 0) {
                        this.flag = dump
                    } else {
                        portionSelected = !portionSelected
                    }
                }
            }
        }

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
                _humanOne.value = _humanOne.value?.let { it->
                    repo.changeText(number, it)
                } ?: repo.changeText(number, "0")
            }
            EditTextType.AMOUNT -> {
                _amount.value = _amount.value?.let { it ->
                    repo.changeText(number, it)
                } ?: repo.changeText(number, "0")
            }
            EditTextType.UNIT -> {

            }
            EditTextType.INGREDIENT -> {

            }
            EditTextType.HUMAN_TWO -> {
                _humanTwo.value = _humanTwo.value?.let { it ->
                    repo.changeText(number, it)
                } ?: repo.changeText(number, "0")
            }
            EditTextType.TOOL -> {

            }
        }
    }
}