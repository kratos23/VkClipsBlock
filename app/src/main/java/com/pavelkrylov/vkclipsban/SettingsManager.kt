package com.pavelkrylov.vkclipsban

import android.content.Context
import kotlin.reflect.KProperty


object SettingsManager {
    init {
        val x by lazy { 2 }
    }

    private const val PREFS_NAME = "settings"

    private val preferences = App.INSTANCE.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    var blockEnabled by BooleanSettingsDelegate(true)

    class BooleanSettingsDelegate(val defaultValue: Boolean = false) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            return preferences.getBoolean(property.name, defaultValue)
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            preferences.edit().putBoolean(property.name, value).apply()
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}