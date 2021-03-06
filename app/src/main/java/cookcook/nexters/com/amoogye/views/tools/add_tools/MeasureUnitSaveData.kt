package cookcook.nexters.com.amoogye.views.tools.add_tools

class MeasureUnitSaveData {

    /* 헌진이의 기운이 남아있습니다. */
    var unitNameBold: String = ""
    var unitNameSoft: String = ""
    var unitValue: Int = 0


    /* 이게 진짜 */
    var currentTool: String = ""
    var currentInteger: String = ""
    var currentDecimalPoint: String = ""

    var newItemId: Long = -1

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