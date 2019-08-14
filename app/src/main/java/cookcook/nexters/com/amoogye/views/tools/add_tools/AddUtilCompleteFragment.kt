package cookcook.nexters.com.amoogye.views.tools.add_tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.views.tools.ToolsFragment


class AddUtilCompleteFragment : Fragment() {

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
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_addutil_3_complete, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}