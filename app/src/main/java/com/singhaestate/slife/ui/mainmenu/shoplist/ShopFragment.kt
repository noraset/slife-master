package com.singhaestate.slife.ui.mainmenu.shoplist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.mainmenu.shoplist.detail.ShopDetailActivity
import com.singhaestate.slife.ui.mainmenu.shoplist.model.ShopListModel
import com.singhaestate.slife.util.Contextor.context
import com.singhaestate.slife.util.ShopListUtil
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment() {

    private val ARG_LIST_TYPE = "LIST_TYPE"
    private var shopListModel = ArrayList<ShopListModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootLayout.requestFocus()
        val listType = this.arguments!!.getSerializable(ARG_LIST_TYPE) as ListType
        when (listType) {
            ListType.Shop -> shopListModel = shop()
            ListType.Restaurant -> shopListModel = restaurant()
        }
        listView.layoutManager = LinearLayoutManager(this@ShopFragment.context)
        listView.itemAnimator = DefaultItemAnimator()
        listView.addItemDecoration(DividerItemDecoration(this@ShopFragment.context, LinearLayoutManager.VERTICAL))
        listView.adapter = MyAdapter(this@ShopFragment.context, shopListModel) {
            //            Toast.makeText(context, "" + it, Toast.LENGTH_LONG).show()
            startActivity(Intent(context, ShopDetailActivity::class.java)
                    .putExtra("itemDetail", it.id))
        }

        swipeRefreshLayout.setOnRefreshListener {
            refreshItems()
        }
    }

    private fun refreshItems() {
        onItemLoadeCompleted()
    }

    private fun onItemLoadeCompleted() {
        swipeRefreshLayout.isRefreshing = false
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val logo: ImageView = itemView.findViewById<ImageView>(R.id.icon)
        private val textName: TextView = itemView.findViewById(R.id.textName)
        private val textFloor: TextView = itemView.findViewById(R.id.textFloor)
        private val textType: TextView = itemView.findViewById(R.id.textType)

        fun updateWithShop(shopListModel: ShopListModel) {
            Glide.with(context)
                    .load(shopListModel.logo)
                    .into(logo)
            textName.text = shopListModel.name
            textFloor.text = shopListModel.floor
            textType.text = shopListModel.type
        }
    }

    inner class MyAdapter(context: Context?, private val shopListModel: ArrayList<ShopListModel>,
                          val clickListener: (ShopListModel) -> Unit) : RecyclerView.Adapter<ViewHolder>() {
        private val context: Context? = context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return shopListModel.toArray().count()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var item: ShopListModel = shopListModel[position]
            holder.updateWithShop(item)
            holder.itemView.setOnClickListener {
                clickListener.invoke(item) // <- item instance of ItemObject
            }
        }
    }

    companion object {
        val ARG_LIST_TYPE = "LIST_TYPE"

        @JvmStatic
        fun newInstance() = ShopFragment()

        fun newInstance(listType: ListType): ShopFragment {
            val fragment = ShopFragment()
            val args = Bundle()
            args.putSerializable(ARG_LIST_TYPE, listType)
            fragment.arguments = args
            return fragment
        }
    }

    fun shop(): ArrayList<ShopListModel> {
        val allPuppies = ShopListUtil.getInstance().getResultInfo()

        val selectedPuppies = ArrayList<ShopListModel>()
        for (puppy in allPuppies) {
            if (puppy.type.equals("Shop"))
                selectedPuppies.add(puppy)
        }
        return selectedPuppies
    }

    fun restaurant(): ArrayList<ShopListModel> {
        val allPuppies = ShopListUtil.getInstance().getResultInfo()
        val selectedPuppies = ArrayList<ShopListModel>()
        for (puppy in allPuppies) {
            if (puppy.type.equals("Restaurant"))
                selectedPuppies.add(puppy)
        }
        return selectedPuppies
    }
}
