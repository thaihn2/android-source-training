package thaihn.android.daggerandroid.di.component;

import dagger.Component;
import thaihn.android.daggerandroid.MainActivity;
import thaihn.android.daggerandroid.di.PerActivity;
import thaihn.android.daggerandroid.di.module.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
}
