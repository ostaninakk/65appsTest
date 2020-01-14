package ostanina.kk.appstest.ui.viewmodels;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import ostanina.kk.appstest.data.model.Employee;
import ostanina.kk.appstest.data.model.Specialty;
import ostanina.kk.appstest.data.repositories.EmployeeRepository;

public class EmployeeViewModel extends ViewModel {
    private EmployeeRepository repository;

    @Inject
    public EmployeeViewModel(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    public LiveData<Employee> getEmployee(int employeeId) {
        return repository.getEmployee(employeeId);
    }

    public LiveData<List<Specialty>> getSpecialtiesForEmployee(int employeeId) {
        return repository.getSpecialtiesForEmployee(employeeId);
    }



}

