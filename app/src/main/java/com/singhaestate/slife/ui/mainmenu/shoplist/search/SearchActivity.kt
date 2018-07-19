package com.singhaestate.slife.ui.mainmenu.shoplist.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ravikoradiya.library.CenterTitle
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity
import com.singhaestate.slife.ui.mainmenu.buildinginfo.adapter.BtnClickListener
import com.singhaestate.slife.ui.mainmenu.buildinginfo.adapter.FloorAdapter
import com.singhaestate.slife.ui.mainmenu.shoplist.detail.ShopDetailActivity
import com.singhaestate.slife.ui.mainmenu.shoplist.model.ShopListModel
import com.singhaestate.slife.ui.mainmenu.shoplist.search.model.SearchRow
import com.singhaestate.slife.util.ShopListUtil
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        listViewFloor.visibility = View.GONE
        getData(current_page, newText!!)
        return false
    }

    private var listData = ArrayList<SearchRow>()
    private var data = ArrayList<ShopListModel>()
    private var search: SearchView? = null

    private var current_page: Int = 1
    private var filteredOutput = ArrayList<SearchRow>()
    private val mFloorList: ArrayList<String> = arrayListOf()
    private var floorListData: ArrayList<SearchRow> = arrayListOf()
    private var mFloor: String = ""
    private var boolean: Boolean = false
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        rootLayout.requestFocus()
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_blue)
        toolbar.setNavigationOnClickListener { finish() }
        CenterTitle.centerTitle(toolbar, true);
        mFloorList.add("G")
        mFloorList.add("1")
        mFloorList.add("2")
        mFloorList.add("3")
        mFloorList.add("4")
        data = ShopListUtil.getInstance().getResultInfo()
        boolean = intent.getBooleanExtra("floor", false)
        for (i in 0..(data.size - 1)) {
            listData.add(SearchRow(data[i].id,
                    data[i].name, data[i].logo))
        }
        if (boolean) {
            listViewFloor.visibility = View.VISIBLE
            mFloor = mFloorList[0]
            loadData()
        } else {
            getData(current_page, "")
        }
        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        listViewFloor.layoutManager = lm

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        listViewFloor.adapter = FloorAdapter(mFloorList, this, mFloor, true, object : BtnClickListener {
            override fun onBtnClick(position: Int, floor: String) {
                mFloor = floor
                loadData()
            }
        })

    }

    private fun loadData() {
        floorListData = ArrayList<SearchRow>()

        for (floorData in data) {
            if (floorData.floor.equals(mFloor)) {
                floorListData.add(SearchRow(floorData.id,
                        floorData.name, floorData.logo))
            }
        }
        val adapter = CustomAdapter(this, R.layout.search_item, floorListData)
        listView?.adapter = adapter

    }

    private fun getData(pageno: Int, query: String) {
        var output = ArrayList<SearchRow>()
        filteredOutput = ArrayList<SearchRow>()

        for (i in pageno - 1..(listData.size - 1)) {
            output.add(listData[i])
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
            convertView.setOnClickListener {
                context.startActivity(Intent(context, ShopDetailActivity::class.java)
                        .putExtra("itemDetail", dataItem.id))
            }

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
        val menuItem = menu.findItem(R.id.searchNote)
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                if (boolean) {
                    listViewFloor.visibility = View.VISIBLE
                    loadData()
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
