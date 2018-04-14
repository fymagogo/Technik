package ra.olympus.zeus.events.Models;

/**
 * Created by fawkes on 4/14/2018.
 */

public class Create {
    private String post_id;
    private String user_id;
    private String image;
    private String name;
    private String description;
    private String contact;
    private String category;
    private String date;
    private String time;

    public Create(String image, String name, String description, String contact, String category, String date, String time) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.category = category;
        this.date = date;
        this.time = time;
    }



    public String getPost_id() {
        return post_id;
    }

    public String getUser_id() {
        return user_id;
    }
}



