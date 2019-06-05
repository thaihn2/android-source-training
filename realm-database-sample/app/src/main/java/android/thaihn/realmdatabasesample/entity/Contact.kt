package android.thaihn.realmdatabasesample.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Contact(

        @PrimaryKey var id: Long = 0,

        var name: String = "",

        var phone: String = "",

        var address: String = ""

) : RealmObject() {
}