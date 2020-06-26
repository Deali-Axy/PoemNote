package cn.deali.poemnote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.deali.poemnote.R

class PoemNoteListAdapter : RecyclerView.Adapter<PoemNoteListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View, adapter: PoemNoteListAdapter) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.item_poemnote_card, parent, false)
        return ViewHolder(root, this)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}