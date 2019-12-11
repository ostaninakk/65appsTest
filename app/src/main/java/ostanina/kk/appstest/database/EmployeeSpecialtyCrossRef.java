package ostanina.kk.appstest.database;

import androidx.room.Entity;

@Entity(tableName = "employee_specialty_cross_ref", primaryKeys = {"employeeId", "specialtyId"})
public class EmployeeSpecialtyCrossRef {
    public int employeeId;
    public int specialtyId;

    public EmployeeSpecialtyCrossRef(int employeeId, int specialtyId) {
        this.employeeId = employeeId;
        this.specialtyId = specialtyId;
    }
}
