package ostanina.kk.appstest.repositories;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ostanina.kk.appstest.database.EmployeeDao;
import ostanina.kk.appstest.database.EmployeeDatabase;
import ostanina.kk.appstest.database.EmployeeSpecialtyCrossRef;
import ostanina.kk.appstest.database.EmployeeSpecialtyDao;
import ostanina.kk.appstest.database.SpecialtyDao;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.model.Specialty;
import ostanina.kk.appstest.requests.EmployeeApi;
import ostanina.kk.appstest.requests.NetworkService;
import ostanina.kk.appstest.requests.responses.EmployeeResponse;
import ostanina.kk.appstest.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {
    private static final String TAG = "EmployeeRepository";
    private static EmployeeRepository instance;
    private EmployeeDao employeeDao;
    private SpecialtyDao specialtyDao;
    private EmployeeSpecialtyDao employeeSpecialtyDao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private MutableLiveData<Boolean> errorRequest = new MutableLiveData<>();

    private EmployeeRepository(Context context) {
        EmployeeDatabase database = EmployeeDatabase.getInstance(context);
        employeeDao = database.employeeDao();
        specialtyDao = database.specialtyDao();
        employeeSpecialtyDao = database.employeeSpecialtyCrossRefDao();

        // Загрузить данные по работникам по сети
        loadData();
    }

    public static EmployeeRepository getInstance(Context context) {
        if (instance == null) {
            instance = new EmployeeRepository(context);
        }
        return instance;
    }

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
        EmployeeApi dataApi = NetworkService.getInstance().getEmployeeApi();
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
                // Очистить данные в базе
                deleteAllData();

                if (employees != null) {
                    /* Отдельно получить массив Работников, массив Специальностей
                     * и массив связей работника и специальности. Полученнные данные
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

                    employeeDao.insertEmployees(employees);
                    specialtyDao.insertSpecialties(specialties);
                    employeeSpecialtyDao.insertAllEmployeeSpecialtyCrossRef(employeeSpecialtyCrossRef);
                }
            }
        });
    }


    private void deleteAllData() {
        employeeSpecialtyDao.deleteAllEmployeeSpecialtyCrossRef();
        employeeDao.deleteAllEmployees();
        specialtyDao.deleteAllSpecialties();
    }


    public LiveData<Boolean> checkError() {
        return errorRequest;
    }

}
