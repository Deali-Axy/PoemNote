package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import cn.deali.poemnote.App
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.R
import cn.deali.poemnote.event.UserLoginEvent
import cn.deali.poemnote.utils.HitokotoUtils
import cn.deali.poemnote.utils.TipDialogUtils
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.fragment_user.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UserFragment : QMUIFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_user, null)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: UserLoginEvent) {
        initUserInfo()
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

                return@onClick
            }
            startActivity(
                QMUIFragmentActivity.intentOf(
                    requireContext(),
                    CommonHolderActivity::class.java,
                    SignInFragment::class.java
                )
            )
        }

        // 我喜欢的
        linear_layout_favorite.onClick {
            if (App.instance.currentUser == null) {
                TipDialogUtils.fail(it, "请先登录！")
                return@onClick
            }
            startActivity(
                QMUIFragmentActivity.intentOf(
                    requireContext(),
                    CommonHolderActivity::class.java,
                    UserFavoriteFragment::class.java
                )
            )
        }

        // 我的笔记
        linear_layout_my_note.onClick {
            if (App.instance.currentUser == null) {
                TipDialogUtils.fail(it, "请先登录！")
                return@onClick
            }
            startActivity(
                QMUIFragmentActivity.intentOf(
                    requireContext(),
                    CommonHolderActivity::class.java,
                    UserNoteFragment::class.java
                )
            )
        }

        // 注销
        linear_layout_logout.onClick {
            App.instance.currentUser = null
            initUserInfo()
            TipDialogUtils.success(it, "已退出登录")
        }
    }

    /**
     * 初始化用户信息
     */
    fun initUserInfo() {
        if (App.instance.currentUser == null) {
            linear_layout_logout.isVisible = false
            tv_username.text = "未登录"
            tv_hitokoto.text = "点击此处选择用户登录"
        } else {
            linear_layout_logout.isVisible = true
            tv_username.text = App.instance.currentUser?.name
            tv_hitokoto.text = HitokotoUtils.random.content
        }
    }
}