package ra.olympus.zeus.events.data.remote;

import java.util.List;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.data.models.CreateEvent;
import ra.olympus.zeus.events.data.models.EventDetail;
import ra.olympus.zeus.events.data.models.Update;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventHubClient {
    @POST("create-event")
    Call<ResponseBody> creatingEvent (@Body CreateEvent createEvent );

    @GET("events/{eventid}")
    Call<List<EventDetail>> gettingEvent(@Path("eventid") int id);

    @POST("events/{eventid}/like")
    Call<ResponseBody> liking(@Path("eventid") int id, @Body Update like);

    @PUT("events/{eventid}/unlike")
    Call<ResponseBody> unliking(@Path("eventid") int id, @Body Update unlike);

    @POST("events/{eventid}/like")
    Call<ResponseBody> attending(@Path("eventid") int id, @Body Update attend);

    @PUT("events/{eventid}/unlike")
    Call<ResponseBody> unattending(@Path("eventid") int id, @Body Update unattend);




}
