package ra.olympus.zeus.events;

public class Event {
    private int imageID;
    private String eventName;
    private String eventDate;


    public Event(){}

    public Event(int imageID, String eventName, String eventDate) {
        this.imageID = imageID;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

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





