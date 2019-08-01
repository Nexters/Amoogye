package cookcook.nexters.com.amoogye.views.calc.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment

class IngredientFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc_ingredient

    override fun setupViews(view: View) {

    }

    override fun subscribeUI() {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_calc_ingredient, container, false)
    }
}