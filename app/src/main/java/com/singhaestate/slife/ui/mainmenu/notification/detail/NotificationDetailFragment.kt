package com.singhaestate.slife.ui.mainmenu.notification.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseFragment
import com.singhaestate.slife.util.GlideImageLoadingService
import kotlinx.android.synthetic.main.fragment_notification_detail.*
import ss.com.bannerslider.Slider
import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotificationDetailFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null

    private val mImages: MutableList<String> = arrayListOf()
    private var mImageAdapter: MyImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        init()
    }

    private fun init() {
        mImages.add("TEST")
        mImages.add("TEST")
        mImages.add("TEST")
        mImages.add("TEST")
        mImages.add("TEST")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstances()
    }

    private fun initInstances() {
        rootLayout.requestFocus()
        mImageAdapter = MyImageAdapter()
        Slider.init(GlideImageLoadingService(this@NotificationDetailFragment.context))
        slider.setAdapter(mImageAdapter)
    }

    inner class MyImageAdapter : SliderAdapter() {
        override fun getItemCount(): Int = mImages.size

        override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
            imageSlideViewHolder?.bindImageSlide(R.drawable.notification_image)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                NotificationDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
