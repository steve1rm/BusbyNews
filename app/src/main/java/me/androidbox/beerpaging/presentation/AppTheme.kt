package me.androidbox.beerpaging.presentation

enum class AppTheme {
    MODE_DAY,
    MODE_NIGHT,
    MODE_AUTO;

    companion object {
        fun fromOriginal(index: Int) : AppTheme {
            return entries[index]
        }
    }
}
