package ra.olympus.zeus.events.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditEvent {

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
    @SerializedName("Username")
    @Expose
    private String Username;
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

    public EditEvent() {

    }

    public EditEvent(String eventName, long categoryId, String mainImage, String eventDate, String username, String description, String locationName, double latitude, double longitude) {
        EventName = eventName;
        CategoryId = categoryId;
        MainImage = mainImage;
        EventDate = eventDate;
        Username = username;
        Description = description;
        LocationName = locationName;
        Latitude = latitude;
        Longitude = longitude;
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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
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

    @Override
    public String toString() {
        return "EditEvent{" +
                "EventName:'" + EventName + '\'' +
                ", CategoryId:" + CategoryId +
                ", MainImage:'" + MainImage + '\'' +
                ", EventDate:'" + EventDate + '\'' +
                ", Username:'" + Username + '\'' +
                ", Description;'" + Description + '\'' +
                ", LocationName:'" + LocationName + '\'' +
                ", Latitude:" + Latitude +
                ", Longitude:" + Longitude +
                '}';
    }
}
