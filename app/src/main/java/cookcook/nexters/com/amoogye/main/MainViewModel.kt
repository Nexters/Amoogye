package cookcook.nexters.com.amoogye.main

import android.content.Context
import androidx.lifecycle.ViewModel
import cookcook.nexters.com.amoogye.main.domain.MainRepository


class MainViewModel(private val repo : MainRepository) : ViewModel() {

    fun sayHello(context: Context) = "${repo.showToast(context)} from $this"

    val text = repo.getMessage()
}