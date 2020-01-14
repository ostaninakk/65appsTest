package ostanina.kk.appstest.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ostanina.kk.appstest.di.ViewModelKey;
import ostanina.kk.appstest.factory.ViewModelFactory;
import ostanina.kk.appstest.ui.viewmodels.EmployeeListViewModel;
import ostanina.kk.appstest.ui.viewmodels.EmployeeViewModel;
import ostanina.kk.appstest.ui.viewmodels.SpecialtyListViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(SpecialtyListViewModel.class)
    protected abstract ViewModel specialtyListViewModel(SpecialtyListViewModel specialtyListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EmployeeListViewModel.class)
    protected abstract ViewModel employeeListViewModel(EmployeeListViewModel employeeListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EmployeeViewModel.class)
    protected abstract ViewModel employeeViewModel(EmployeeViewModel employeeViewModel);
}
