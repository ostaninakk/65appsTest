package ostanina.kk.appstest.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ostanina.kk.appstest.model.Employee;

public class EmployeeResponse {
    @SerializedName("response")
    @Expose
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }
}
