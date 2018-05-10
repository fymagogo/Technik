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
    @SerializedName("contact")
    @Expose
    private Integer contact;
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


    public EventDetail(String eventName, Integer categoryId, String mainImage, String eventDate, String timeCreated, Integer locationId, Integer eventId, Integer contact, Integer interested, Integer attending, String description, String username, Double latitude, Double longitude, String locationName) {
        this.eventName = eventName;
        this.categoryId = categoryId;
        this.mainImage = mainImage;
        this.eventDate = eventDate;
        this.timeCreated = timeCreated;
        this.locationId = locationId;
        this.eventId = eventId;
        this.contact = contact;
        this.interested = interested;
        this.attending = attending;
        this.description = description;
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public Integer getInterested() {
        return interested;
    }

    public void setInterested(Integer interested) {
        this.interested = interested;
    }

    public Integer getAttending() {
        return attending;
    }

    public void setAttending(Integer attending) {
        this.attending = attending;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return "EventDetail{" +
                "eventName='" + eventName + '\'' +
                ", categoryId=" + categoryId +
                ", mainImage='" + mainImage + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", timeCreated='" + timeCreated + '\'' +
                ", locationId=" + locationId +
                ", eventId=" + eventId +
                ", contact=" + contact +
                ", interested=" + interested +
                ", attending=" + attending +
                ", description='" + description + '\'' +
                ", username='" + username + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
