package ostanina.kk.appstest.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ostanina.kk.appstest.ui.MainActivity;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
}
