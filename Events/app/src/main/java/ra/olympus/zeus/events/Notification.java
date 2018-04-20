package ra.olympus.zeus.events;

public class Notification {
    private int imageID;
    private String notificationText;

    public Notification() {}


    public Notification(int imageID, String notificationText) {
        this.imageID = imageID;
        this.notificationText = notificationText;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }




}
