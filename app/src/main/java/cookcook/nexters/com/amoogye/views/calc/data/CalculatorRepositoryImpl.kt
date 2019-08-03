package cookcook.nexters.com.amoogye.views.calc.data

import android.content.Context
import android.widget.Toast
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository

class CalculatorRepositoryImpl : CalculatorRepository {

    override fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}