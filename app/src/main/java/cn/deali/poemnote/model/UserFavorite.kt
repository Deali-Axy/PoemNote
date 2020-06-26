package cn.deali.poemnote.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.time.LocalDateTime


//@Entity
data class UserFavorite(
    @Id var id: Long = 0,
    var user: User? = null,
    var poem: Poem? = null,
    var createdAt: LocalDateTime = LocalDateTime.now()
)