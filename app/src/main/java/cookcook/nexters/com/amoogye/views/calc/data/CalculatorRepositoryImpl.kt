package cookcook.nexters.com.amoogye.views.calc.data

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository
import cookcook.nexters.com.amoogye.views.calc.presenter.IngredientFragment
import cookcook.nexters.com.amoogye.views.calc.presenter.PortionFragment
import cookcook.nexters.com.amoogye.views.calc.presenter.TwiceFragment

class CalculatorRepositoryImpl : CalculatorRepository {

    override fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}