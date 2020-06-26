package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.ObjectBox
import cn.deali.poemnote.R
import cn.deali.poemnote.adapter.UserListAdapter
import cn.deali.poemnote.event.MessageEvent
import cn.deali.poemnote.event.NewUserCreatedEvent
import cn.deali.poemnote.ext.toast
import cn.deali.poemnote.model.User
import cn.deali.poemnote.utils.TipDialog
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