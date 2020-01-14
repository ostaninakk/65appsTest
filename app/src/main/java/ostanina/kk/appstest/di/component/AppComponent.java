package ostanina.kk.appstest.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import ostanina.kk.appstest.AppController;
import ostanina.kk.appstest.di.module.ActivityModule;
import ostanina.kk.appstest.di.module.ApiModule;
import ostanina.kk.appstest.di.module.DbModule;
import ostanina.kk.appstest.di.module.ViewModelModule;

@Component(modules = {
        ApiModule.class,
        DbModule.class,
        ViewModelModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent extends AndroidInjector<AppController> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
