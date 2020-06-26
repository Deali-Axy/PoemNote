package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import cn.deali.poemnote.App
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.R
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_user, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        topbar.setTitle("用户中心")

        initUserInfo()

        linear_layout_user.onClick {

            if (App.instance.currentUser != null) {
                val tipDialog = QMUITipDialog.Builder(requireContext())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                    .setTipWord("当前登录用户：${App.instance.currentUser?.name}")
                    .create()
                tipDialog.show()
                it.postDelayed({ tipDialog.dismiss() }, 1500)

                reenterTransition
            }
            startActivity(
                QMUIFragmentActivity.intentOf(
                    requireContext(),
                    CommonHolderActivity::class.java,
                    SignInFragment::class.java
                )
            )
        }
    }

    fun initUserInfo() {
        if (App.instance.currentUser == null) {
            linear_layout_logout.isVisible = false
            tv_username.text = "未登录"
        }
    }
}