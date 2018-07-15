package com.singhaestate.slife.ui.mainmenu.buildinginfo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.mainmenu.shoplist.search.SearchActivity
import com.singhaestate.slife.util.Contextor.context
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private val mMenus: MutableList<FilterItem> = arrayListOf()
    private var mAdapter: FilterAdapter? = null
    private var isSelectAll: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()
    }

    private fun init() {
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", false))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", false))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", false))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", false))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", false))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", false))
        initMenu()
        rootLayout.requestFocus()
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar_title.text = "Building Info"
        toolbarFilter.setNavigationIcon(R.drawable.ic_back_blue)
        toolbarFilter.setNavigationOnClickListener {
            closeFilter()
        }
        toolbar_title_filter.text = "Filter"
        search_bar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        btn_filter.setOnClickListener {
            openFilter()
        }
        btn_search.setOnClickListener {
            closeFilterAndSearch()
        }
        btn_select.setOnClickListener {
            if (isSelectAll) {
                unSelectAll()
            } else {
                selectAll()
            }
        }
    }

    private fun closeFilterAndSearch() {
        view_overlay_filter.visibility = View.GONE
    }

    private fun closeFilter() {
        view_overlay_filter.visibility = View.GONE
    }

    private fun openFilter() {
        view_overlay_filter.visibility = View.VISIBLE
    }


    private fun initMenu() {
        mAdapter = FilterAdapter()
        listView.adapter = mAdapter
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        // 1
        mMap.isMyLocationEnabled = true
// 2
    }

    inner class FilterAdapter : BaseAdapter() {

        @SuppressLint("ResourceAsColor")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val view = convertView
                    ?: LayoutInflater.from(context).inflate(R.layout.filter_item, parent, false)

            val item = mMenus[position]
            val title = view.findViewById<TextView>(R.id.title)
            val icon = view.findViewById<ImageView>(R.id.icon)
            title.text = item.name
            if (mMenus[position].isSelect) {
                icon.setImageResource(R.drawable.barcode_icon)
            } else {
                icon.setImageResource(R.drawable.activity_icon)
            }
            view.setOnClickListener {
                select(position)
            }

            return view
        }

        override fun getItem(position: Int): Any = mMenus[position]
        override fun getItemId(position: Int): Long = 0L
        override fun getCount(): Int = mMenus.size

    }

    private fun select(position: Int) {
        mMenus[position].isSelect = !mMenus[position].isSelect
        mAdapter!!.notifyDataSetChanged()
    }

    private fun selectAll() {
        for (i in 0..(mMenus.size - 1)) {
            mMenus[i].isSelect = true
        }
        isSelectAll = true
        mAdapter!!.notifyDataSetChanged()
        btn_select.text = "UnSeclect All"
    }

    private fun unSelectAll() {
        for (i in 0..(mMenus.size - 1)) {
            mMenus[i].isSelect = false
        }
        isSelectAll = false
        mAdapter!!.notifyDataSetChanged()
        btn_select.text = "Seclect All"
    }

    data class FilterItem(
            val icon: String,
            val name: String,
            var isSelect: Boolean
    )

}
