package cookcook.nexters.com.amoogye.base.wheel_picker

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

class ScreenUtils {
    companion object {
        fun getScreenHeight(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }

        fun dpToPx(context: Context, value: Int) : Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics).toInt()
        }

        fun pxToDp(context: Context, value: Int) : Int {
            return value / ((context.resources.displayMetrics.densityDpi/DisplayMetrics.DENSITY_DEFAULT))
        }
    }
}