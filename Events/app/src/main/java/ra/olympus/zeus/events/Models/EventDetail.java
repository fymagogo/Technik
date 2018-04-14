package ra.olympus.zeus.events.Models;

/**
 * Created by fawkes on 4/14/2018.
 */

public class EventDetail {

    private String post_id;
    private String user_id;
    private String image;
    private String name;
    private String description;
    private String location;
    private String contact;
    private String date;
    private String time;
    private int interested;
    private int attending;

    public String getPost_id() {
        return post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getInterested() {
        return interested;
    }

    public int getAttending() {
        return attending;
    }
}
