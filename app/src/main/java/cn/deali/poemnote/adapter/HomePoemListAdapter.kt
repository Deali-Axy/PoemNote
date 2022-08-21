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

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.deali.poemnote.App
import cn.deali.poemnote.CommonHolderActivity
import cn.deali.poemnote.ObjectBox
import cn.deali.poemnote.R
import cn.deali.poemnote.event.NewFavoriteCreatedEvent
import cn.deali.poemnote.event.NewNoteCreatedEvent
import cn.deali.poemnote.event.PoemEvent
import cn.deali.poemnote.fragment.PoemFragment
import cn.deali.poemnote.model.Poem
import cn.deali.poemnote.model.PoemNote
import cn.deali.poemnote.model.UserFavorite
import cn.deali.poemnote.model.UserFavorite_
import cn.deali.poemnote.utils.TipDialogUtils
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.view.IconicsImageButton
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.squareup.picasso.Picasso
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import org.greenrobot.eventbus.EventBus

class HomePoemListAdapter : RecyclerView.Adapter<HomePoemListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // 获取随机10首诗词
    private var poems: List<Poem> = App.instance.poemBox.all.shuffled().take(10)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val root = inflater.inflate(R.layout.item_poem_card_large, viewGroup, false)

        return ViewHolder(root)
    }

    override fun getItemCount(): Int = poems.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val root = holder.itemView

        val ivCard = root.findViewById<QMUIRadiusImageView>(R.id.iv_card)
        val tvTitle = root.findViewById<TextView>(R.id.tv_title)
        val tvAuthor = root.findViewById<TextView>(R.id.tv_author)
        val tvContent = root.findViewById<TextView>(R.id.tv_content)
        val poem = poems[position]

        // 下载随机图片
        Picasso.get().load("http://www.sblt.deali.cn:15002/Api/PicLib/Random/300/350?name=${poem.title}")
            .placeholder(R.mipmap.example_image5).into(ivCard)

        // 显示诗词的内容
        tvTitle.text = poem.title
        tvAuthor.text = "${poem.dynasty} / ${poem.author}"
        val sentences = poem.content.split(",")
        if (sentences.isEmpty()) return
        if (sentences.size < 2) {
            tvContent.text = "${sentences.first()}。"
        } else {
            tvContent.text = "${sentences[0]}，${sentences[1]}。"
        }

        val btnFavorite: IconicsImageButton = root.findViewById(R.id.btn_favorite)
        val btnNote: IconicsImageButton = root.findViewById(R.id.btn_note)
        val btnDetail: IconicsImageButton = root.findViewById(R.id.btn_detail)

        // 添加收藏
        btnFavorite.onClick {
            if (App.instance.currentUser == null) {
                TipDialogUtils.fail(it, "请先登录！")
                return@onClick
            }

            val favoriteBox: Box<UserFavorite> = ObjectBox.boxStore.boxFor()
            // 是否已经存在收藏
            val query = favoriteBox.query {
                equal(UserFavorite_.userId, App.instance.currentUser!!.id)
                equal(UserFavorite_.poemId, poem.id)
            }
            if (query.count() > 0) {
                // 删除收藏
                favoriteBox.remove(query.findFirst()!!)
                TipDialogUtils.success(it, "取消喜欢")
                btnFavorite.icon = IconicsDrawable(root.context, GoogleMaterial.Icon.gmd_favorite_border)
                return@onClick
            }
            val userFavorite = UserFavorite()
            userFavorite.user.target = App.instance.currentUser
            userFavorite.poem.target = poem
            if (favoriteBox.put(userFavorite) > 0) {
                TipDialogUtils.success(it, "已添加到我喜欢")
                btnFavorite.icon = IconicsDrawable(root.context, GoogleMaterial.Icon.gmd_favorite).apply {
                    colorInt(Color.RED)
                }
                EventBus.getDefault().post(NewFavoriteCreatedEvent(userFavorite))
            } else {
                TipDialogUtils.fail(it, "添加喜欢失败！")
            }
        }

        // 添加笔记
        btnNote.onClick {
            if (App.instance.currentUser == null) {
                TipDialogUtils.fail(it, "请先登录！")
                return@onClick
            }

            val builder = QMUIDialog.EditTextDialogBuilder(root.context)
            builder
                .setTitle("测试")
                .setPlaceholder("请输入笔记内容")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消") { dialog, _ -> dialog.dismiss() }
                .addAction("确定") { dialog, _ ->
                    val note = PoemNote(content = builder.editText.text.toString())
                    note.user.target = App.instance.currentUser
                    note.poem.target = poem

                    // 写入数据库
                    val noteBox: Box<PoemNote> = ObjectBox.boxStore.boxFor()
                    if (noteBox.put(note) > 0) {
                        TipDialogUtils.success(btnNote, "添加笔记成功")
                        EventBus.getDefault().post(NewNoteCreatedEvent(note))
                    } else {
                        TipDialogUtils.fail(btnNote, "添加笔记失败！")
                    }

                    dialog.dismiss()
                }
                .create().show()
        }

        // 查看详情
        btnDetail.onClick {
            EventBus.getDefault().postSticky(PoemEvent(poem))
            root.context.startActivity(
                QMUIFragmentActivity.intentOf(
                    root.context,
                    CommonHolderActivity::class.java,
                    PoemFragment::class.java
                )
            )
        }
    }
}