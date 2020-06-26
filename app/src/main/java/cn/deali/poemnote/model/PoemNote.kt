package cn.deali.poemnote.model

import android.os.Build
import androidx.annotation.RequiresApi
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.time.LocalDateTime

//@Entity
data class PoemNote(
    @Id var id: Long = 0,
    var user: User? = null,
    var poem: Poem? = null,
    var content: String = "",
    var createdAt: LocalDateTime = LocalDateTime.now()
)