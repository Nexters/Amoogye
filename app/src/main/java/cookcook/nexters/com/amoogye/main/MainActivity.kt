package cookcook.nexters.com.amoogye.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cookcook.nexters.com.amoogye.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.sayHello(this)
    }
}
