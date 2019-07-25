package cookcook.nexters.com.amoogye.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

abstract class BaseActivity : AppCompatActivity(){
    abstract val layoutRes: Int
    abstract val isUseDatabinding: Boolean
    lateinit var dialog: DefaultDialog

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    inline fun <reified T : Activity>
            Context.startActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (layoutRes != 0) {
            if (!isUseDatabinding)
                setContentView(layoutRes)
            else
                onDataBinding()
        }

        setupViews()
        subscribeUI()
    }

    open fun onDataBinding() {

    }

    // 뷰 컴포넌트 설정
    abstract fun setupViews()

    // livedata 있는 경우 observe 설정
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

    // fragment가 있을 경우 transacion 진행
    fun transaction(targetId : Int, newFragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(targetId, newFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        // transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }
}