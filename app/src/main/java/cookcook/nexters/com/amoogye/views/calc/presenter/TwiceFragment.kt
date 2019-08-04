package cookcook.nexters.com.amoogye.views.calc.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.databinding.FragmentCalcTwiceBinding

class TwiceFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc_twice
    private lateinit var binding: FragmentCalcTwiceBinding

    override fun setupViews(view: View) {
    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc_twice, container, false)

        return binding.root
    }
}