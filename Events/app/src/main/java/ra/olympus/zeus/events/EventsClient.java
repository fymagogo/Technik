package ra.olympus.zeus.events;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventsClient {
    @GET("events")
    Call<List<Event>>getAllEvents();

    @GET("myEvents/{username}")
    Call<List<Event>>getMyEvents(@Path("username") String username);

    @GET("myinterested/{username}")
    Call<List<Event>>getInterestedEvents(@Path("username") String username);

    @GET("myattending/{username}")
    Call<List<Event>>getAttendingEvents(@Path("username") String username);

    @GET("events/filter/{categoryID}")
    Call<List<Event>> getEventsByCategory(@Path("categoryID") int categoryID);
//
//    @POST("settings/addCategory/{username}/{categoryName}")
//    Call<Subscription> addSubscription(@Body Subscription subscription);

    //@POST("events/:eventId/like")
    //Call<Subscription> addLike(@Body Subscription subscription);


}
