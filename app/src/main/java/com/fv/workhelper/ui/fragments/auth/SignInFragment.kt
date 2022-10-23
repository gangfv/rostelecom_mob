package com.fv.workhelper.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.fv.workhelper.R
import com.fv.workhelper.database.*
import com.fv.workhelper.database.dataclasses.User
import com.fv.workhelper.ui.fragments.HomeFragment
import com.fv.workhelper.utilits.APP_ACTIVITY
import com.fv.workhelper.utilits.AppValueEventListener
import com.fv.workhelper.utilits.replaceFragment
import com.fv.workhelper.utilits.showToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.nav_header.*


class SignInFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        Click()
    }

    private fun Click() {
        textView.setOnClickListener {
            replaceFragment(SignUpFragment())
        }

        button.setOnClickListener {
            val email = emailEt.text.toString()
            val pass = passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        initFirebase()
                        replaceFragment(HomeFragment())
                    } else {
                        showToast("Неправильный логин или пароль!")
                    }
                }
            } else {
                showToast("Пустые поля не допускаются!")
            }
        }
    }


}