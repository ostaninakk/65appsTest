package ostanina.kk.appstest.data.repositories;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ostanina.kk.appstest.data.database.EmployeeDao;
import ostanina.kk.appstest.data.database.EmployeeDatabase;
import ostanina.kk.appstest.data.database.InsertEmployeeSpecialtyDao;
import ostanina.kk.appstest.data.database.EmployeeSpecialtyCrossRef;
import ostanina.kk.appstest.data.database.EmployeeSpecialtyDao;
import ostanina.kk.appstest.data.database.SpecialtyDao;
import ostanina.kk.appstest.data.model.Employee;
import ostanina.kk.appstest.data.model.Specialty;
import ostanina.kk.appstest.data.requests.EmployeeApi;
import ostanina.kk.appstest.data.requests.responses.EmployeeResponse;
import ostanina.kk.appstest.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class EmployeeRepository {
    private static final String TAG = "EmployeeRepository";
    private EmployeeDao employeeDao;
    private SpecialtyDao specialtyDao;
    private EmployeeSpecialtyDao employeeSpecialtyDao;
    private InsertEmployeeSpecialtyDao insertEmployeeSpecialtyDao;
    private EmployeeApi dataApi;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private MutableLiveData<Boolean> errorRequest = new MutableLiveData<>();

    public EmployeeRepository(
            SpecialtyDao specialtyDao,
            EmployeeDao employeeDao,
            EmployeeSpecialtyDao employeeSpecialtyDao,
            InsertEmployeeSpecialtyDao insertEmployeeSpecialtyDao,
            EmployeeApi employeeApi) {

        this.specialtyDao = specialtyDao;
        this.employeeDao = employeeDao;
        this.employeeSpecialtyDao = employeeSpecialtyDao;
        this.insertEmployeeSpecialtyDao = insertEmployeeSpecialtyDao;
        dataApi = employeeApi;

        // Загрузить данные по работникам по сети
        loadData();
    }

    //public static EmployeeRepository getInstance(Context context) {
    //    if (instance == null) {
    //        instance = new EmployeeRepository(context);
    //    }
    //    return instance;
    //}

    public LiveData<List<Specialty>> getSpecialties() {
        return specialtyDao.getSpecialties();
    }

    public LiveData<List<Employee>> getEmployeesForSpecialty(int specialtyId) {
        return employeeSpecialtyDao.getEmployeesForSpecialty(specialtyId);
    }

    public LiveData<Employee> getEmployee(int employeeId) {
        return employeeDao.getEmployee(employeeId);
    }

    public LiveData<List<Specialty>> getSpecialtiesForEmployee(int employeeId) {
        return employeeSpecialtyDao.getSpecialtiesForEmployee(employeeId);
    }

    public void loadData() {
        //EmployeeApi dataApi = NetworkService.getInstance().getEmployeeApi();
        Call<EmployeeResponse> request = dataApi.getEmployeeList();
        errorRequest.setValue(false);
        request.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                if (response.code() == 200) {
                    List<Employee> employees = response.body().getEmployees();
                    putData(employees);
                } else {
                    // Если данных в локальной базе нет, то оповестить пользователя
                    if (specialtyDao.getSpecialtiesCount() == 0) {
                        errorRequest.postValue(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Log.e(TAG, "Failed get data.", t);
                /* Если не удалось загрузить данные, то проверить
                 * есть ли данные в локальной базе данных. Если данных нет, то
                 * оповестить пользователя, иначе продолжить работать с локальной базой
                 */
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (specialtyDao.getSpecialtiesCount() == 0) {
                            errorRequest.postValue(true);
                        }
                    }
                });
            }
        });
    }

    private void putData(final List<Employee> employees) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                if (employees != null) {
                    /* Отдельно получить массив Работников, массив Специальностей
                     * и массив связей работника и специальности. Полученные данные
                     * вставить в локальную базу
                     */


                    SparseArray<Specialty> specialtiesSparseArray = new SparseArray<>();
                    List<EmployeeSpecialtyCrossRef> employeeSpecialtyCrossRef = new ArrayList<>();
                    int index = 0;
                    for (Employee employee : employees) {
                        employee.setId(index);
                        index++;
                        for (Specialty specialty : employee.getSpecialty()) {
                            employeeSpecialtyCrossRef.add(
                                    new EmployeeSpecialtyCrossRef(
                                            employee.getId(),
                                            specialty.getId()));
                            specialtiesSparseArray.put(specialty.getId(), specialty);
                        }
                    }
                    List<Specialty> specialties = Utils.asList(specialtiesSparseArray);

                    // Очистить базу и вставить все данные
                    insertEmployeeSpecialtyDao.deleteAndInsertInTransaction(
                            specialties,
                            employees,
                            employeeSpecialtyCrossRef);

                }
            }

        });
    }


    public LiveData<Boolean> checkError() {
        return errorRequest;
    }

}
