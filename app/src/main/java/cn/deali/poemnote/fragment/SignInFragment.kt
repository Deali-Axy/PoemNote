package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.ObjectBox
import cn.deali.poemnote.R
import cn.deali.poemnote.adapter.UserListAdapter
import cn.deali.poemnote.event.MessageEvent
import cn.deali.poemnote.event.UserLoginEvent
import cn.deali.poemnote.model.User
import cn.deali.poemnote.utils.TipDialogUtils
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SignInFragment : QMUIFragment() {
    private lateinit var userBox: Box<User>
    private lateinit var rootView: View

    @SuppressLint("InflateParams")
    override fun onCreateView(): View {
        userBox = ObjectBox.boxStore.boxFor()
        rootView = LayoutInflater.from(context).inflate(R.layout.fragment_sign_in, null)
        return rootView
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    // 接收文字消息事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: MessageEvent) {
        println("MessageEvent，信息：${event.content}")
        TipDialogUtils.show(rootView, event.iconType, event.content, 1500)

        // 2020-6-27：todo 这里应该释放事件的，但是一释放就会出错，我顶不住了
        // 2022-8-21：ThreadMode 必须指定为 POSTING，才能释放事件。（不指定的话，默认值就是 POSTING）
        // EventBus.getDefault().cancelEventDelivery(event)
        // 2022-8-21 21:57:53 虽然但是，并不需要调用 cancelEventDelivery，直接把这个事件删除即可，搞定！
        EventBus.getDefault().removeStickyEvent(event)
    }

    // 接收用户登录事件
    @Subscribe(sticky = true)
    fun onEvent(event: UserLoginEvent) {
        // 返回上一级
        popBackStack()
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        initTopBar()

        val listLayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        rv_user_list.layoutManager = listLayoutManager
        rv_user_list.adapter = UserListAdapter(userBox.all)
    }

    private fun initTopBar() {
        topbar.setTitle("选择用户登录")
        topbar.addLeftBackImageButton().onClick { popBackStack() }
        topbar.addRightTextButton("创建用户", R.id.topbar_right_sign_up_button)
            .onClick {
                startActivity(
                    QMUIFragmentActivity.intentOf(
                        requireContext(),
                        CommonHolderActivity::class.java,
                        SignUpFragment::class.java
                    )
                )
            }
    }
}