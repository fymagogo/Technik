package ra.olympus.zeus.events.Search.Recycler.Implement;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfre on 21/04/2018.
 */

public class EventSearchClass {

    @SerializedName("eventname")
    @Expose
    private String eventname;
    @SerializedName("eventdate")
    @Expose
    private String eventdate;
    @SerializedName("Mainimage")
    @Expose
    private String mainimage;

    @Expose
    @SerializedName("eventid")
    private int eventid;

    public EventSearchClass(String eventname, String eventdate, String mainimage) {
        this.eventname = eventname;
        this.eventdate = eventdate;
        this.mainimage = mainimage;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getMainimage() {
        return mainimage;
    }

    public void setMainimage(String mainimage) {
        this.mainimage = mainimage;
    }

    public void setEventid(int eventid) { this.eventid = eventid; }

    public int getEventid(){return eventid; }
}
