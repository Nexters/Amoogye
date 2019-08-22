package cookcook.nexters.com.amoogye

import android.app.Application
import cookcook.nexters.com.amoogye.utils.realmData
import cookcook.nexters.com.amoogye.views.calc.history.CalcHistory
import cookcook.nexters.com.amoogye.views.calc.history.historyRealmData
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.annotations.RealmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@RealmModule(classes = arrayOf(MeasureUnit::class, CalcHistory::class))
private class UnitModule {
}


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

            historyRealmData.map {
                realm?.apply {
                    val item = this.createObject(CalcHistory::class.java, it.historyId)
                    item.createDate = it.createDate
                    item.calcResultBefore = it.calcResultBefore
                    item.calcResultAfter = it.calcResultAfter
                }
            }
        }.modules(UnitModule()).build()

         Realm.setDefaultConfiguration(realmConfiguration)

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule)
        }
    }
}