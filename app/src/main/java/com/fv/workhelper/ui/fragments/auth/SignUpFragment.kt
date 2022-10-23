package com.fv.workhelper.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fv.workhelper.R
import com.fv.workhelper.database.*
import com.fv.workhelper.database.dataclasses.User
import com.fv.workhelper.ui.fragments.HomeFragment
import com.fv.workhelper.utilits.APP_ACTIVITY
import com.fv.workhelper.utilits.AppValueEventListener
import com.fv.workhelper.utilits.replaceFragment
import com.fv.workhelper.utilits.showToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.nav_header.*


class SignUpFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {
            val fio = fioEt.text.toString()
            val numberPhone = numberET.text.toString()
            val email = emailEt.text.toString()
            val pass = passwordEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && fio.isNotEmpty() && numberPhone.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val uid = AUTH.currentUser?.uid.toString()
                        val dateMap = mutableMapOf<String, Any>()
                        dateMap[CHILD_ID] = uid
                        dateMap[CHILD_FIO] = fio
                        dateMap[CHILD_EMAIL] = email
                        dateMap[CHILD_NUMBERPHONE] = numberPhone

                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                            .addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    initFirebase()
                                    replaceFragment(HomeFragment())
                                } else {
                                    showToast(it.exception.toString())

                                }
                            }
                    }
                }
            }
        }
    }

}