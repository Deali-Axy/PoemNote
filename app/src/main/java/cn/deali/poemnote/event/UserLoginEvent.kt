package cn.deali.poemnote.event

import cn.deali.poemnote.model.User

data class UserLoginEvent(
    var user: User
)