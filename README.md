# PreferencesManagerKT

[![Kotlin version](https://img.shields.io/badge/Kotlin-1.2-brightgreen.svg)](https://kotlinlang.org/)
[![license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/illescasDaniel/PreferencesManagerKT/blob/master/LICENSE)

A simple way to manage local values in your Android apps.

You can get the preferences values for the default `SharedPreferences`with `default`, and your custom ones with `custom`. The preferences saved for switches for example are saved in the default `SharedPreferences`. 

iOS version [here](https://github.com/illescasDaniel/PreferencesManagerSwift).

Setup
-----

0. Add the **Gson** library in your `build.gradle` file (**Optional** for standard types but required for using with custom classes):
   ```gradle
   implementation 'com.google.code.gson:gson:2.8.5'
   ```

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

   **Or** just use the static funciton `with(context:)` indicating the activity context whenever you want to access the properties:

   ```kotlin
   val isLogged = PreferencesManager.customWith(this)[Properties.isLogged, ""]
   ```

Usage
-----

### Get saved values:

```Kotlin
val savedUsername = PreferencesManager.custom[Properties.username, ""]
val switchValue = PreferencesManager.default[Properties.music_enabled, ""]
```

**Notes:** second parameter is the default value. Also, you may want to add `import com.yourpackage.PreferencesManager.Properties` in the file you're using it.

### **Set** values:

```kotlin
// When is only one
PreferencesManager.custom[Properties.username] = "daniel"

// Recommended when modifying multiple properties
PreferencesManager.custom.apply (
    username to Properties.username,
    password to Properties.password
)
```
**Optional:** you can change the internal preferences name value by modifying the `PreferencesManager.customName` property.