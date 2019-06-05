package thaihn.android.daggerandroid;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import thaihn.android.daggerandroid.data.DataManager;
import thaihn.android.daggerandroid.di.component.ApplicationComponent;
import thaihn.android.daggerandroid.di.component.DaggerApplicationComponent;
import thaihn.android.daggerandroid.di.module.ApplicationModule;

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Inject
    DataManager mDataManager;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
