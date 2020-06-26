package cn.deali.poemnote.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Poem(
    @Id var id: Long = 0,
    var title: String = "",
    var dynasty: String = "",
    var author: String = "",
    var content: String = ""
)