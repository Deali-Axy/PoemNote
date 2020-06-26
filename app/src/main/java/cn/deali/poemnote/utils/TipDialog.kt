package cn.deali.poemnote.utils

import android.view.View
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog

object TipDialog {
    fun show(view: View, iconType: Int, content: String, delayMillis: Long) {
        val tipDialog = QMUITipDialog.Builder(view.context)
            .setIconType(iconType)
            .setTipWord(content)
            .create()
        tipDialog.show()
        view.postDelayed({ tipDialog.dismiss() }, delayMillis)
    }

    fun success(view: View, content: String) {
        show(view, QMUITipDialog.Builder.ICON_TYPE_SUCCESS, content, 1500)
    }

    fun fail(view: View, content: String) {
        show(view, QMUITipDialog.Builder.ICON_TYPE_FAIL, content, 1500)
    }
}