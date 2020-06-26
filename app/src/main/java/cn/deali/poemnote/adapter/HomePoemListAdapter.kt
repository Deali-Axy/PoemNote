/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.deali.poemnote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.R
import cn.deali.poemnote.fragment.PoemFragment
import com.mikepenz.iconics.view.IconicsImageButton
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import java.util.*

/**
 * Demo 中通用的 RecyclerView Adapter。
 * Created by sm on 2015/5/3.
 */
class HomePoemListAdapter :
    RecyclerView.Adapter<HomePoemListAdapter.ViewHolder>() {
    private val mItems: MutableList<Data>
    private var mOnItemClickListener: AdapterView.OnItemClickListener? = null
    fun addItem(position: Int) {
        if (position > mItems.size) return
        mItems.add(position, Data(position.toString()))
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        if (position >= mItems.size) return
        mItems.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val root = inflater.inflate(R.layout.item_poem_card_large, viewGroup, false)
        val btnFavorite: IconicsImageButton = root.findViewById(R.id.btn_favorite)
        val btnNote: IconicsImageButton = root.findViewById(R.id.btn_note)
        val btnDetail: IconicsImageButton = root.findViewById(R.id.btn_detail)

        btnNote.onClick {
            QMUIDialog.MessageDialogBuilder(root.context)
                .setTitle("测试")
                .setMessage("测试信息")
                .addAction("取消") { dialog, index -> dialog.dismiss() }
                .create().show()
        }

        btnDetail.onClick {
            root.context.startActivity(
                QMUIFragmentActivity.intentOf(
                    root.context,
                    CommonHolderActivity::class.java,
                    PoemFragment::class.java
                )
            )
        }

        return ViewHolder(root, this)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val data = mItems[i]
        viewHolder.setText(data.text)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun setItemCount(count: Int) {
        mItems.clear()
        mItems.addAll(generateDatas(count))
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    private fun onItemHolderClick(itemHolder: RecyclerView.ViewHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener!!.onItemClick(
                null, itemHolder.itemView,
                itemHolder.adapterPosition, itemHolder.itemId
            )
        }
    }

    class Data(var text: String)

    class ViewHolder(itemView: View, adapter: HomePoemListAdapter) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
//        private val mTextView: TextView
        private val mAdapter: HomePoemListAdapter
        fun setText(text: String?) {
//            mTextView.text = text
        }

        override fun onClick(v: View) {
            mAdapter.onItemHolderClick(this)
        }

        init {
            itemView.setOnClickListener(this)
            mAdapter = adapter
//            mTextView = itemView.findViewById<View>(R.id.textView) as TextView
        }
    }

    companion object {
        fun generateDatas(count: Int): List<Data> {
            val mDatas =
                ArrayList<Data>()
            for (i in 0 until count) {
                mDatas.add(Data(i.toString()))
            }
            return mDatas
        }
    }

    init {
        mItems = ArrayList()
    }
}