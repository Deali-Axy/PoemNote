package cn.deali.poemnote

import android.app.Application
import cn.deali.poemnote.model.User
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import kotlin.properties.Delegates

class App : Application() {
    var currentUser: User? = null

    override fun onCreate() {
        super.onCreate()

        instance = this

        // 注册返回手势
        QMUISwipeBackActivityManager.init(this)

        // 初始化数据库
        ObjectBox.init(this)
    }

    companion object {
        var instance: App by Delegates.notNull()
    }
}