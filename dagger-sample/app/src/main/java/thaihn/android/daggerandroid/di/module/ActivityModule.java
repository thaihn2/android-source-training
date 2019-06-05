package thaihn.android.daggerandroid.di.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import thaihn.android.daggerandroid.di.ActivityContext;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context providerConext() {
        return mActivity;
    }

    @Provides
    Activity providerActivity() {
        return mActivity;
    }
}
