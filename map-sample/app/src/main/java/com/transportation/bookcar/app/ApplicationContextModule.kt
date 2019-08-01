package com.transportation.bookcar.app

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created on 4/10/2018.
 */
@Module
class ApplicationContextModule(val context: Context) {
    @Provides
    fun applicationContext(): Context = context
}
