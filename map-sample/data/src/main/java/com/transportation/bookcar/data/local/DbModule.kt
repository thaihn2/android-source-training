package com.transportation.bookcar.data.local

import android.app.Application
import dagger.Module
import dagger.Provides
import com.transportation.bookcar.data.repo.DbRepo
import com.transportation.bookcar.data.repo.LocalRepo

/**
 * Created on 2/2/2018.
 */
@Module
class DbModule(val application: Application) {
    @Provides
    fun provideLocalRepo(): LocalRepo = DbRepo(application)
}
