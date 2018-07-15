package com.singhaestate.slife.ui.mainmenu.shoplist.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity
import com.singhaestate.slife.ui.mainmenu.shoplist.detail.ShopDetailActivity
import com.singhaestate.slife.ui.mainmenu.shoplist.model.ShopListModel
import com.singhaestate.slife.ui.mainmenu.shoplist.search.model.SearchRow
import com.singhaestate.slife.util.ShopListUtil
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        getData(current_page, newText!!)
        return false

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        startActivity(Intent(this, ShopDetailActivity::class.java)
                .putExtra("itemDetail", filteredOutput[position].id))
    }

    private var listData = ArrayList<SearchRow>()
    private var data = ArrayList<ShopListModel>()
    private var search: SearchView? = null

    private var current_page: Int = 1
    private var filteredOutput = ArrayList<SearchRow>()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        rootLayout.requestFocus()
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.search)
        toolbar.setTitleTextColor(R.color.primary)
        toolbar.setNavigationIcon(R.drawable.ic_back_blue)
        toolbar.setNavigationOnClickListener { finish() }
        listView!!.setOnItemClickListener(this)
        data = ShopListUtil.getInstance().getResultInfo()
        for (i in 0..(data.size - 1)) {
            listData.add(SearchRow(data[i].id,
                    data[i].name, data[i].logo))
        }
        getData(current_page, "")
    }

    private fun getData(pageno: Int, query: String) {
        var output = ArrayList<SearchRow>()
        filteredOutput = ArrayList<SearchRow>()

        for (i in pageno - 1..(listData.size - 1)) {
            output!!.add(listData[i])
        }
        if (search != null) {
            for (detail in output) {
                if (detail.name!!.toLowerCase().startsWith(query.toLowerCase())) {
                    filteredOutput!!.add(detail)
                }
            }
        } else {
            filteredOutput = output
        }
        val adapter = CustomAdapter(this, R.layout.search_item, filteredOutput)

        listView?.adapter = adapter

    }

    class CustomAdapter(context: Context?, var resource: Int, var objects: ArrayList<SearchRow>?) : ArrayAdapter<SearchRow>(context, resource, objects) {

        internal class DataHolder {
            var logo: ImageView? = null
            var name: TextView? = null

        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView

            var holder: DataHolder? = null

            if (convertView == null) {
                val inflater = (context as Activity).layoutInflater

                convertView = inflater.inflate(resource, parent, false)

                holder = DataHolder()
                holder.logo = convertView!!.findViewById(R.id.child_icon) as ImageView
                holder.name = convertView.findViewById(R.id.child_text) as TextView

                convertView.tag = holder
            } else {
                holder = convertView.tag as DataHolder
            }

            val dataItem = objects!!.get(position)
            holder.name!!.setText(dataItem.name)

            Glide.with(context)
                    .load(dataItem.image!!)
                    .into(holder.logo!!)

            return convertView
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        var searchItem = menu!!.findItem(R.id.searchNote)
        search = searchItem?.actionView as SearchView
        search?.setQuery(getString(R.string.search), true)
        search?.setIconifiedByDefault(true)
        search?.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }
}
