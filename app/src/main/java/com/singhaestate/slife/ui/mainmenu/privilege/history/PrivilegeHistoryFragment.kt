package com.singhaestate.slife.ui.mainmenu.privilege.history


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.singhaestate.slife.R
import kotlinx.android.synthetic.main.fragment_news.*


class PrivilegeHistoryFragment : Fragment() {

    private var mAdapter: MyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_privilege_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = MyAdapter(context)

        listView.layoutManager = LinearLayoutManager(context)
        listView.itemAnimator = DefaultItemAnimator()
        listView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        listView.adapter = mAdapter

        swipeRefreshLayout.setOnRefreshListener {
            refreshItems()
        }
    }

    private fun refreshItems() {
        onItemLoadedCompleted()
    }

    private fun onItemLoadedCompleted() {
        swipeRefreshLayout.isRefreshing = false
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class MyAdapter(context: Context?) : RecyclerView.Adapter<ViewHolder>() {
        private val context: Context? = context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.privilege_history_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PrivilegeHistoryFragment()
    }
}
