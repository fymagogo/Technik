package ra.olympus.zeus.events.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDetail {
    @SerializedName("EventName")
    @Expose
    private String eventName;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("MainImage")
    @Expose
    private String mainImage;
    @SerializedName("EventDate")
    @Expose
    private String eventDate;
    @SerializedName("TimeCreated")
    @Expose
    private String timeCreated;
    @SerializedName("LocationId")
    @Expose
    private Integer locationId;
    @SerializedName("EventId")
    @Expose
    private Integer eventId;
    @SerializedName("Interested")
    @Expose
    private Integer interested;
    @SerializedName("Attending")
    @Expose
    private Integer attending;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("LocationName")
    @Expose
    private String locationName;

    public EventDetail() {
    }

    public String getEventName() {
        return eventName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getMainImage() {
        return mainImage;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public Integer getInterested() {
        return interested;
    }

    public Integer getAttending() {
        return attending;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLocationName() {
        return locationName;
    }
}
