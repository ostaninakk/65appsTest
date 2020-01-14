package ostanina.kk.appstest.di.module;

import android.app.Application;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ostanina.kk.appstest.data.database.EmployeeDao;
import ostanina.kk.appstest.data.database.EmployeeDatabase;
import ostanina.kk.appstest.data.database.EmployeeSpecialtyDao;
import ostanina.kk.appstest.data.database.InsertEmployeeSpecialtyDao;
import ostanina.kk.appstest.data.database.SpecialtyDao;
import ostanina.kk.appstest.data.repositories.EmployeeRepository;
import ostanina.kk.appstest.data.requests.EmployeeApi;
import ostanina.kk.appstest.utils.Constants;

@Module
public class DbModule {

    @Provides
    @Singleton
    static EmployeeDatabase provideEmployeeDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                EmployeeDatabase.class,
                Constants.DATABASE_NAME).build();
    }


    @Provides
    @Singleton
    static SpecialtyDao provideSpecialtyDao(EmployeeDatabase database) {
        return database.specialtyDao();
    }

    @Provides
    @Singleton
    static EmployeeDao provideEmployeeDao(EmployeeDatabase database) {
        return database.employeeDao();
    }

    @Provides
    @Singleton
    static EmployeeSpecialtyDao provideEmployeeSpecialtyDao(EmployeeDatabase database) {
        return database.employeeSpecialtyCrossRefDao();
    }

    @Provides
    @Singleton
    static InsertEmployeeSpecialtyDao provideInsertEmployeeSpecialtyDao(EmployeeDatabase database) {
        return database.insertEmployeeSpecialtyDao();
    }

    @Provides
    @Singleton
    static EmployeeRepository provideEmployeeRepository(
            SpecialtyDao specialtyDao,
            EmployeeDao employeeDao,
            EmployeeSpecialtyDao employeeSpecialtyDao,
            InsertEmployeeSpecialtyDao insertEmployeeSpecialtyDao,
            EmployeeApi employeeApi) {
        return new EmployeeRepository(specialtyDao, employeeDao, employeeSpecialtyDao,
                insertEmployeeSpecialtyDao, employeeApi);
    }

}
