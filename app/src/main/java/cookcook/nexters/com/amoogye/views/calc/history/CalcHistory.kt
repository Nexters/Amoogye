package cookcook.nexters.com.amoogye.views.calc.history

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CalcHistory(
    @PrimaryKey var historyId: Long = 0,
    var createDate: Long = 0,
    var calcResultBefore: String = "",
    var calcResultAfter: String = ""
) : RealmObject()