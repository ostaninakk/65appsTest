package ostanina.kk.appstest.requests;

import ostanina.kk.appstest.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private Retrofit retrofit;
    private static NetworkService instance;

    private NetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public EmployeeApi getEmployeeApi() {
        return retrofit.create(EmployeeApi.class);
    }

}
