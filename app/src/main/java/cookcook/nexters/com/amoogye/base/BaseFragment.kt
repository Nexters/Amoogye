package cookcook.nexters.com.amoogye.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    @get:LayoutRes
    abstract val layoutRes: Int
    open val isUseDataBinding: Boolean = false
    var activity: BaseActivity? = null
    lateinit var dialog: DefaultDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (layoutRes != 0) {
            return if (!isUseDataBinding) {
                inflater.inflate(layoutRes, container, false)
            } else {
                onDataBinding(inflater, container)
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    open fun onDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)
        subscribeUI()
    }

    abstract fun setupViews(view: View)

    abstract fun subscribeUI()

    // 다이얼로그 설정
    fun openDialog(
        context: Context,
        type: Int,
        message: String,
        layoutRes: Int,
        okListener: () -> Unit = {
            dialog.dismiss()
        },
        cancelListener: () -> Unit = {
            dialog.dismiss()
        }
    ) {
        dialog = DefaultDialog(context, okListener, cancelListener)
        // 초기 설정
        dialog.setInit(layoutRes, type)

        dialog.setTitle(message)

        dialog.callFunction()
    }

}