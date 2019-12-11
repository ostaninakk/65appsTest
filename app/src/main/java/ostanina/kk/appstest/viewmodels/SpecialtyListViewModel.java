package ostanina.kk.appstest.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ostanina.kk.appstest.model.Specialty;
import ostanina.kk.appstest.repositories.EmployeeRepository;


public class SpecialtyListViewModel extends AndroidViewModel {
    private EmployeeRepository repository;

    public SpecialtyListViewModel(@NonNull Application application) {
        super(application);
        this.repository = EmployeeRepository.getInstance(application);
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
