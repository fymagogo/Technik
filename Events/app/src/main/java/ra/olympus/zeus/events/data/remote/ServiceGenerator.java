package ra.olympus.zeus.events.data.remote;

import android.app.Application;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.EventLog;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Context mCtx;


    private static class ResponseCachingInterceptor implements Interceptor {

        @Override

        public Response intercept(Chain chain) throws IOException {

            Response response = chain.proceed(chain.request());
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Access-Control-Allow-Origin")
                    .removeHeader("Vary")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=300")
                    .build();
        }
    }


    private static class OfflineResponseCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            NetworkInfo networkInfo =((ConnectivityManager)

                    (mCtx.getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();
            if (networkInfo==null) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Access-Control-Allow-Origin")
                        .removeHeader("Vary")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control",
                                "public, only-if-cached, max-stale= 604800")
                        .build();
            }
            return chain.proceed(request);
        }
    }



    //creating an okHttp client
    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            /*.addNetworkInterceptor(new ResponseCachingInterceptor())
            .addInterceptor(new OfflineResponseCacheInterceptor())*/
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            /*.cache(new Cache(new File(mCtx.getCacheDir(),
                    "apiResponses"), 10 * 1024 * 1024))*/;



     static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://192.168.43.43:8000/")
            .addConverterFactory(GsonConverterFactory.create());


    static Retrofit  retrofit = builder.client(httpClient.build())
            .build();

    public static <S> S  createService  (Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
