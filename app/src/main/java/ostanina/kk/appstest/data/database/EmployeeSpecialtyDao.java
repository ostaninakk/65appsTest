package ostanina.kk.appstest.data.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import ostanina.kk.appstest.data.model.Employee;
import ostanina.kk.appstest.data.model.Specialty;

@Dao
public interface EmployeeSpecialtyDao {
    @Query("SELECT employees.* FROM employees JOIN employee_specialty_cross_ref " +
            "ON employees.id = employee_specialty_cross_ref.employeeId " +
            "WHERE employee_specialty_cross_ref.specialtyId = :specialtyId")
    LiveData<List<Employee>> getEmployeesForSpecialty(int specialtyId);

    @Query("SELECT specialties.* FROM specialties JOIN employee_specialty_cross_ref " +
            "ON specialties.id = employee_specialty_cross_ref.specialtyId " +
            "WHERE employee_specialty_cross_ref.employeeId = :employeeId")
    LiveData<List<Specialty>> getSpecialtiesForEmployee(int employeeId);

}
