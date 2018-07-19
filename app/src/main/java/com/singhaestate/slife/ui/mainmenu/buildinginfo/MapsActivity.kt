package com.singhaestate.slife.ui.mainmenu.buildinginfo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.view.get
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity
import com.singhaestate.slife.ui.mainmenu.buildinginfo.adapter.BtnClickListener
import com.singhaestate.slife.ui.mainmenu.buildinginfo.adapter.FloorAdapter
import com.singhaestate.slife.ui.mainmenu.buildinginfo.model.LocationNearBy
import com.singhaestate.slife.ui.mainmenu.shoplist.detail.ShopDetailActivity
import com.singhaestate.slife.ui.mainmenu.shoplist.search.SearchActivity
import com.singhaestate.slife.util.Contextor.context
import com.singhaestate.slife.util.DialogHelper
import com.singhaestate.slife.util.ShopListUtil
import com.singhaestate.slife.util.Utils
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.filter_layout.*
import kotlinx.android.synthetic.main.shop_item.*
import java.util.*
import kotlin.collections.ArrayList

class MapsActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {
    override fun onMarkerDragEnd(p0: Marker?) {
    }

    override fun onMarkerDragStart(p0: Marker?) {
    }

    override fun onMarkerDrag(p0: Marker?) {

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        if (p0!!.tag != null) {
            val location = p0.tag as LocationNearBy
            for (detail in ShopListUtil.getInstance().getResultInfo()) {
                if (detail.id.equals(location.id)) {
                    Glide.with(context)
                            .load(detail.logo)
                            .into(icon)
                    textFloor.text = detail.floor
                    textName.text = detail.name
                    textType.text = detail.type
                    ic_next.visibility = View.GONE
                    shopId = detail.id
                    mFloor = detail.floor
                }
            }
            loadLocation()
            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude.toDouble(), location.longitude.toDouble())))
            layoutBottom.visibility = View.VISIBLE
            Utils.setLayoutAnimSlideInBottomToTop(layoutBottom, applicationContext)
        } else {
            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(myPlace))
            layoutBottom.visibility = View.GONE
            Utils.setLayoutAnimSlideOutTopToBottom(layoutBottom, applicationContext)
        }

        return false
    }

    private var mMap: GoogleMap? = null
    private var mCurrentLocation: Location? = null
    private val mMenus: MutableList<FilterItem> = arrayListOf()
    private val mLocation: MutableList<LocationNearBy> = arrayListOf()
    private val mFloorList: ArrayList<String> = arrayListOf()
    private val mType: MutableList<String> = arrayListOf()
    private var mAdapter: FilterAdapter? = null
    private var isSelectAll: Boolean = false
    private var isFirstInitial = true
    private var marker: Marker? = null
    private var shopId: Int? = null
    private var mFloor: String = ""
    private var myPlace: LatLng? = null
    private var center: LatLng = LatLng(13.772297, 100.542233)
    private var plan: GroundOverlayOptions = GroundOverlayOptions()

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
        initClick()
        initMenu()
        selectAll()
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {


        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", "Shop", true))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", "Shop", true))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", "Shop", true))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", "Restaurant", true))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", "Restaurant", true))
        mMenus.add(FilterItem("", "สแกนบาร์โค้ด", "Restaurant", true))
        mFloorList.add("G")
        mFloorList.add("F")
        mFloorList.add("1")
        mFloorList.add("3")
        mFloorList.add("4")
        if (intent.getStringExtra("floor") != null) {
            shopId = intent.getIntExtra("maps", 0)
            mFloor = intent.getStringExtra("floor")
            for (detail in ShopListUtil.getInstance().getResultInfo()) {
                if (detail.id.equals(shopId)) {
                    Glide.with(context)
                            .load(detail.logo)
                            .into(icon)
                    textFloor.text = detail.floor
                    textName.text = detail.name
                    textType.text = detail.type
                    ic_next.visibility = View.GONE
                    shopId = detail.id
                    mFloor = detail.floor
                    layoutBottom.visibility = View.VISIBLE
                    Utils.setLayoutAnimSlideInBottomToTop(layoutBottom, applicationContext)
                }
            }
        } else {
            mFloor = mFloorList[0]
        }
        setPlan(mFloor)
        for (location in ShopListUtil.getInstance().getResultInfo()) {
            mLocation.add(LocationNearBy(location.id, location.name, location.floor, location.type,
                    location.address.geo.lat, location.address.geo.lng))
        }

        rootLayout.requestFocus()
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar_title.text = "Building Info"
        toolbarFilter.setNavigationIcon(R.drawable.ic_back_blue)

        toolbar_title_filter.text = "Filter"
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listViewFloor.layoutManager = lm

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        listViewFloor.adapter = FloorAdapter(mFloorList, this, mFloor, false, object : BtnClickListener {
            override fun onBtnClick(position: Int, floor: String) {
                layoutBottom.visibility = View.GONE
                shopId = null
                mFloor = floor
                setPlan(floor)
                loadLocation()
            }
        })
    }

    private fun setPlan(i: String) {
        when (i) {
            "G" -> {
                plan.position(center, 100f, 100f)
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.plan_g))
            }
            "1" -> {
                plan.position(center, 100f, 100f)
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.plan_1))
            }
            "F" -> {
                plan.position(center, 100f, 100f)
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.plan_2))
            }
        }
    }


    private fun initMenu() {
        mAdapter = FilterAdapter()
        listView.adapter = mAdapter
    }

    private fun initClick() {
        toolbarFilter.setNavigationOnClickListener {
            closeFilterAndSearch()
        }
        btnSearch.setOnClickListener {
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
        btn_directory.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java)
                    .putExtra("floor", true))
        }
        btn_info.setOnClickListener {
            startActivity(Intent(this, InformationPolicyActivity::class.java))
        }
        layoutBottom.setOnClickListener {
            startActivity(Intent(context, ShopDetailActivity::class.java)
                    .putExtra("itemDetailFromMaps", true)
                    .putExtra("itemDetail", shopId))
        }
    }

    override fun onBackPressed() {
        if (view_overlay_filter.visibility == View.VISIBLE) {
            closeFilterAndSearch()
        } else {
            super.onBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap!!.setOnMapLongClickListener {
            val uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s,%s (%s)", it.latitude, it.longitude, "Where the party is at")
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    .setPackage("com.google.android.apps.maps"))
        }

        mMap!!.setOnMarkerClickListener(this)
        mMap!!.setOnMarkerDragListener(this)
        // Add a marker in Sydney and move the camera
        mMap!!.uiSettings.isZoomControlsEnabled = true
        if (isLocationEnabled())
            setUpMap()
        else {
            val dialog = AlertDialog.Builder(this@MapsActivity)
                    .setTitle("")
                    .setMessage("GPS ไม่ได้เปิดใช้งาน คุณต้องการเปิดหรือไม?\"")
                    .setPositiveButton("Confirm") { dialog, i ->
                        val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivityForResult(myIntent, 435)
                    }
                    .setNegativeButton("Cancel") { dialog, i ->
                        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 10f))
                        loadLocation()
                    }
            dialog.show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 435) {
            if (isLocationEnabled())
                setUpMap()
            else {
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(13.792183, 100.5223213), 10f))
                loadLocation()
            }
        }
    }

    private fun closeFilterAndSearch() {
        shopId = null
        Utils.setLayoutAnimSlideOutLeftToRight(view_overlay_filter, applicationContext)
        view_overlay_filter.visibility = View.GONE
        loadLocation()

    }

    private fun loadLocation() {
        DialogHelper.showProgressDialog(this)
        mMap!!.clear()
        if (myPlace != null) {
            mMap!!.addMarker(MarkerOptions().position(myPlace!!)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_marker))
            )
        }
        if (mType.size != 0) {
            mMap!!.addGroundOverlay(plan)
            addMarkerToMap(search())
        } else {
            DialogHelper.hideProgressDialog()
            mMap!!.addGroundOverlay(plan)
        }
    }

    private fun search(): List<LocationNearBy> {
        var selectedPuppies = ArrayList<LocationNearBy>()
        selectedPuppies.clear()
        for (location in mLocation) {
            for (type in mType) {
                if (location.type.equals(type)) {
                    if (location.floor.equals(mFloor)) {
                        selectedPuppies.add(location)
                    }
                }
            }
        }
        return selectedPuppies
    }


    private fun openFilter() {
        layoutBottom.visibility = View.GONE
        Utils.setLayoutAnimSlideOutTopToBottom(layoutBottom, applicationContext)
        view_overlay_filter.visibility = View.VISIBLE
        Utils.setLayoutAnimSlideInRightToLeft(view_overlay_filter, applicationContext)
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        mMap!!.isMyLocationEnabled = true
        mMap!!.setOnMyLocationChangeListener(GoogleMap.OnMyLocationChangeListener { location ->
            if (isFirstInitial) {
                isFirstInitial = false
                mCurrentLocation = location
                myPlace = LatLng(location.latitude, location.longitude)
                mMap!!.addMarker(MarkerOptions().position(myPlace!!)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_marker))
                )
                mMap!!.addGroundOverlay(plan)
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 17f))
                addMarkerToMap(search())
            }
        })
    }

    private fun createStoreMarker(id: Int, name: String): Bitmap {
        val markerLayout = layoutInflater.inflate(R.layout.store_marker_layout, null)
        val markerImage = markerLayout.findViewById(R.id.marker_image) as ImageView
        val markerRating = markerLayout.findViewById(R.id.marker_text) as TextView
        if (id == shopId) {
            markerImage.setImageResource(R.drawable.ic_place_blue)
//            marker!!.showInfoWindow()
        }
        markerRating.text = name

        markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        markerLayout.layout(0, 0, markerLayout.measuredWidth, markerLayout.measuredHeight)

        val bitmap = Bitmap.createBitmap(markerLayout.measuredWidth, markerLayout.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        markerLayout.draw(canvas)
        return bitmap
    }

    private fun addMarkerToMap(data: List<LocationNearBy>) {
        if (data.isNotEmpty()) {
            val builder = LatLngBounds.Builder()
            builder.include(center)
            for (location in data) {
                marker = mMap!!.addMarker(MarkerOptions()
                        .position(LatLng(java.lang.Double.valueOf(location.latitude), java.lang.Double.valueOf(location.longitude)))
//                        .anchor(0.5f, 0.5f)
                        .title(location.name)
                        .icon(BitmapDescriptorFactory.fromBitmap(createStoreMarker(location.id, location.name)))
//                        .infoWindowAnchor(0.5f, 3.0f)
                )
                marker!!.tag = location
                builder.include(marker!!.getPosition())
            }
            val bounds = builder.build()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val padding = (width * 0.15).toInt() // offset from edges of the map 10% of screen

            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            this.mMap!!.moveCamera(cameraUpdate)
            DialogHelper.hideProgressDialog()
        } else {
            DialogHelper.hideProgressDialog()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationMode = 0
        val locationProviders: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(applicationContext.contentResolver, Settings.Secure.LOCATION_MODE)

            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return false
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF

        } else {
            locationProviders = Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
            return !TextUtils.isEmpty(locationProviders)
        }
    }

    inner class FilterAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val view = convertView
                    ?: LayoutInflater.from(context).inflate(R.layout.filter_item, parent, false)

            val item = mMenus[position]
            val layoutItemFilter = view.findViewById<RelativeLayout>(R.id.layoutItemFilter)
            val filter_title = view.findViewById<TextView>(R.id.filter_title)
            val icon = view.findViewById<ImageView>(R.id.filter_icon)
            filter_title.text = item.name
            if (mMenus[position].isSelect) {
                layoutItemFilter.setBackgroundResource(R.drawable.filter_primary_btn)
                filter_title.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                layoutItemFilter.setBackgroundResource(R.drawable.filter_white_btn)
                filter_title.setTextColor(ContextCompat.getColor(context, R.color.grey03))
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
        if (isSelectAll) {
            btn_select.text = "Seclect all"
            isSelectAll = false
        }
        if (mMenus[position].isSelect) {
            mMenus[position].isSelect = false
            mType.remove(mMenus[position].type)
        } else {
            mMenus[position].isSelect = true
            mType.add(mMenus[position].type)
        }
        mAdapter!!.notifyDataSetChanged()
    }

    private fun selectAll() {
        mType.clear()
        for (i in 0..(mMenus.size - 1)) {
            mMenus[i].isSelect = true
            mType.add(mMenus[i].type)
        }
        isSelectAll = true
        mAdapter!!.notifyDataSetChanged()
        btn_select.text = "UnSeclect all"
    }

    private fun unSelectAll() {
        mType.clear()
        for (i in 0..(mMenus.size - 1)) {
            mMenus[i].isSelect = false
        }
        isSelectAll = false
        mAdapter!!.notifyDataSetChanged()
        btn_select.text = "Seclect all"
    }

    data class FilterItem(
            val icon: String,
            val name: String,
            val type: String,
            var isSelect: Boolean = false
    )

}
