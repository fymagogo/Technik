package ra.olympus.zeus.events.data.remote;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.data.models.CreateEvent;
import ra.olympus.zeus.events.data.models.EventDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EventHubClient {
    @POST("createEvent")
    Call<CreateEvent> creatingEvent (@Body CreateEvent createEvent );




}
