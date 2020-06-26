package cn.deali.poemnote.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import org.json.JSONObject

@Entity
data class Poem(
    @Id var id: Long = 0,
    var title: String = "",
    var dynasty: String = "",
    var author: String = "",
    var content: String = ""
) {
    companion object {
        fun fromJson(jsonObject: JSONObject, dynasty: String) = Poem(
            id = 0,
            title = jsonObject.getString("title"),
            dynasty = dynasty,
            author = jsonObject.getString("author"),
            content = jsonObject.getString("content")
        )
    }
}