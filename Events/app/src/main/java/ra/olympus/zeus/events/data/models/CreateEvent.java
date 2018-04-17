package ra.olympus.zeus.events.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Create {
    @SerializedName("EventName")
    @Expose
    private String EventName;
    @SerializedName("CategoryId")
    @Expose
    private String CategoryId;
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
    private String Latitude;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
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

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "Create{" +
                "EventName='" + EventName + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", MainImage='" + MainImage + '\'' +
                ", EventDate='" + EventDate + '\'' +
                ", Description='" + Description + '\'' +
                ", LocationName='" + LocationName + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", Longitude='" + Longitude + '\'' +
                '}';
    }
}
