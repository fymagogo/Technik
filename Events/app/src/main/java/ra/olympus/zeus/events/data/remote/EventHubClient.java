package ra.olympus.zeus.events.data.remote;

import org.json.JSONArray;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.data.models.CreateEvent;
import ra.olympus.zeus.events.data.models.EventDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventHubClient {
    @POST("create-event")
    Call<ResponseBody> creatingEvent (@Body CreateEvent createEvent );

    @GET("events/{eventid}")
    Call<JSONArray> gettingEvent(@Path("eventid") int id);




}
