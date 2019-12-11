package ostanina.kk.appstest.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.model.Specialty;
import ostanina.kk.appstest.repositories.EmployeeRepository;

public class EmployeeViewModel extends AndroidViewModel {
    private EmployeeRepository repository;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        this.repository = EmployeeRepository.getInstance(application);
    }

    public LiveData<Employee> getEmployee(int employeeId) {
        return repository.getEmployee(employeeId);
    }

    public LiveData<List<Specialty>> getSpecialtiesForEmployee(int employeeId) {
        return repository.getSpecialtiesForEmployee(employeeId);
    }



}

