package com.fv.workhelper.ui.fragments.tasks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fv.workhelper.R
import com.fv.workhelper.database.*
import com.fv.workhelper.database.dataclasses.Note
import com.fv.workhelper.ui.fragments.HomeFragment
import com.fv.workhelper.ui.fragments.chats.ChatsFragment
import com.fv.workhelper.ui.fragments.chats.single_chat.SingleChatFragment
import com.fv.workhelper.utilits.APP_ACTIVITY
import com.fv.workhelper.utilits.AppValueEventListener
import com.fv.workhelper.utilits.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exec_task.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_one_task.*
import kotlinx.android.synthetic.main.fragment_one_task.author
import kotlinx.android.synthetic.main.fragment_one_task.description
import kotlinx.android.synthetic.main.fragment_one_task.name_task
import kotlinx.android.synthetic.main.fragment_one_task.time
import kotlinx.android.synthetic.main.fragment_user_tasks.button


class ExecTaskFragment(val mIntent: Intent) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exec_task, container, false)
    }

    override fun onStart() {
        super.onStart()
        val projectIntent = mIntent
        val projectName = projectIntent.getStringExtra("name")
        val projectInfo = projectIntent.getStringExtra("description")
        val projectAuthor = projectIntent.getStringExtra("author")
        val projectTime = projectIntent.getStringExtra("time")
        val projectId = projectIntent.getStringExtra("id")

        name_task.text = projectName
        description.text = projectInfo
        author.text = "Куратор: "+projectAuthor
        time.text = projectTime
        REF_DATABASE_ROOT.child(NODE_TASKS).child(projectId.toString()).child("note").child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener{
                NOTE = it.getValue(Note::class.java) ?: Note()
                note.setText(NOTE.note)
            })

        button.setOnClickListener {
            replaceFragment(SingleChatFragment(projectAuthor.toString()))
        }

        button_notes.setOnClickListener {
            if (note.text.isNotEmpty()){
                val dateMap = mutableMapOf<String, Any>()
                var note = note.text

                dateMap["note"] = note.toString()
                dateMap["user_id"] = UID.toString()
                REF_DATABASE_ROOT.child(NODE_TASKS).child(projectId.toString()).child("note").child(UID).updateChildren(dateMap)
                    .addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            replaceFragment(HomeFragment())
                        }
                    }
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }

}