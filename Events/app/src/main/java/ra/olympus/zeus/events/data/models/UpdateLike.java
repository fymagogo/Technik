package ra.olympus.zeus.events.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLike {

    @SerializedName("Username")
    @Expose
    private String Username;

    public UpdateLike() {

    }

    public UpdateLike(String username) {
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
        return "UpdateLike{" +
                "Username='" + Username + '\'' +
                '}';
    }
}
