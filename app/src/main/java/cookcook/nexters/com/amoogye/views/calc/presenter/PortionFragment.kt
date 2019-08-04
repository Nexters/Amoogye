package cookcook.nexters.com.amoogye.views.calc.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.databinding.FragmentCalcPortionBinding

class PortionFragment: BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc_portion
    private lateinit var binding: FragmentCalcPortionBinding
    override val isUseDataBinding = true

    override fun setupViews(view: View) {

    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc_portion, container, false)

        return binding.root
    }
}