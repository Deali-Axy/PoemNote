package cn.deali.poemnote.event

import cn.deali.poemnote.model.User

data class NewUserCreatedEvent(
    val user: User
)