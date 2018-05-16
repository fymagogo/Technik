package ra.olympus.zeus.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alfre on 05/04/2018.
 */

public class UserSignIn {
    //Class for passing user sign in data
    private Integer Id;

    @SerializedName("Username")
    @Expose
    private String Username;

    @SerializedName("Password")
    @Expose
    private String Password;

    public UserSignIn() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getId() {
        return Id;
    }
}
