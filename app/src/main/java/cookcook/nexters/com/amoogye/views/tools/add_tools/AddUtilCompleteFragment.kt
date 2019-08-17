package cookcook.nexters.com.amoogye.views.tools.add_tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.ToolsFragment


class AddUtilCompleteFragment : Fragment() {

    interface OnAddUtilResultListener {
        fun onAddUtilResult()
    }


    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: AddUtilCompleteFragment? = null

        fun getInstance(): AddUtilCompleteFragment {
            if (INSTANCE == null) {
                INSTANCE =
                    AddUtilCompleteFragment()
            }
            return INSTANCE!!
        }

        fun instanceInit() {
            INSTANCE = null
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_addutil_3_complete, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
        사용자 입력 데이터 가져오기





        realm.beginTransition()





       // 저장하기
        private fun saveData(nameBold: String, nameSoft: String) {
        realm.beginTransaction()


        // 기준 데이터 가져오기
        val getStandardData() = realm.where<>().equalTo("unitId", id)

        val saveData = realm.createObject(MeasureUnit::class.java, newId())
        saveData.unitStatus = 0
        saveData.unitType = 1
        saveData.unit = 기준 데이터(unit)
        saveData.unitNameBold = 건네받은 이름 데이터
        saveData.unitValue = 기준 데이터(unitValue * 비율)
        saveData.unitNameSoft = unitValue + unit

        realm.commitTransaction()
    }

    private fun newId(): Long {
        val maxId = realm.where(MeasureUnit::class.java).max("unitId")
        if (maxId != null) {
            return maxId.toLong() + 1
        }
        return 0
    }

        // realm.commitTransition()
        *
        * */


    }

}