# Toasty-Kotlin
Toasty Kotlin 版本
- fork from [Toasy](https://github.com/GrenderG/Toasty.git)

# Example
```kotlin
toast.normal(this, "Normal toast w/o icon")
```
or
```kotlin
val icon = resources.getDrawable(R.drawable.ic_pets_white_48dp)
toast.normal(this)
        .withIcon(true)
        .icon(icon)
        .message("Normal toast w/ icon")
        .show()
```
see more in [Demo](app/src/main/java/me/codego/toasty/MainActivity.kt)
