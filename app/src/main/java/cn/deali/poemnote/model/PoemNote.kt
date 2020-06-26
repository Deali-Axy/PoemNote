package cn.deali.poemnote.model

import android.os.Build
import androidx.annotation.RequiresApi
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import java.time.LocalDateTime
import java.util.*

@Entity
data class PoemNote(
    @Id var id: Long = 0,
    var content: String = "",
    var createdAt: Date? = Date()
) {
    lateinit var user: ToOne<User>
    lateinit var poem: ToOne<Poem>
}