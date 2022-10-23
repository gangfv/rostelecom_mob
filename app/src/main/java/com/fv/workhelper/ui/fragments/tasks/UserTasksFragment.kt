package com.fv.workhelper.ui.fragments.tasks

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fv.workhelper.R
import com.fv.workhelper.database.NODE_TASKS
import com.fv.workhelper.database.REF_DATABASE_ROOT
import com.fv.workhelper.database.UID
import com.fv.workhelper.database.dataclasses.ListTasks
import com.fv.workhelper.ui.fragments.HomeFragment
import com.fv.workhelper.utilits.APP_ACTIVITY
import com.fv.workhelper.utilits.TasksListAdapter
import com.fv.workhelper.utilits.replaceFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerNews
import kotlinx.android.synthetic.main.fragment_one_task.*
import kotlinx.android.synthetic.main.fragment_user_tasks.*
import kotlinx.android.synthetic.main.fragment_user_tasks.button


class UserTasksFragment : Fragment() {

    private lateinit var mAdapter: TasksListAdapter
    private lateinit var projectList: ArrayList<ListTasks>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY.AppName.text = "Заказы"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_tasks, container, false)
    }

    override fun onStart() {
        super.onStart()
        recyclerNews.setVisibility(View.GONE);
        button.setOnClickListener {
            setupRecyclerView("isp")
            recyclerNews.setVisibility(View.VISIBLE);
        }
        button1.setOnClickListener {
            recyclerNews.setVisibility(View.GONE);
            setupRecyclerView("exec")
            recyclerNews.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupRecyclerView(user: String) {
        recyclerNews.layoutManager = LinearLayoutManager(view!!.context)
        projectList = ArrayList()
        mAdapter = TasksListAdapter(view!!.context, projectList, user)
        recyclerNews.setHasFixedSize(true)
        // recyclerAnimals.adapter = mAdapter
        // recyclerAnimals.adapter = mAdapter
        /**getData firebase*/
        getProjectsData(user.toString())
    }

    private fun getProjectsData(user: String) {

        REF_DATABASE_ROOT.child(NODE_TASKS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (productSnapshot in dataSnapshot.children) {
                        val product = productSnapshot.getValue(ListTasks::class.java)
                        if (projectList.contains(product) == false) {
                            println(user)
                            if (user == "isp"){
                                if (product!!.author == UID){
                                    projectList.add(product)
                                }
                            } else {
                                if (product!!.executor == UID){
                                    projectList.add(product)
                                }
                            }
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