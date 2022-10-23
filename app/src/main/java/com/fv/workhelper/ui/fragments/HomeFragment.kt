package com.fv.workhelper.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.fv.workhelper.R
import com.fv.workhelper.database.NODE_TASKS
import com.fv.workhelper.database.REF_DATABASE_ROOT
import com.fv.workhelper.database.dataclasses.ListTasks
import com.fv.workhelper.ui.fragments.tasks.AddTaskFragment
import com.fv.workhelper.utilits.APP_ACTIVITY
import com.fv.workhelper.utilits.TasksListAdapter
import com.fv.workhelper.utilits.replaceFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private lateinit var mAdapter: TasksListAdapter
    private lateinit var projectList: ArrayList<ListTasks>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        add_task_button.setOnClickListener {
            replaceFragment(AddTaskFragment())
        }
        APP_ACTIVITY.AppName.text = "Задачи"
        
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    @SuppressLint("UseRequireInsteadOfGet", "NotifyDataSetChanged")
    private fun setupRecyclerView() {
        recyclerNews.layoutManager = LinearLayoutManager(view!!.context)
        projectList = ArrayList()
        mAdapter = TasksListAdapter(view!!.context, projectList, "0")
        recyclerNews.setHasFixedSize(true)
        // recyclerAnimals.adapter = mAdapter
        // recyclerAnimals.adapter = mAdapter
        /**getData firebase*/
        getProjectsData()
        mAdapter.notifyDataSetChanged()
    }


    private fun getProjectsData() {

        REF_DATABASE_ROOT.child(NODE_TASKS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (productSnapshot in dataSnapshot.children) {
                        val product = productSnapshot.getValue(ListTasks::class.java)
                        if (projectList.contains(product) == false) {
                            projectList.add(product!!)

                        }
                    }
                    try {
                        recyclerNews.adapter = mAdapter
                    } catch (e: NullPointerException) {
                        replaceFragment(HomeFragment())
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}