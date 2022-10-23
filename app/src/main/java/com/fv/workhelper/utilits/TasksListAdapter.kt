package com.fv.workhelper.utilits

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fv.workhelper.R
import com.fv.workhelper.database.UID
import com.fv.workhelper.database.dataclasses.ListTasks
import com.fv.workhelper.databinding.ItemListBinding
import com.fv.workhelper.ui.fragments.OneTaskFragment
import com.fv.workhelper.ui.fragments.tasks.AuthorTaskFragment
import com.fv.workhelper.ui.fragments.tasks.ExecTaskFragment
import java.util.*
import kotlin.collections.ArrayList

class TasksListAdapter(
    var c: Context, var projectList:ArrayList<ListTasks>, var user_role: String
): RecyclerView.Adapter<TasksListAdapter.ProjectViewHolder>()
{
    inner class ProjectViewHolder(var v: ItemListBinding): RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemListBinding>(
            inflter, R.layout.item_list,parent,
            false)
        return ProjectViewHolder(v)

    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val newList = projectList[position]
        holder.v.isProjects = projectList[position]
        holder.v.root.setOnClickListener {
            val name = newList.name
            val info = newList.description
            val author = newList.author
            val time = newList.time
            val id = newList.id

            /**set Data*/
            val mIntent = Intent(c, OneTaskFragment::class.java)
            mIntent.putExtra("name",name)
            mIntent.putExtra("description",info)
            mIntent.putExtra("author",author)
            mIntent.putExtra("time",time)
            mIntent.putExtra("id",id)
            if (user_role == "isp"){
                replaceFragment(AuthorTaskFragment(mIntent))
            }
            if (user_role == "exec"){
                replaceFragment(ExecTaskFragment(mIntent))
            } else {
                replaceFragment(OneTaskFragment(mIntent, "Home"))
            }

        }
    }

    override fun getItemCount(): Int {
        return  projectList.size
    }

    fun updateAdapter(items: ArrayList<ListTasks>){
        // обновляем список
        projectList.clear()
        projectList.addAll(items)
        notifyDataSetChanged()
    }


    fun removeItem(pos: Int){
        projectList.removeAt(pos) // удаляем элемент из списка с позиции pos
        notifyItemRangeChanged(0,projectList.size) // указываем адаптеру новый диапазон элементов
        notifyItemRemoved(pos) // указываем адаптеру, что один элемент удалился
    }
}