package ostanina.kk.appstest.requests;

import ostanina.kk.appstest.requests.responses.EmployeeResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeApi {
    @GET("65gb/static/raw/master/testTask.json")
    Call<EmployeeResponse> getEmployeeList();
}
