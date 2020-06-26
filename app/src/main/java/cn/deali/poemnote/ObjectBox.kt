package cn.deali.poemnote

import android.content.Context
import cn.deali.poemnote.model.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}