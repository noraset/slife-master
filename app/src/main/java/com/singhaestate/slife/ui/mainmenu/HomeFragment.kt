package com.singhaestate.slife.ui.mainmenu

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.mainmenu.buildinginfo.MapsActivity
import com.singhaestate.slife.ui.mainmenu.shoplist.ShopListActivity
import com.singhaestate.slife.ui.mainmenu.shoplist.model.ShopListModel
import com.singhaestate.slife.util.GlideImageLoadingService
import com.singhaestate.slife.util.ShopListUtil
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import ss.com.bannerslider.Slider
import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.text.ParseException

class HomeFragment : Fragment() {

    private val mImages: MutableList<String> = arrayListOf()
    private val mMenus: MutableList<HomeItem> = arrayListOf()
    private var mImageAdapter: MyImageAdapter? = null
    private var mAdapter: MyMenuAdapter? = null
    private var shopinfo = ArrayList<ShopListModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        get_json()
    }

    private fun init() {

        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))
        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))
        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))
        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))
        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))
        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))
        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))
        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))
        mMenus.add(HomeItem("", "สแกนบาร์โค้ด"))

        mImages.add("http://sl.glitter-graphics.net/pub/530/530072lntiktklog.png")
        mImages.add("http://sl.glitter-graphics.net/pub/530/530072lntiktklog.png")
        mImages.add("http://sl.glitter-graphics.net/pub/530/530072lntiktklog.png")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstances()
    }

    private fun initInstances() {
        rootLayout.requestFocus()
        mImageAdapter = MyImageAdapter()
        Slider.init(GlideImageLoadingService(this@HomeFragment.context))
        slider.setAdapter(mImageAdapter)

        initMenu()
    }

    private fun initMenu() {
        mAdapter = MyMenuAdapter()
        listView.adapter = mAdapter
    }

    inner class MyMenuAdapter : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val view = convertView
                    ?: LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)

            val item = mMenus[position]
            val title = view.findViewById<TextView>(R.id.title)
            title.text = item.name
            view.setOnClickListener {
                if (position == 1) {
                    startActivity(Intent(this@HomeFragment.context, ShopListActivity::class.java))
                } else if (position == 4) {
                    startActivity(Intent(this@HomeFragment.context, MapsActivity::class.java))
                }
            }

            return view
        }

        override fun getItem(position: Int): Any = mMenus[position]
        override fun getItemId(position: Int): Long = 0L
        override fun getCount(): Int = mMenus.size

    }

    private fun get_json() {
        var json: String

        try {

            val inputStream: InputStream = activity!!.assets.open("file.json")
            var size: Int = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
            var jsonArray = JSONArray(json)
            for (jsonIndex in 0..(jsonArray.length() - 1)) {
                shopinfo.add(Gson().fromJson(jsonArray[jsonIndex].toString(), ShopListModel::class.java))
            }
            ShopListUtil.getInstance().setResultInfo(shopinfo)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }


    }


    inner class MyImageAdapter : SliderAdapter() {
        override fun getItemCount(): Int = mImages.size

        override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
//            imageSlideViewHolder?.bindImageSlide(R.drawable.home_image)
            Glide.with(this@HomeFragment.context!!)
                    .load(mImages[position])
                    .into(imageSlideViewHolder!!.imageView)
        }
    }

    data class HomeItem(
            val icon: String,
            val name: String
    )

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
