package com.fv.workhelper.database


import com.fv.workhelper.database.dataclasses.Note
import com.fv.workhelper.database.dataclasses.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

lateinit var AUTH: FirebaseAuth
lateinit var UID:String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User
lateinit var NOTE: Note

const val TYPE_TEXT = "text"
const val NODE_USERS = "users"
const val NODE_TASKS = "tasks"
const val NODE_MESSAGES = "messages"
const val NODE_SHABLONS = "shablons"

const val CHILD_ID = "id"
const val CHILD_FIO = "fio"
const val CHILD_EMAIL = "email"
const val CHILD_NUMBERPHONE = "numberPhone"
const val CHILD_NAME = "name"
const val CHILD_DESC = "description"
const val CHILD_AUTHOR = "author"
const val TIME = "time"
const val CHILD_EXECUTOR = "executor"
const val CHILD_TEXT = "text"
const val CHILD_TYPE = "type"
const val CHILD_FROM = "from"
const val CHILD_TIMESTAMP = "timeStamp"

const val TYPE_CHAT = "chat"
const val NODE_MAIN_LIST = "main_list"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = Firebase.database.reference
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}

fun DataSnapshot.getUserModel(): User =
    this.getValue(User::class.java) ?: User()

fun sendMessage(message: String, receivingUserID: String, typeText: String, function: () -> Unit) {
    val refDialogUser = "$NODE_MESSAGES/$UID/$receivingUserID"
    val refDialogReceivingUser = "$NODE_MESSAGES/$receivingUserID/$UID"
    val messageKey = REF_DATABASE_ROOT.child(refDialogUser).push().key

    val mapMessage = hashMapOf<String, Any>()
    mapMessage[CHILD_FROM] = UID
    mapMessage[CHILD_TYPE] = typeText
    mapMessage[CHILD_TEXT] = message
    mapMessage[CHILD_ID] = messageKey.toString()
    mapMessage[CHILD_TIMESTAMP] = ServerValue.TIMESTAMP

    val mapDialog = hashMapOf<String, Any>()
    mapDialog["$refDialogUser/$messageKey"] = mapMessage
    mapDialog["$refDialogReceivingUser/$messageKey"] = mapMessage

    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnSuccessListener { function() }
        .addOnFailureListener {  }

}
