package ostanina.kk.appstest.ui.viewmodels;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import ostanina.kk.appstest.data.model.Specialty;
import ostanina.kk.appstest.data.repositories.EmployeeRepository;


public class SpecialtyListViewModel extends ViewModel {
    private EmployeeRepository repository;

    @Inject
    public SpecialtyListViewModel(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    public LiveData<List<Specialty>> getSpecialties() {
        return repository.getSpecialties();
    }

    public LiveData<Boolean> checkError() {
        return repository.checkError();
    }

    public void loadData() {
        repository.loadData();
    }

}
