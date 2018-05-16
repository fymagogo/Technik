package ra.olympus.zeus.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alfre on 04/04/2018.
 */

public class UserDetails {
    private Integer id;

    @SerializedName("FirstName")
    @Expose
    private String FirstName;

    @SerializedName("LastName")
    @Expose
    private String LastName;

    @SerializedName("UserName")
    @Expose
    private String UserName;

    @SerializedName("Email")
    @Expose
    private String Email;

    @SerializedName("PhoneNumber")
    @Expose
    private String PhoneNumber;

    @SerializedName("Password")
    @Expose
    private String Password;

    @SerializedName("ConfirmPassword")
    @Expose
    private String confirmpassword;

    public UserDetails() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
