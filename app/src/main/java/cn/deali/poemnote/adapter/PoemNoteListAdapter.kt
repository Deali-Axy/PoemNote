package cn.deali.poemnote.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import cn.deali.poemnote.App
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.ObjectBox
import cn.deali.poemnote.R
import cn.deali.poemnote.event.NewNoteCreatedEvent
import cn.deali.poemnote.event.PoemEvent
import cn.deali.poemnote.fragment.PoemFragment
import cn.deali.poemnote.model.PoemNote
import cn.deali.poemnote.model.PoemNote_
import cn.deali.poemnote.model.User
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PoemNoteListAdapter : RecyclerView.Adapter<PoemNoteListAdapter.ViewHolder> {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // 从数据库读取笔记数据
    var noteBox: Box<PoemNote> = ObjectBox.boxStore.boxFor()
    lateinit var notes: MutableList<PoemNote>

    constructor() {
        notes = noteBox.query { orderDesc(PoemNote_.createdAt) }.find()
    }

    constructor(user: User) {
        notes = noteBox.query { equal(PoemNote_.userId, user.id) }.find()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.item_poemnote_card, parent, false)
        return ViewHolder(root)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // 注册事件总线
        EventBus.getDefault().register(this)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        EventBus.getDefault().unregister(this)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    // 监听新笔记创建事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: NewNoteCreatedEvent) {
        // 插入到最前面
//        notes.add(0, event.poemNote)
        notes.add(event.poemNote)
        EventBus.getDefault().cancelEventDelivery(event)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val root = holder.itemView
        val note = notes[position]

        // 找到元素
        val cardPoem = root.findViewById<CardView>(R.id.card_poem)
        val tvUsername = root.findViewById<TextView>(R.id.tv_username)
        val tvContent = root.findViewById<TextView>(R.id.tv_content)
        val tvPoemSentence = root.findViewById<TextView>(R.id.tv_poem_sentence)
        val tvPoemInfo = root.findViewById<TextView>(R.id.tv_poem_info)

        // 设置用户名和笔记内容
        tvUsername.text = note.user.target.name
        tvContent.text = note.content

        // 设置诗句
        val poem = note.poem.target
        val sentences = poem.content.split(",")
        if (sentences.isEmpty()) return
        if (sentences.size < 2) {
            tvPoemSentence.text = "${sentences.first()}。"
        } else {
            tvPoemSentence.text = "${sentences[0]}，${sentences[1]}。"
        }

        // 设置诗词信息
        tvPoemInfo.text = "—— 《${poem.title}》 ${poem.dynasty} / ${poem.author}"

        // 设置点击事件
        cardPoem.onClick {
            EventBus.getDefault().postSticky(PoemEvent(poem))
            holder.itemView.context.startActivity(
                QMUIFragmentActivity.intentOf(
                    holder.itemView.context,
                    CommonHolderActivity::class.java,
                    PoemFragment::class.java
                )
            )
        }
    }
}