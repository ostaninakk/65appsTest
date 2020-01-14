package ostanina.kk.appstest;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import ostanina.kk.appstest.di.component.DaggerAppComponent;

public class AppController extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}