package cookcook.nexters.com.amoogye.main.service

import android.content.Context
import android.widget.Toast
import cookcook.nexters.com.amoogye.main.domain.MainRepository

class MainRepositoryImpl: MainRepository {

    override fun showToast(context: Context) {
        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
    }

    override fun getMessage(): String {
        return "hello"
    }
}