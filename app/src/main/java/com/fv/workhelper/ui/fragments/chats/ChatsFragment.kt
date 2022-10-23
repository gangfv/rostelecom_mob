package com.fv.workhelper.ui.fragments.chats

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.fv.workhelper.R
import com.fv.workhelper.database.*
import com.fv.workhelper.database.dataclasses.CommonModel
import com.fv.workhelper.utilits.APP_ACTIVITY
import com.fv.workhelper.utilits.AppValueEventListener
import com.fv.workhelper.utilits.getCommonModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_chats.*


class ChatsFragment : Fragment(R.layout.fragment_chats) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ChatsAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(UID)
    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.AppName.text = "Чаты"
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_list_recycle_view
        mAdapter = ChatsAdapter()

        // 1 запрос
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mListItems.forEach { model ->

                // 2 запрос
                mRefUsers.child(model.id)
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                        val newModel = dataSnapshot1.getCommonModel()

                        // 3 запрос
                        mRefMessages.child(model.id).limitToLast(1)
                            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                                val tempList = dataSnapshot2.children.map { it.getCommonModel() }
                                newModel.lastMessage = tempList[0].text

                                if (newModel.fio.isEmpty()) {
                                    newModel.fio = newModel.numberPhone
                                }
                                mAdapter.updateListItems(newModel)
                            })
                    })
            }
        })

        mRecyclerView.adapter = mAdapter
    }
}
