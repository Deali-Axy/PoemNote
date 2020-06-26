package cn.deali.poemnote.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import org.json.JSONObject
import java.time.LocalDate

@Entity
data class Hitokoto(
    @Id var id: Long,
    var content: String,
    var type: String,
    var from_where: String,
    var creator: String
) {
    companion object {
        fun fromJson(jsonObj: JSONObject): Hitokoto = Hitokoto(
            id = 0,
            content = jsonObj.getString("hitokoto"),
            type = jsonObj.getString("type"),
            from_where = jsonObj.getString("from_where"),
            creator = jsonObj.getString("creator")
        )
    }
}