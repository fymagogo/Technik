package ra.olympus.zeus.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("imageLink")
    @Expose
    private String imageLink;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("eventDate")
    @Expose
    private String eventDate;


    public Event(){}

    public Event(String imageLink, String eventName, String eventDate) {
        this.imageLink =imageLink;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public String getImageLink() { return imageLink; }

    public void setImageLink(String imageLink) { this.imageLink = imageLink; }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }


}





