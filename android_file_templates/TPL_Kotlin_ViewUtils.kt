package ${PACKAGE_NAME}

import android.os.SystemClock
import android.view.View


class ViewUtils {

    companion object {
        private val MIN_PAUSE_MS: Long = 1000
        private var mLastClickTime: Long = 0

        /**
         * Avoids multiple fast clicks.
         * @param view
         * @param onClickListener
         */
        fun onSafeClick(view: View, onClickListener: View.OnClickListener) {
            view.setOnClickListener(View.OnClickListener { view ->
                if (!isSafeClick()) return@OnClickListener
                onClickListener.onClick(view)
            })
        }

        private fun isSafeClick(): Boolean {
            val isSafeClick = SystemClock.elapsedRealtime() - mLastClickTime > MIN_PAUSE_MS
            mLastClickTime = SystemClock.elapsedRealtime()
            return isSafeClick
        }
    }
}