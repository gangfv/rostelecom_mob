package com.fv.workhelper.ui.fragments.tasks

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.fv.workhelper.database.*
import com.fv.workhelper.ui.fragments.HomeFragment
import com.fv.workhelper.utilits.replaceFragment
import kotlinx.android.synthetic.main.fragment_add_task.*
import java.util.*


class AddTaskFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.fv.workhelper.R.layout.fragment_add_task, container, false)
    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {
            AddTask()
        }
    }

    fun AddTask() {
        val spinner: Spinner = requireView().findViewById(com.fv.workhelper.R.id.spinner)
        val selected = spinner.getSelectedItem().toString();
        val id = UUID.randomUUID().toString()
        val dateMap = mutableMapOf<String, Any>()

        var name = addNameTask_Et.text.toString()
        var description = description.text.toString()

        dateMap[CHILD_ID] = id
        dateMap[CHILD_NAME] = name
        dateMap[CHILD_DESC] = description
        dateMap[CHILD_AUTHOR] = UID.toString()
        dateMap[TIME] = "Время выполнения: " + selected


        REF_DATABASE_ROOT.child(NODE_TASKS).child(id).updateChildren(dateMap)
            .addOnCompleteListener { task2 ->
                if (task2.isSuccessful) {
                    replaceFragment(HomeFragment())
                }
            }
    }

}