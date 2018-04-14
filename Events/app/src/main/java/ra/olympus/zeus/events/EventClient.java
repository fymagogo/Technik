package ra.olympus.zeus.events;

import ra.olympus.zeus.events.Models.Create;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by fawkes on 4/14/2018.
 */

public interface EventClient {
    @POST("events")
    Call<Create> createEvent(@Body Create event);
}
