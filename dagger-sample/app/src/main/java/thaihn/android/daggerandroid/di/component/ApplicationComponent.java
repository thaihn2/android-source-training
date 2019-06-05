package thaihn.android.daggerandroid.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import thaihn.android.daggerandroid.MyApplication;
import thaihn.android.daggerandroid.data.DataManager;
import thaihn.android.daggerandroid.data.DbHelper;
import thaihn.android.daggerandroid.data.SharedPrefsHelper;
import thaihn.android.daggerandroid.di.ApplicationContext;
import thaihn.android.daggerandroid.di.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MyApplication myApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    SharedPrefsHelper getSharedPref();

    DbHelper getDbHelper();
}
