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

    override fun changeFragment(view: View, fragmentManager: FragmentManager, flag: Int, click: Int): Int {
        when (flag) {
            1 -> {
                if (click == 2) {
                    val transaction = fragmentManager.beginTransaction()

                    transaction.replace(R.id.calculator_container, TwiceFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                    transaction.commit()

                    ((view as RelativeLayout).getChildAt(0) as TextView).setTextColor(Color.parseColor("#131c32"))
                    (view.getChildAt(1) as TextView).setTextColor(Color.parseColor("#131c32"))
                    (view.getChildAt(2) as TextView).setTextColor(Color.parseColor("#131c32"))

                    return 3
                }
            }

            2 -> {
                if (click == 1) {
                    val transaction = fragmentManager.beginTransaction()

                    transaction.replace(R.id.calculator_container, TwiceFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                    transaction.commit()

                    ((view as RelativeLayout).getChildAt(0) as TextView).setTextColor(Color.parseColor("#131c32"))
                    (view.getChildAt(1) as TextView).setTextColor(Color.parseColor("#131c32"))
                    (view.getChildAt(2) as TextView).setTextColor(Color.parseColor("#131c32"))

                    return 3
                }
            }

            3 -> {
                if (click == 1) {
                    val transaction = fragmentManager.beginTransaction()

                    transaction.replace(R.id.calculator_container, PortionFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                    transaction.commit()

                    ((view as RelativeLayout).getChildAt(0) as TextView).setTextColor(Color.parseColor("#33131c32"))
                    (view.getChildAt(1) as TextView).setTextColor(Color.parseColor("#33131c32"))
                    (view.getChildAt(2) as TextView).setTextColor(Color.parseColor("#131c32"))

                    return 2
                } else if (click == 2) {
                    val transaction = fragmentManager.beginTransaction()

                    transaction.replace(R.id.calculator_container, IngredientFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                    transaction.commit()

                    ((view as RelativeLayout).getChildAt(0) as TextView).setTextColor(Color.parseColor("#131c32"))
                    (view.getChildAt(1) as TextView).setTextColor(Color.parseColor("#33131c32"))
                    (view.getChildAt(2) as TextView).setTextColor(Color.parseColor("#33131c32"))

                    return 1
                }
            }
        }

        return flag
    }
}