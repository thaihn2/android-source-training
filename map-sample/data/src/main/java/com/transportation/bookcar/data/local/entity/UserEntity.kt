package com.transportation.bookcar.data.local.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created on 2/2/2018.
 */
open class UserEntity(
        @PrimaryKey var userId: String = "",

        var name: String = "",

        var email: String? = "",

        var type: String = "",

        var token: String = ""

) : RealmObject()
