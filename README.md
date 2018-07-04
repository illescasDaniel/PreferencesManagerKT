# PreferencesManagerKT

[![Kotlin version](https://img.shields.io/badge/Kotlin-1.2-brightgreen.svg)](https://kotlinlang.org/)
[![license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/illescasDaniel/PreferencesManagerKT/blob/master/LICENSE)

A simple way to manage local values in your Android apps.

iOS version [here](https://github.com/illescasDaniel/PreferencesManagerSwift).

Setup
-----

1. Add your properties inside the `PreferencesManager` class (name of the values it will save):
	```kotlin
	enum class Properties {
	    isLogged,
	    username, password
	    // ...
	}
	```
2. Initialize the preferences manager in the **first activity** of the app:

	```kotlin
	PreferencesManager.initializeFrom(this)
	```

Usage
-----

### Get saved values:

```Kotlin
val savedUsername = PreferencesManager.shared[Properties.username, ""]
```

**Notes:** second parameter is the default value. Also, you may want to add `import com.yourpackage.PreferencesManager.Properties` in the file you're using it.

### **Set** values:

```kotlin
// When is only one
PreferencesManager.shared[Properties.username] = "daniel"

// Recommended when modifying multiple properties
PreferencesManager.shared.apply (
    username to Properties.username,
    password to Properties.password
)
```
**Optional:** you can change the internal preferences name value by modifying the `PreferencesManager.name` property.