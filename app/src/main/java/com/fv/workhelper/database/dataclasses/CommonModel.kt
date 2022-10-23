package com.fv.workhelper.database.dataclasses

data class CommonModel(
    val id: String = "",
    var fio: String = "",
    var email: String = "",
    var numberPhone: String = "",
    var photoUrl: String = "empty",

    var text: String = "",
    var type: String = "",
    var from: String = "",
    var timeStamp: Any = "",
    var lastMessage:String = ""
){
    override fun equals(other: Any?): Boolean {
        return (other as CommonModel).id == id
    }
}