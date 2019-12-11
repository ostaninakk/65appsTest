package ostanina.kk.appstest.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.repositories.EmployeeRepository;

public class EmployeeListViewModel extends AndroidViewModel {
    private EmployeeRepository repository;

    public EmployeeListViewModel(@NonNull Application application) {
        super(application);
        this.repository = EmployeeRepository.getInstance(application);
    }

    public LiveData<List<Employee>> getEmployeesForSpecialty(int specialtyId) {
        return repository.getEmployeesForSpecialty(specialtyId);
    }

}
