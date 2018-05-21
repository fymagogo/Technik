package ra.olympus.zeus.events.data.remote;

import java.util.List;

import okhttp3.ResponseBody;
import ra.olympus.zeus.events.UserDetails;
import ra.olympus.zeus.events.UserSignIn;
import ra.olympus.zeus.events.data.models.ChangePassword;
import ra.olympus.zeus.events.data.models.CreateEvent;
import ra.olympus.zeus.events.data.models.EditEvent;
import ra.olympus.zeus.events.data.models.EventDetail;
import ra.olympus.zeus.events.data.models.Update;
import ra.olympus.zeus.events.data.models.UserProfileDetails;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ra.olympus.zeus.events.Search.Recycler.Implement.EventSearchClass;


public interface EventHubClient {
    @POST("create-event")
    Call<ResponseBody> creatingEvent (@Body CreateEvent createEvent );

    @GET("events/{eventid}")
    Call<List<EventDetail>> gettingEvent(@Path("eventid") int id);

    @POST("events/{eventid}/like")
    Call<ResponseBody> liking(@Path("eventid") int id, @Body Update like);

    @PUT("events/{eventid}/unlike")
    Call<ResponseBody> unliking(@Path("eventid") int id, @Body Update unlike);

    @PUT("events/{eventid}/attend")
    Call<ResponseBody> attending(@Path("eventid") int id, @Body Update attend);

    @PUT("events/{eventid}/unattend")
    Call<ResponseBody> unattending(@Path("eventid") int id, @Body Update unattend);

    @POST("signup")
    Call<ResponseBody> sendUserDetails (@Body UserDetails user);

    @POST("login")
    Call<ResponseBody> sendSignInDetails (@Body UserSignIn user);

    @GET("search/")
    Call <List<EventSearchClass>> getSearchResults ( @Query("search_query") String name, @Query("category_filter") int id);

    @PUT("settings/change-password/{username}")
    Call<ResponseBody> changepassword (@Path("username") String username, @Body ChangePassword change);

    @GET("get-user-details/{username}")
    Call<List<UserDetails>> getUserDetails (@Path("username") String username);

    @PUT("settings/edit-profile/{username}")
    Call<ResponseBody> sendProfileDetails (@Path("username") String username, @Body UserProfileDetails userprofile);

    @PUT("my-event/{username}/{eventid}/edit")
    Call<ResponseBody> editingEvent (@Path("username") String username,@Path("eventid") int id,@Body EditEvent editEvent );

    @DELETE("my-events/{username}/{event-id}/delete")
    Call<ResponseBody> deleteEvent (@Path("username") String username, @Path("event-id") int id);

    @DELETE("settings/delete-account/{username}")
    Call<ResponseBody> deleteAccount (@Path("username") String username);







}