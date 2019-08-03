package cookcook.nexters.com.amoogye.views.calc.data

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository
import cookcook.nexters.com.amoogye.views.calc.presenter.IngredientFragment
import cookcook.nexters.com.amoogye.views.calc.presenter.PortionFragment
import kotlinx.android.synthetic.main.fragment_calc.*

class CalculatorRepositoryImpl : CalculatorRepository {

    override fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun changeFragment(id: Int, destination: BaseFragment, fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.calculator_container, destination)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        transaction.commit()
    }
}