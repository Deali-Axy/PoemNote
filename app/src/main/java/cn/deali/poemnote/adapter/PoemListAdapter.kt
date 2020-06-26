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
import cn.deali.poemnote.R
import cn.deali.poemnote.event.PoemEvent
import cn.deali.poemnote.fragment.PoemFragment
import cn.deali.poemnote.model.Poem
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import org.greenrobot.eventbus.EventBus

class PoemListAdapter : RecyclerView.Adapter<PoemListAdapter.ViewHolder> {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val poems: List<Poem>

    constructor() {
        poems = App.instance.poemBox.all
    }

    constructor(poems: List<Poem>) {
        this.poems = poems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.item_poem_card, parent, false)
        return ViewHolder(root)
    }

    override fun getItemCount(): Int {
        return poems.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardPoem = holder.itemView.findViewById<CardView>(R.id.card_poem)
        val tvTitle = holder.itemView.findViewById<TextView>(R.id.tv_title)
        val tvAuthor = holder.itemView.findViewById<TextView>(R.id.tv_author)
        val tvContent = holder.itemView.findViewById<TextView>(R.id.tv_content)

        // 设置诗词内容
        val poem = poems[position]
        tvTitle.text = poem.title
        tvAuthor.text = "${poem.dynasty} / ${poem.author}"
        val sentences = poem.content.split(",")
        if (sentences.isEmpty()) return
        if (sentences.size < 2) {
            tvContent.text = "${sentences.first()}。"
        } else {
            tvContent.text = "${sentences[0]}，${sentences[1]}。"
        }

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