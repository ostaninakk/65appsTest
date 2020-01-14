package ostanina.kk.appstest.ui.viewmodels;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import ostanina.kk.appstest.data.model.Employee;
import ostanina.kk.appstest.data.repositories.EmployeeRepository;

public class EmployeeListViewModel extends ViewModel {
    private EmployeeRepository repository;

    @Inject
    public EmployeeListViewModel(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    public LiveData<List<Employee>> getEmployeesForSpecialty(int specialtyId) {
        return repository.getEmployeesForSpecialty(specialtyId);
    }

}
