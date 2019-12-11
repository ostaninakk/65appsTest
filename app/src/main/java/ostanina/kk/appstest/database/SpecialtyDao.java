package ostanina.kk.appstest.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import ostanina.kk.appstest.model.Specialty;

@Dao
public interface SpecialtyDao {
    @Insert
    void insertSpecialties(List<Specialty> specialties);

    @Query("SELECT * FROM specialties")
    LiveData<List<Specialty>> getSpecialties();

    @Query("DELETE FROM specialties")
    void deleteAllSpecialties();

    @Query("SELECT COUNT(*) FROM specialties")
    int getSpecialtiesCount();
}
