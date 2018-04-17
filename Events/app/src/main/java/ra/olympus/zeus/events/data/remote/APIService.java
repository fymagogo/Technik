package ra.olympus.zeus.events.data.remote;

import ra.olympus.zeus.events.data.models.CreateEvent;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("/createEvent")
    @FormUrlEncoded
    Call<CreateEvent> createPost(@Field("EventName") String EventName,
                                 @Field("CategoryId") long CategoryId,
                                 @Field("MainImage") String MainImage,
                                 /*@Field("EventDate") String EventDate,*/
                                 @Field("Description") String Description,
                                 @Field("LocationName") String LocationName,
                                 @Field("Latitude") double Latitude,
                                 @Field("Longitude") double Longitude);

}
