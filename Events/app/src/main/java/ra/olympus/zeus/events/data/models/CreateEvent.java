package ra.olympus.zeus.events.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateEvent {
    @SerializedName("EventName")
    @Expose
    private String EventName;
    @SerializedName("CategoryId")
    @Expose
    private long CategoryId;
    @SerializedName("MainImage")
    @Expose
    private String MainImage;
    @SerializedName("EventDate")
    @Expose
    private String EventDate;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("LocationName")
    @Expose
    private String LocationName;
    @SerializedName("Latitude")
    @Expose
    private double Latitude;
    @SerializedName("Longitude")
    @Expose
    private double Longitude;

    public CreateEvent() {

    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(long categoryId) {
        CategoryId = categoryId;
    }

    public String getMainImage() {
        return MainImage;
    }

    public void setMainImage(String mainImage) {
        MainImage = mainImage;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
