package ra.olympus.zeus.events.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {




    //creating an okHttp client
    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();




     static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create());

    static Retrofit  retrofit = builder.client(httpClient.build())
            .build();

    public static <S> S  createService  (Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
