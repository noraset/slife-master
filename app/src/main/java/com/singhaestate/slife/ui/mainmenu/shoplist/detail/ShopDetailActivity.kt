package com.singhaestate.slife.ui.mainmenu.shoplist.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.mainmenu.buildinginfo.MapsActivity
import com.singhaestate.slife.ui.mainmenu.shoplist.model.ShopListModel
import com.singhaestate.slife.util.GlideImageLoadingService
import com.singhaestate.slife.util.ShopListUtil
import kotlinx.android.synthetic.main.activity_shop_detail.*
import ss.com.bannerslider.Slider
import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder


class ShopDetailActivity : AppCompatActivity() {

    private val mImages: MutableList<String> = arrayListOf()
    private var mImageAdapter: MyImageAdapter? = null
    private var shopList: ShopListModel? = null
    private var itemMaps: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)

        init()
        initInstances()
    }

    private fun init() {
        val item = intent.getIntExtra("itemDetail", 0)
        itemMaps = intent.getBooleanExtra("itemDetailFromMaps", false)
        val allShop = ShopListUtil.getInstance().getResultInfo()
        if (itemMaps) {
            btnMap.visibility = View.VISIBLE
        }
        for (detail in allShop) {
            if (detail.id == item)
                shopList = detail
        }
        mImages.add(shopList?.banner!!)
    }

    @SuppressLint("ResourceAsColor")
    private fun initInstances() {
        rootLayout.requestFocus()
        mImageAdapter = MyImageAdapter()
        Slider.init(GlideImageLoadingService(this@ShopDetailActivity))
        slider.setAdapter(mImageAdapter)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setTitleTextColor(R.color.primary)
        toolbar_title.text = shopList!!.name
        Glide.with(this)
                .load(shopList!!.logo)
                .into(logoImageView)
        shopName.text = shopList!!.name
        shopType.text = shopList!!.type
        textFloor.text = shopList!!.floor
        textDesc.text = shopList!!.description
        textTel.text = shopList!!.phone
        textHours.text = "Hours : " + shopList?.open_close
        textWeb.text = shopList!!.website
        btnMap.setOnClickListener {
            if (itemMaps) {
                finish()
            } else {
                startActivity(Intent(this, MapsActivity::class.java)
                        .putExtra("maps", shopList!!.id)
                        .putExtra("floor", shopList!!.floor))
            }
        }
    }

    inner class MyImageAdapter : SliderAdapter() {
        override fun getItemCount(): Int = mImages.size

        override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
            imageSlideViewHolder?.bindImageSlide(mImages[position])
//            Glide.with(context)
//                    .load(mImages[position])
//                    .into(imageSlideViewHolder!!.imageView)
        }
    }
}
