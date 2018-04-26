package ra.olympus.zeus.events;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alfre on 04/04/2018.
 */

public interface EventHubClient {
    @POST("signup")
    Call<ResponseBody> sendUserDetails (@Body UserDetails user);

    @POST("login")
    Call<ResponseBody> sendSignInDetails (@Body UserSignIn user);

    @GET("event")
    Call<List<EventSearchClass>> getSearchResults (@Body String searchQuery);




}
