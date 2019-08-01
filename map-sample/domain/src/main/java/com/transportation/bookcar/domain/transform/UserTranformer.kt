package com.transportation.bookcar.domain.transform

import com.transportation.bookcar.data.local.entity.UserEntity
import com.transportation.bookcar.domain.pojo.User

/**
 * Created on 5/16/2019.
 */
fun User.toEntity() = UserEntity(userId, name, email, type, token)

fun UserEntity.toUser() = User(email, userId, name, token, type)
