package ra.olympus.zeus.events;

import ra.olympus.zeus.events.Models.EventDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fawkes on 4/14/2018.
 */

public interface EventDetailClient {


    @GET("/events")
    Call<EventDetail> getEvent(@Query("id") String PostID);
}
