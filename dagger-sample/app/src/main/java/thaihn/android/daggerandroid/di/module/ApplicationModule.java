package thaihn.android.daggerandroid.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import thaihn.android.daggerandroid.di.ApplicationContext;
import thaihn.android.daggerandroid.di.DatabaseInfo;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        this.mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context providerContext() {
        return mApplication;
    }

    @Provides
    Application providerApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String providerDatabaseName() {
        return "dagger.db";
    }

    @Provides
    @DatabaseInfo
    int providerDatabaseVersion() {
        return 2;
    }

    @Provides
    SharedPreferences providerSharePreference() {
        return mApplication.getSharedPreferences("demo_prefs",
                Context.MODE_PRIVATE);
    }
}
