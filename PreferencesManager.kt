import android.content.Context
import android.content.SharedPreferences

class PreferencesManager {

	enum class Properties {
		isLogged,
		username, password
	}

	var preferences: SharedPreferences? = null
	var editor: SharedPreferences.Editor? = null

	companion object {
		var shared = PreferencesManager()
		const val name = "UserPreferences"

		fun initializeFrom(context: Context) {
			shared.preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
			shared.editor = shared.preferences?.edit().also { it?.apply() }
		}
	}

	inline operator fun <reified T>get(property: Properties, alternativeValue: T): T =
		when (alternativeValue) {
			is Int -> this.preferences?.getInt(property.name, alternativeValue) as T
			is Long -> this.preferences?.getLong(property.name, alternativeValue) as T
			is Float -> this.preferences?.getFloat(property.name, alternativeValue) as T
			is Boolean -> this.preferences?.getBoolean(property.name, alternativeValue) as T
			is String -> this.preferences?.getString(property.name, alternativeValue) as T
			else -> alternativeValue
		}

	operator fun <T>set(property: Properties, value: T) {
		when (value) {
			is Int -> this.editor?.putInt(property.name, value)
			is Long -> this.editor?.putLong(property.name, value)
			is Float -> this.editor?.putFloat(property.name, value)
			is Boolean -> this.editor?.putBoolean(property.name, value)
			is String -> this.editor?.putString(property.name, value)
			else -> return
		}
		this.editor?.apply()
	}

	fun apply(vararg values: Pair<Properties, Any>) {
		values.forEach { this[it.first] = it.second }
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