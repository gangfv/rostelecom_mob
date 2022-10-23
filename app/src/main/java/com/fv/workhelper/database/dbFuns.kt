package com.fv.workhelper.database

fun saveToMainList(id: String, type: String){
    var refUser = "$NODE_MAIN_LIST/$UID/$id"
    var refReceived = "$NODE_MAIN_LIST/$id/$UID"

    val mapUser = hashMapOf<String, Any>()
    val mapReceived = hashMapOf<String, Any>()

    mapUser[CHILD_ID] = id
    mapUser[CHILD_TYPE] = type

    mapReceived[CHILD_ID] = UID
    mapReceived[CHILD_TYPE] = type

    val commonMap = hashMapOf<String, Any>()
    commonMap[refUser] = mapUser
    commonMap[refReceived] = mapReceived

    REF_DATABASE_ROOT.updateChildren(commonMap)
        .addOnFailureListener{ }
}