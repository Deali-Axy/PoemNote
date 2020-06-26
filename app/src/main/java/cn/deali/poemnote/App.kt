package cn.deali.poemnote

import android.app.Application
import cn.deali.poemnote.model.Hitokoto
import cn.deali.poemnote.model.Poem
import cn.deali.poemnote.model.User
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.properties.Delegates

class App : Application() {
    companion object {
        var instance: App by Delegates.notNull()
    }

    var currentUser: User? = null
    lateinit var hitokotoBox: Box<Hitokoto>
    lateinit var poemBox: Box<Poem>

    override fun onCreate() {
        super.onCreate()

        instance = this

        // 注册返回手势
        QMUISwipeBackActivityManager.init(this)

        // 初始化数据库
        ObjectBox.init(this)

        // 初始化一言数据
        initHitokoto()

        // 初始化诗词数据
        initPoem()
    }

    /**
     * 一言数据库初始化
     */
    private fun initHitokoto() {
        hitokotoBox = ObjectBox.boxStore.boxFor()

        // 如果已存在一言数据则跳过数据导入
        if (hitokotoBox.count() > 0) return

        // 将资源文件里的一言数据导入数据库
        try {
            val input = applicationContext.assets.open("hitokoto.json")
            val bufferReader = BufferedReader(InputStreamReader(input))
            val jsonArray = JSONArray(bufferReader.readText())
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                hitokotoBox.put(Hitokoto.fromJson(jsonObj))
            }
        } catch (ex: IOException) {

        }
    }

    /**
     * 诗词数据初始化
     */
    private fun initPoem() {
        poemBox = ObjectBox.boxStore.boxFor()

        // 如果已存在诗词数据则跳过数据导入
        if (poemBox.count() > 0) return

        // 将资源文件里的诗词数据导入数据库
        try {
            val input = applicationContext.assets.open("poem.json")
            val bufferReader = BufferedReader(InputStreamReader(input))
            val jsonArray = JSONArray(bufferReader.readText())
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                poemBox.put(Poem.fromJson(jsonObj, "唐"))
            }
        } catch (ex: IOException) {

        }
    }
}