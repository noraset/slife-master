package com.singhaestate.slife.ui.mainmenu.shoplist.model

data class ShopListModel(
        var id: Int,
        var name: String,
        var username: String,
        var type: String,
        var floor: String,
        var logo: String,
        var banner: String,
        var email: String,
        var address: Addrress,
        var phone: String,
        var website: String,
        var company: Company,
        var description: String,
        var open_close: String
)


data class Addrress(
        var street: String,
        var suite: String,
        var city: String,
        var zipcode: String,
        var geo: Geo
)


data class Geo(
        var lat: String,
        var lng: String
)

data class Company(
        var name: String,
        var bs: String,
        var catchPhrase: String
)
