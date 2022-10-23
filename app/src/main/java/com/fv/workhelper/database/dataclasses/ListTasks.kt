package com.fv.workhelper.database.dataclasses

data class ListTasks(
    var name: String = "",
    var description: String = "",
    var author: String = "",
    var time: String = "",
    var id: String = "",
    var executor: String = "",
){
    override fun equals(other: Any?): Boolean {
        return (other as ListTasks).id == id
    }
}