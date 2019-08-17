package cookcook.nexters.com.amoogye.views.calc.presenter

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.base.BaseScrollPicker
import cookcook.nexters.com.amoogye.databinding.FragmentCalcBinding
import kotlinx.android.synthetic.main.fragment_calc.*
import org.koin.android.ext.android.get
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class CalcFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_calc
    override val isUseDataBinding = true
    lateinit var binding: FragmentCalcBinding
    private val calculatorViewModel: CalculatorViewModel = get()

    private val ingredientSelectStatus = arrayOf(true, false, true)
    private val portionSelectStatus = arrayOf(false, true, true)
    private val plusSelectStatus = arrayOf(false, false, true)

    private val calcBottomContainer: Array<Fragment> = arrayOf(
        IngredientFragment(),
        PortionFragment(),
        TwiceFragment()
    )

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: CalcFragment? = null

        fun getInstance(): CalcFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    CalcFragment()
            }
            return INSTANCE!!
        }

    }

    override fun setupViews(view: View) {

        btn_history.setOnClickListener { calculatorViewModel.gazuaa("history 구현 예정") }
        btn_tip.setOnClickListener { calculatorViewModel.gazuaa("tool_tip 구현 예정") }

        itemChange(calculatorViewModel.flag - 1)

        txt_ingredient.setOnClickListener {
            changeCalcContainerLayout(1)
        }

        txt_human.setOnClickListener {
            changeCalcContainerLayout(2)
        }

        val items: ArrayList<String> = arrayListOf(
            "jjjjjjjjj", "mmmmmmm","kkkkkkkk","kjkjkjkjkjk","kkkkkkkkkk","jijijijiji","mkmkmkmkmk","inknknknk","rdrdftft","gtfrdeswasrdf"
        )
        val picker = BaseScrollPicker(view, items)
    }

    private fun itemChange(containerCase: Int) {

        if (ingredientSelectStatus[containerCase]) {
            txt_ingredient.setTextColor(Color.parseColor("#131c32"))
        } else {
            txt_ingredient.setTextColor(Color.parseColor("#33131c32"))
        }

        if (portionSelectStatus[containerCase]) {
            txt_human.setTextColor(Color.parseColor("#131c32"))
        } else {
            txt_human.setTextColor(Color.parseColor("#33131c32"))
        }

        if (plusSelectStatus[containerCase]) {
            txt_calc_plus.setTextColor(Color.parseColor("#131c32"))
        } else {
            txt_calc_plus.setTextColor(Color.parseColor("#33131c32"))
        }

        updateContents(containerCase)
    }

    private fun updateContents(number: Int) {
//        layout_ingredient_top_standard.visibility = View.GONE
//        layout_ingredient_bottom_standard.visibility = View.GONE
//        layout_human_top_standard.visibility = View.GONE
//        layout_human_bottom_standard.visibility = View.GONE
//        layout_weight_standard.visibility = View.GONE

        when(number) {
            0 -> {
                // 재료
                layout_ingredient_top_standard.visibility = View.VISIBLE
                layout_ingredient_bottom_standard.visibility = View.VISIBLE
                layout_human_top_standard.visibility = View.GONE
                layout_human_bottom_standard.visibility = View.GONE
                layout_weight_standard.visibility = View.GONE
            }
            1 -> {
                // 인원
                Log.d("TAG", "layout param: ${layout_ingredient_top_standard.measuredWidth}")
                layout_ingredient_top_standard.visibility = View.VISIBLE
//                changeWidthInteraction(layout_ingredient_top_standard, layout_ingredient_top_standard.measuredWidth)
//                changeWidthInteraction(layout_ingredient_bottom_standard, layout_ingredient_bottom_standard.measuredWidth)

                val animation = ValueAnimator.ofInt(layout_ingredient_top_standard.measuredWidth, 0).setDuration(200)
                animation.addUpdateListener {
                    var value = it.animatedValue as Int
                    Log.d("TAG", "change value $value")
                    layout_ingredient_top_standard.layoutParams.width = value
                    layout_ingredient_top_standard.requestLayout()
                }

                val set: AnimatorSet = AnimatorSet()
                set.play(animation)
                set.setInterpolator(AccelerateDecelerateInterpolator())
                set.start()


                layout_human_top_standard.visibility = View.VISIBLE
                layout_human_bottom_standard.visibility = View.VISIBLE
            }
            2 -> {
                // 재료 + 인원
                layout_ingredient_top_standard.visibility = View.VISIBLE
                layout_ingredient_bottom_standard.visibility = View.VISIBLE
                layout_human_top_standard.visibility = View.VISIBLE
                layout_human_bottom_standard.visibility = View.VISIBLE
            }
            3 -> {
                // 질량
                layout_weight_standard.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 인터렉션 부분
     */
    private fun changeWidthInteraction(view: View, width: Int) {
        var reduceWidth = width / 500.0
        Log.d("TAG", "time is $width reduce is $reduceWidth")
        val timer: CountDownTimer = object : CountDownTimer(200, 1) {
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.d("TAG", "mill: $millisUntilFinished   reduce: $reduceWidth")
                view.layoutParams.width = (reduceWidth * millisUntilFinished).toInt()

                Log.d("TAG", "width: ${view.layoutParams.width}")
            }
        }
        timer.start()
    }

    private fun changeCalcContainerLayout(number: Int) {
        val containerCase = calculatorViewModel.convertFragment(number)
        itemChange(containerCase)
    }

    override fun subscribeUI() {
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calc, container, false)
        binding.calculatorVM = calculatorViewModel
        calculatorViewModel.context = context!!
        binding.lifecycleOwner = this

        return binding.root
    }
}