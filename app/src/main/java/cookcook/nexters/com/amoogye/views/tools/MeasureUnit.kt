package cookcook.nexters.com.amoogye.views.tools


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MeasureUnit(
    @PrimaryKey var unitId: Long = 0, var unitType: Int = 0, var unitStatus: Int = 0,
    var unitNameBold: String = "", var unitNameSoft: String = "",
    var unit: String = "", var unitValue: Int = 0
) : RealmObject()