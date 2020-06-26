package cn.deali.poemnote.event

import cn.deali.poemnote.model.UserFavorite

data class NewFavoriteCreatedEvent(
    var userFavorite: UserFavorite
)