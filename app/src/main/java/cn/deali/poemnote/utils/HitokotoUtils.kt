package cn.deali.poemnote.utils

import cn.deali.poemnote.App
import cn.deali.poemnote.model.Hitokoto

object HitokotoUtils {
    val random: Hitokoto
        get() {
            val items = App.instance.hitokotoBox.all
            // shuffled()的函数可以对集合进行洗牌（类似于扑克牌的洗牌），每次可以获取到不同的数据
            // 我只需要一条，所以取第一条
            return items.shuffled().first()
        }
}