package ostanina.kk.appstest.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ostanina.kk.appstest.model.Employee;
import ostanina.kk.appstest.model.Specialty;
import ostanina.kk.appstest.utils.Constants;

@Database(entities = {Employee.class, Specialty.class, EmployeeSpecialtyCrossRef.class}, version = 1,
        exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {

    private static EmployeeDatabase instance;

    public static EmployeeDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    EmployeeDatabase.class,
                    Constants.DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract EmployeeDao employeeDao();
    public abstract SpecialtyDao specialtyDao();
    public abstract EmployeeSpecialtyDao employeeSpecialtyCrossRefDao();
}
