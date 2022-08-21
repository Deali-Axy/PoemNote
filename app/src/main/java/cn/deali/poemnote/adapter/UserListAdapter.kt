package cn.deali.poemnote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.deali.poemnote.App
import cn.deali.poemnote.R
import cn.deali.poemnote.event.NewUserCreatedEvent
import cn.deali.poemnote.event.UserLoginEvent
import cn.deali.poemnote.ext.toast
import cn.deali.poemnote.model.User
import com.qmuiteam.qmui.alpha.QMUIAlphaTextView
import com.qmuiteam.qmui.kotlin.onClick
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UserListAdapter(private var users: MutableList<User>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private lateinit var parent: ViewGroup

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // 注册事件总线
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        EventBus.getDefault().unregister(this)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    // 接收到创建新用户事件时调用
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: NewUserCreatedEvent) {
        users.add(event.user)
        // parent.context.toast("创建用户成功！")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.parent = parent
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.item_user_card, parent, false)
        return ViewHolder(root)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.itemView.findViewById<QMUIAlphaTextView>(R.id.tv_username).text = user.name
        holder.itemView.onClick {
            // 设置当前用户
            App.instance.currentUser = user
            // 发送用户登录消息
            EventBus.getDefault().postSticky(UserLoginEvent(user))
        }
    }
}