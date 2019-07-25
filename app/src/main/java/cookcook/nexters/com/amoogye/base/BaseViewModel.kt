package cookcook.nexters.com.amoogye.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(app: Application) : AndroidViewModel(app) {
    // 이벤트 리스너 관련 변수
    var action: SingleLiveEvent<String> = SingleLiveEvent()
    var actionField: ObservableField<String> = ObservableField()

    // 컴포넌트가 클릭 상태인지 확인
    protected var clicked: Boolean = false

    // 이벤트 리스너 처리
    open fun callAction(action: String) {
        // 중복 클릭 방지
        if (!clicked) {
            freezeClick()
            this.action.value = action
            actionField.set(action)
        }
    }

    fun releaseClick() {
        clicked = false
    }

    fun freezeClick() {
        clicked = true
    }
}