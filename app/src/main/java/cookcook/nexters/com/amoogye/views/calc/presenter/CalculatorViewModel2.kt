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

    var humanOne: MutableLiveData<String> = MutableLiveData()
    var humanTwo: MutableLiveData<String> = MutableLiveData()
    var unit: MutableLiveData<String> = MutableLiveData()
    var amount: MutableLiveData<String> = MutableLiveData()
    var ingredient: MutableLiveData<String> = MutableLiveData()
    var tool: MutableLiveData<String> = MutableLiveData()

    var selectedEditBox: MutableLiveData<EditTextType> = MutableLiveData()

    init {
        this.selectedEditBox.value = EditTextType.AMOUNT
    }

    fun onSelectedEditBox(type: EditTextType) {
        this.selectedEditBox.value = type
    }


}