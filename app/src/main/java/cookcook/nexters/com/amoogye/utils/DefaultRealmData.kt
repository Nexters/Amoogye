package cookcook.nexters.com.amoogye.utils

import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import cookcook.nexters.com.amoogye.views.tools.TYPE_LIFE
import cookcook.nexters.com.amoogye.views.tools.TYPE_NORMAL
import java.util.*

// 단위는 ML로 계산한다.
val realmData = arrayOf(
    MeasureUnit(1, TYPE_NORMAL, 0, "g", "그램", "", 1, Date().time, Date().time),
    MeasureUnit(2, TYPE_NORMAL, 0, "kg", "킬로그램", "", 1, Date().time, Date().time),
    MeasureUnit(3, TYPE_NORMAL, 0, "oz", "온즈", "", 1, Date().time, Date().time),
    MeasureUnit(4, TYPE_NORMAL, 0, "CC", "씨씨", "", 1, Date().time, Date().time),
    MeasureUnit(5, TYPE_NORMAL, 0, "ml", "밀리리터", "", 1, Date().time, Date().time),
    MeasureUnit(6, TYPE_NORMAL, 0, "L", "리터", "", 1, Date().time, Date().time),
    MeasureUnit(7, TYPE_NORMAL, 0, "Tbsp", "테이블 스푼", "", 1, Date().time, Date().time),
    MeasureUnit(8, TYPE_NORMAL, 0, "Tsp", "티스푼", "", 0, Date().time, Date().time),
    MeasureUnit(9, TYPE_NORMAL, 0, "pt", "파인트", "", 0, Date().time, Date().time),
    MeasureUnit(10, TYPE_LIFE, 0, "밥숟가락", "", "", 10, Date().time, Date().time),
    MeasureUnit(11, TYPE_LIFE, 0, "베라스푼", "", "", 5, Date().time, Date().time),
    MeasureUnit(12, TYPE_LIFE, 0, "종이컵", "", "", 150, Date().time, Date().time),
    MeasureUnit(13, TYPE_LIFE, 0, "병뚜껑", "", "", 5, Date().time, Date().time),
    MeasureUnit(14, TYPE_LIFE, 0, "김용기", "", "", 30, Date().time, Date().time),
    MeasureUnit(15, TYPE_LIFE, 0, "소주잔", "", "", 50, Date().time, Date().time),
    MeasureUnit(16, TYPE_LIFE, 0, "참치캔", "", "", 100, Date().time, Date().time),
    MeasureUnit(17, TYPE_NORMAL, 0, "햇반그릇", "", "", 200, Date().time, Date().time),
    MeasureUnit(18, TYPE_NORMAL, 0, "내냄비", "", "", 400, Date().time, Date().time),
    MeasureUnit(19, TYPE_NORMAL, 0, "맥주잔", "", "", 150, Date().time, Date().time),
    MeasureUnit(20, TYPE_NORMAL, 0, "내주먹", "", "", 30, Date().time, Date().time)
)