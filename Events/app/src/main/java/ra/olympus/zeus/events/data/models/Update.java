package ra.olympus.zeus.events.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Update {

    @SerializedName("UserName")
    @Expose
    private String Username;

    public Update() {

    }

    public Update(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    @Override
    public String toString() {
        return "Update{" +
                "Username='" + Username + '\'' +
                '}';
    }
}
