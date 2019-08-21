package cookcook.nexters.com.amoogye.views.tools


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MeasureUnit(
    @PrimaryKey var unitId: Long = 0,
    var unitType: Int = TYPE_NORMAL,
    var unitStatus: Int = 0,
    var unitNameBold: String = "",
    var unitNameSoft: String = "",
    var unit: String = "",
    var unitValue: Int = 0,
    var recentlyUseData: Long = 0,
    var createData: Long = 0
) : RealmObject()