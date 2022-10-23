package com.fv.workhelper.ui.fragments.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fv.workhelper.R
import com.fv.workhelper.database.dataclasses.CommonModel
import com.fv.workhelper.ui.fragments.chats.single_chat.SingleChatFragment
import com.fv.workhelper.utilits.replaceFragment
import kotlinx.android.synthetic.main.main_item_list.view.*

class ChatsAdapter : RecyclerView.Adapter<ChatsAdapter.MainListHolder>() {

    private var listItems = mutableListOf<CommonModel>()

    class MainListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.main_list_item_name
        val itemLastMessage: TextView = view.main_list_last_message
        val id_us: TextView = view.id_us
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_item_list, parent, false)

        val holder = MainListHolder(view)

        return holder
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
        holder.itemName.text = listItems[position].fio
        holder.itemLastMessage.text = listItems[position].lastMessage
        holder.id_us.text = listItems[position].id
        holder.itemView.setOnClickListener {
            replaceFragment(SingleChatFragment(listItems[position].id))
        }
    }

    fun updateListItems(item:CommonModel){
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }
}