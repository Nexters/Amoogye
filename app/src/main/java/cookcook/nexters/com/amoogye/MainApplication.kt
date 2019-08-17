package cookcook.nexters.com.amoogye

import android.app.Application
import cookcook.nexters.com.amoogye.utils.realmData
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder().initialData { realm ->
            realmData.map {
                realm?.apply {
                    val item = this.createObject(MeasureUnit::class.java, it.unitId)
                    item.unitNameBold = it.unitNameBold
                    item.unitNameSoft = it.unitNameSoft
                }
            }
        }.build()
        Realm.setDefaultConfiguration(realmConfiguration)
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule)
        }
    }
}