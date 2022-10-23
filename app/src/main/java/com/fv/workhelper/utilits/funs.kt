package com.fv.workhelper.utilits

import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.fv.workhelper.MainActivity
import com.fv.workhelper.R
import com.fv.workhelper.database.dataclasses.CommonModel
import com.fv.workhelper.database.dataclasses.ListTasks
import com.google.firebase.database.DataSnapshot
import java.text.SimpleDateFormat
import java.util.*

lateinit var APP_ACTIVITY: MainActivity

fun Fragment.showToast(message:String){
    Toast.makeText(this.context,message, Toast.LENGTH_SHORT).show()
}

fun DrawerLoad(){
    val drawerLayout: DrawerLayout = APP_ACTIVITY.findViewById(R.id.drawerLayout)
    APP_ACTIVITY.toggle = ActionBarDrawerToggle(APP_ACTIVITY, drawerLayout, R.string.open, R.string.close)
    drawerLayout.addDrawerListener(APP_ACTIVITY.toggle)
}

fun DataSnapshot.getCommonModel(): CommonModel =
    this.getValue(CommonModel::class.java) ?: CommonModel()

fun DataSnapshot.getTaskModel(): ListTasks =
    this.getValue(ListTasks::class.java) ?: ListTasks()

fun replaceFragment(fragment: Fragment, addStack:Boolean = true){
    if (addStack){
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.dataContainer,
                fragment
            ).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer,
                fragment
            ).commit()
    }
}



fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}