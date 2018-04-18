package ra.olympus.zeus.events.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

     HttpLoggingInterceptor logging = new HttpLoggingInterceptor();


    //creating an okHttp client
     OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    httpClient.addInterceptor(logging);

     Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create());

     Retrofit  retrofit = builder.client(httpClient.build())
            .build();

    public  <S> S  createService  (Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
