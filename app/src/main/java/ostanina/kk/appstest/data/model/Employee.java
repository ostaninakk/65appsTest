package ostanina.kk.appstest.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "employees")
public class Employee {
    @PrimaryKey
    private int id;

    @SerializedName("f_name")
    @ColumnInfo(name = "first_name")
    private String firstName;

    @SerializedName("l_name")
    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "birthday")
    private String birthday;

    @Ignore
    private List<Specialty> specialty;

    public Employee(String firstName, String lastName, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public List<Specialty> getSpecialty() {
        return specialty;
    }
}
