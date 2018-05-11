package ra.olympus.zeus.events.data.remote;

import android.app.Application;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.util.EventLog;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Context mCtx;



    //creating an okHttp client
    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));



     static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://192.168.43.43:8000/")
            .addConverterFactory(GsonConverterFactory.create());


    static Retrofit  retrofit = builder.client(httpClient.build())
            .build();

    public static <S> S  createService  (Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
