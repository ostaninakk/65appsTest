package ostanina.kk.appstest.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import ostanina.kk.appstest.data.model.Employee;
import ostanina.kk.appstest.data.model.Specialty;

@Dao
public abstract class InsertEmployeeSpecialtyDao {

    @Insert
    abstract void insertEmployees(List<Employee> employees);

    @Insert
     abstract void insertSpecialties(List<Specialty> specialties);

    @Insert
    abstract void insertAllEmployeeSpecialtyCrossRef(List<EmployeeSpecialtyCrossRef> items);

    @Query("DELETE FROM employees")
    abstract void deleteAllEmployees();

    @Query("DELETE FROM specialties")
    abstract void deleteAllSpecialties();

    @Query("DELETE FROM employee_specialty_cross_ref")
    abstract void deleteAllEmployeeSpecialtyCrossRef();

    @Transaction
    public void deleteAndInsertInTransaction(List<Specialty> specialties, List<Employee> employees,
                                             List<EmployeeSpecialtyCrossRef> employeeSpecialtyCrossRefs) {
        deleteAllEmployeeSpecialtyCrossRef();
        deleteAllEmployees();
        deleteAllSpecialties();

        insertSpecialties(specialties);
        insertEmployees(employees);
        insertAllEmployeeSpecialtyCrossRef(employeeSpecialtyCrossRefs);

    }
}
