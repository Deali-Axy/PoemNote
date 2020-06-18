package cn.deali.poemnote

import android.os.Bundle
import cn.deali.poemnote.fragment.HomeFragment
import com.qmuiteam.qmui.arch.QMUIActivity
import com.qmuiteam.qmui.arch.QMUIFragmentActivity

class LauncherActivity : QMUIActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 跳转到 Home
        val intent = QMUIFragmentActivity.intentOf(
            this,
            CommonHolderActivity::class.java,
            HomeFragment::class.java
        )
        startActivity(intent)

        // 关闭
        finish()
    }
}