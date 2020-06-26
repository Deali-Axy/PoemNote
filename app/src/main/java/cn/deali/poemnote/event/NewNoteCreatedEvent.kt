package cn.deali.poemnote.event

import cn.deali.poemnote.model.PoemNote

data class NewNoteCreatedEvent(
    var poemNote: PoemNote
)