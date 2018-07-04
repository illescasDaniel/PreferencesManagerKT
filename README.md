# PreferencesManager

[![Kotlin version](https://img.shields.io/badge/Kotlin-1.2-brightgreen.svg)](https://kotlinlang.org/)
[![license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/illescasDaniel/PreferencesManager/blob/master/LICENSE)

A simple way to manage local values in your Android apps.

Usage
-----

Inside the PreferencesManager class, add your **properties** (name of the values it will save):

```kotlin
enum class Properties {
    isLogged,
    username, password
    // ...
}
```

Then, call this in the **first activity**:

```kotlin
PreferencesManager.initializeFrom(this)
```

**Get** property values (second parameter is the default value):

```Kotlin
PreferencesManager.shared[Properties.username, ""]
```

**Note:** you may want to do `import com.yourpackage.PreferencesManager.Properties`

**Set** values:

```kotlin
// When is only one
PreferencesManager.shared[Properties.username] = "daniel"

// Recommended when modifying multiple properties
PreferencesManager.shared.apply (
    Properties.username to emailStr,
    Properties.password to passwordStr
)
```