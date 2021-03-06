/*
The MIT License (MIT)

Copyright (c) 2018 Daniel Illescas Romero <https://github.com/illescasDaniel>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson

class PreferencesManager {

    enum class Properties {
        isLogged,
        username, password,
        notify_followers
    }

    var preferences: SharedPreferences? = null
        private set
    var editor: SharedPreferences.Editor? = null
        private set

    companion object {

        /**
         * You need to call initializeFrom(context:) first, or use with(context:) static method.
         */
        val default = PreferencesManager()
        val custom = PreferencesManager()
        const val customName = "UserCustomPreferences"

        fun initializeFrom(context: Context) {

            custom.preferences = context.getSharedPreferences(customName, Context.MODE_PRIVATE)
            custom.editor = custom.preferences?.edit().also { it?.apply() }

            default.preferences = PreferenceManager.getDefaultSharedPreferences(context)
            default.editor = default.preferences?.edit().also { it?.apply() }
        }

        fun with(context: Context): PreferencesManager {
            if (custom.preferences == null || custom.editor == null) {
                initializeFrom(context)
            }
            return custom
        }
    }

    inline operator fun <reified T>get(property: Properties, default: T): T {

        if (!this.exists(property.name)) { return default }

        return when (default) {
            is Int -> this.preferences?.getInt(property.name, default) as T
            is Long -> this.preferences?.getLong(property.name, default) as T
            is Float -> this.preferences?.getFloat(property.name, default) as T
            is Boolean -> this.preferences?.getBoolean(property.name, default) as T
            is String -> this.preferences?.getString(property.name, default) as T
            else -> {
                val json = this.preferences?.getString(property.name, "").orEmpty()
                return Gson().from<T>(json) ?: default
            }
            // If you don't want to use Gson for custom classes:
            // else -> default
        }
    }

    operator fun <T>set(property: Properties, value: T) {

        when (value) {
            is Int -> this.editor?.putInt(property.name, value)
            is Long -> this.editor?.putLong(property.name, value)
            is Float -> this.editor?.putFloat(property.name, value)
            is Boolean -> this.editor?.putBoolean(property.name, value)
            is String -> this.editor?.putString(property.name, value)
            else -> {
                val json = Gson().toJson(value)
                this.editor?.putString(property.name, json)
            }
            // If you don't want to use Gson for custom classes
            // else -> return
        }
        this.editor?.apply()
    }

    fun exists(propertyKey: String): Boolean {
        return this.preferences?.contains(propertyKey) == true
    }

    fun apply(vararg values: Pair<Any, Properties>) {
        values.forEach { this[it.second] = it.first }
        this.editor?.apply()
    }

    fun remove(property: Properties): PreferencesManager {
        this.editor?.remove(property.name)
        this.editor?.apply()
        return this
    }

    fun clear(): PreferencesManager {
        this.editor?.clear()
        this.editor?.apply()
        return this
    }
}

inline fun <reified T>Gson.from(json: String): T? {
    return try {
        this.fromJson<T>(json, T::class.java)
    } catch (e: Exception) {
        null
    }
}
