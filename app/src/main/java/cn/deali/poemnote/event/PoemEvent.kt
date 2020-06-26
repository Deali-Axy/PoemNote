package cn.deali.poemnote.event

import cn.deali.poemnote.model.Poem

data class PoemEvent(
    var poem: Poem
)