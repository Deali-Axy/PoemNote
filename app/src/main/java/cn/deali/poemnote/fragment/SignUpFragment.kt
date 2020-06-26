package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.ObjectBox
import cn.deali.poemnote.R
import cn.deali.poemnote.event.MessageEvent
import cn.deali.poemnote.event.NewUserCreatedEvent
import cn.deali.poemnote.model.User
import cn.deali.poemnote.model.User_
import cn.deali.poemnote.utils.TipDialogUtils
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.topbar
import org.greenrobot.eventbus.EventBus

class SignUpFragment : QMUIFragment() {
    private lateinit var userBox: Box<User>

    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        userBox = ObjectBox.boxStore.boxFor()
        return LayoutInflater.from(context).inflate(R.layout.fragment_sign_up, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        initTopBar()

        btn_sign_in.onClick {
            if (et_username.text.isEmpty()) {
                TipDialogUtils.fail(it, "用户名不能为空！")
                return@onClick
            }
            // 写入数据库
            val username = et_username.text.toString()
            if (userBox.query { equal(User_.name, username) }.count() > 0) {
                TipDialogUtils.fail(it, "用户名已存在！")
                return@onClick
            }

            val newUser = User(name = username)
            val newId = userBox.put(newUser)
            if (newId > 0) {
                // 发送新用户创建事件
                EventBus.getDefault().post(NewUserCreatedEvent(newUser))
                // 发送 sticky 消息
                EventBus.getDefault().postSticky(MessageEvent("创建用户成功", QMUITipDialog.Builder.ICON_TYPE_SUCCESS))

                // 返回上一层
                popBackStack()
            } else {
                TipDialogUtils.fail(it, "创建新用户失败！")
            }
        }
    }

    private fun initTopBar() {
        topbar.setTitle("创建用户")
        topbar.addLeftBackImageButton().onClick { popBackStack() }
        topbar.addRightTextButton("已有用户", R.id.topbar_right_sign_in_button)
            .onClick {
                startActivity(
                    QMUIFragmentActivity.intentOf(
                        requireContext(),
                        CommonHolderActivity::class.java,
                        SignInFragment::class.java
                    )
                )
            }
    }
}