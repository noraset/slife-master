package com.singhaestate.slife.ui.mainmenu.shoplist


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.mainmenu.shoplist.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_shop_list.*

//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class ShopListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //            param1 = it.getString(ARG_PARAM1)
            //            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        toolbar_title.setText(R.string.title_shop_list)
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_shop))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_restaurant))

        btn_search.setOnClickListener {
            startActivity(Intent(this@ShopListFragment.context, SearchActivity::class.java))
        }
        val pageAdapter = ShopListPageAdapter((activity as AppCompatActivity).supportFragmentManager)
        viewPager.adapter = pageAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position!!
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                ShopListFragment().apply {
                    arguments = Bundle().apply {
                        //                        putString(ARG_PARAM1, param1)
                        //                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
