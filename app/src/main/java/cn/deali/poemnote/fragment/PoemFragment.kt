package cn.deali.poemnote.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import cn.deali.poemnote.R
import cn.deali.poemnote.event.PoemEvent
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.kotlin.onClick
import kotlinx.android.synthetic.main.fragment_poem.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PoemFragment : QMUIFragment() {
    override fun onCreateView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_poem, null)
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        topbar.setTitle("诗词阅读")
        topbar.addLeftBackImageButton().onClick { popBackStack() }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    /**
     * 接收事件 显示诗词内容
     */
    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(poemEvent: PoemEvent) {
        val poem = poemEvent.poem
        tv_title.text = poem.title
        tv_author.text = "${poem.dynasty} / ${poem.author}"

        val sentences = poem.content.split(",")
        tv_content.text = sentences.joinToString("\n")

        // 删除事件广播
        EventBus.getDefault().cancelEventDelivery(poemEvent)
    }
}