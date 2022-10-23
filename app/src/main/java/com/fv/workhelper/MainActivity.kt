package com.fv.workhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import com.fv.workhelper.database.*
import com.fv.workhelper.database.dataclasses.ListTasks
import com.fv.workhelper.database.dataclasses.User
import com.fv.workhelper.ui.fragments.HomeFragment
import com.fv.workhelper.ui.fragments.auth.SignInFragment
import com.fv.workhelper.ui.fragments.chats.ChatsFragment
import com.fv.workhelper.ui.fragments.settings.SettingsFragment
import com.fv.workhelper.ui.fragments.tasks.UserTasksFragment
import com.fv.workhelper.utilits.APP_ACTIVITY
import com.fv.workhelper.utilits.AppValueEventListener
import com.fv.workhelper.utilits.DrawerLoad
import com.fv.workhelper.utilits.replaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.badge_text.*
import kotlinx.android.synthetic.main.badge_text.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var database: DatabaseReference
    var AUTH = FirebaseAuth.getInstance()
    private lateinit var notificationBadges: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        APP_ACTIVITY = this
        database = Firebase.database.reference
    }

    override fun onStart() {
        super.onStart()
        initFirebase()
        initFunc()
        BottomNav()
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            initUser()
            replaceFragment(HomeFragment())
        } else {
            replaceFragment(SignInFragment())
        }
    }



    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener{
                USER = it.getValue(User::class.java) ?: User()

            })
    }

    fun BottomNav(){
        bottomNav.setOnNavigationItemSelectedListener  {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.message -> {
                    replaceFragment(ChatsFragment())
                    true
                }
                R.id.basket -> {
                    replaceFragment(UserTasksFragment())
                    true
                }
                R.id.settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }


}