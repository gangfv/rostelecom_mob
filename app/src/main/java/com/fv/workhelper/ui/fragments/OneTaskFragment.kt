package com.fv.workhelper.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fv.workhelper.R
import com.fv.workhelper.database.*
import com.fv.workhelper.ui.fragments.tasks.UserTasksFragment
import com.fv.workhelper.utilits.replaceFragment
import kotlinx.android.synthetic.main.fragment_one_task.*


class OneTaskFragment(val mIntent: Intent, val frag: String) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_task, container, false)
    }

    override fun onStart() {
        super.onStart()
        Back(frag)
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

        val dateMap = mutableMapOf<String, Any>()
        dateMap[CHILD_EXECUTOR] = UID


        button.setOnClickListener {
            REF_DATABASE_ROOT.child(NODE_TASKS).child(projectId.toString()).updateChildren(dateMap)
        }
    }

    fun Back(frag: String) {
        constraintLayout2.setOnClickListener {
            if (frag == "Home"){
                replaceFragment(HomeFragment())
            }
            if (frag == "User"){
                replaceFragment(UserTasksFragment())
            }
        }
    }
}