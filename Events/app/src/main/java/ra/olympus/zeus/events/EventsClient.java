package ra.olympus.zeus.events;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EventsClient {
    @GET("events")
    Call<List<Event>>gettingAllEvents();

}
