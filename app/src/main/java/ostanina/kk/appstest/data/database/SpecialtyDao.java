package ostanina.kk.appstest.data.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import ostanina.kk.appstest.data.model.Specialty;

@Dao
public interface SpecialtyDao {

    @Query("SELECT * FROM specialties")
    LiveData<List<Specialty>> getSpecialties();

    @Query("SELECT COUNT(*) FROM specialties")
    int getSpecialtiesCount();
}
