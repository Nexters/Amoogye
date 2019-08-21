package cookcook.nexters.com.amoogye.views.tools.add_tools

import android.icu.util.Measure
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.MeasureUnit
import cookcook.nexters.com.amoogye.views.tools.ToolsFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_addutil_3_complete.*
import kotlin.text.Typography.times


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
    }
}