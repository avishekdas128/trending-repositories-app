package com.orangeink.trending.util

import androidx.annotation.StringDef

@StringDef(Constants.KEY_RECYCLER_STATE)
@Retention(AnnotationRetention.SOURCE)
annotation class Constants {
    companion object {
        const val KEY_RECYCLER_STATE = "recycler_state"
    }
}