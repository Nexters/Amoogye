package cookcook.nexters.com.amoogye

import android.app.Application
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule)
        }
    }
}