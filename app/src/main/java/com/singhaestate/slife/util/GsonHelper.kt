package com.singhaestate.slife.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

/**
 * Created by Nut on 03/26/18.
 */
object GsonHelper {
    fun getGson(): Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .create()
}