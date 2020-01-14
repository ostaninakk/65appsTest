package ostanina.kk.appstest.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ostanina.kk.appstest.data.requests.EmployeeApi;
import ostanina.kk.appstest.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static EmployeeApi provideEmployeeApi(Retrofit retrofit) {
        return retrofit.create(EmployeeApi.class);
    }
}
