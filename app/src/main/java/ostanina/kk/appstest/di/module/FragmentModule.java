package ostanina.kk.appstest.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ostanina.kk.appstest.ui.EmployeeFragment;
import ostanina.kk.appstest.ui.EmployeeListFragment;
import ostanina.kk.appstest.ui.SpecialtyListFragment;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract SpecialtyListFragment contributeSpecialtyListFragment();

    @ContributesAndroidInjector
    abstract EmployeeListFragment contributeEmployeeListFragment();

    @ContributesAndroidInjector
    abstract EmployeeFragment contributeEmployeeFragment();
}
