package cn.deali.poemnote

import android.app.Application
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager

class PoemNoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 注册返回手势
        QMUISwipeBackActivityManager.init(this)
    }
}