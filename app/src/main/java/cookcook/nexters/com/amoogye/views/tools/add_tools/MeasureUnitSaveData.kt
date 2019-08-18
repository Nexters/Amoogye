package cookcook.nexters.com.amoogye.views.tools.add_tools

import cookcook.nexters.com.amoogye.views.calc.presenter.CalcFragment

class MeasureUnitSaveData {

    /* 헌진이의 기운이 남아있습니다. */
    var unitId: Long = 0
    var unitType: Int = 0
    var unitStatus: Int = 0
    var unitNameBold: String = ""
    var unitNameSoft: String = ""
    var unit: String = ""
    var unitValue: Int = 0

    var unitStandardId: Long = 0

    /* 이게 진짜 */
    var currentTool: String = ""
    var currentInteger: String = ""
    var currentDecimalPoint: String = ""

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: MeasureUnitSaveData? = null

        fun getInstance(): MeasureUnitSaveData {
            if (INSTANCE == null) {
                INSTANCE =
                    MeasureUnitSaveData()
            }
            return INSTANCE!!
        }

        fun init() {
            INSTANCE = null
        }
    }
}