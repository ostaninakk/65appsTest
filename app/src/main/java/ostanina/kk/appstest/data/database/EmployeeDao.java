package ostanina.kk.appstest.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import ostanina.kk.appstest.data.model.Employee;

@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM employees where id = :employeeId")
    LiveData<Employee> getEmployee(int employeeId);
}
