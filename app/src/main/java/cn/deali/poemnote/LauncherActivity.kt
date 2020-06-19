package cn.deali.poemnote

import android.os.Bundle
import cn.deali.poemnote.fragment.MainFragment
import com.qmuiteam.qmui.arch.QMUIActivity
import com.qmuiteam.qmui.arch.QMUIFragmentActivity

class LauncherActivity : QMUIActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 跳转到 Home
        val intent = QMUIFragmentActivity.intentOf(
            this,
            CommonHolderActivity::class.java,
            MainFragment::class.java
        )
        startActivity(intent)

        // 关闭
        finish()
    }
}