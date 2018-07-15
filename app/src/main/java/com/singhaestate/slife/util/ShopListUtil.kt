package com.singhaestate.slife.util

import com.singhaestate.slife.ui.mainmenu.shoplist.model.ShopListModel


open class ShopListUtil private constructor() {

    var shopinfo = ArrayList<ShopListModel>()

    companion object {
        //Debuggable field to check instance count
        private val mInstance: ShopListUtil = ShopListUtil()

        @Synchronized
        fun getInstance(): ShopListUtil {
            return mInstance
        }
    }

    fun getResultInfo(): ArrayList<ShopListModel> {
        return shopinfo
    }

    fun setResultInfo(resultInfo:  ArrayList<ShopListModel>) {
        this.shopinfo = resultInfo
    }
}