package cookcook.nexters.com.amoogye.utils

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


fun addFragmentToActivity(@NonNull fragmentManager: FragmentManager, @NonNull fragment: Fragment, frameId: Int) {
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(frameId, fragment)
    transaction.commit()
}

fun addFragmentToActivity(@NonNull fragmentManager: FragmentManager, @NonNull fragment: Fragment, tag: String) {
    val transaction = fragmentManager.beginTransaction()
    transaction.add(fragment, tag)
    transaction.commit()
}