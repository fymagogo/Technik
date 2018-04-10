package ra.olympus.zeus.events.Models;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by fawkes on 4/2/2018.
 */

public class PlaceInfo {

    private String name;
    private String phoneNumber;
    private String address;
    private String id;
    private String attributions;
    private Uri websiteUri;
    private LatLng latlng;


    public PlaceInfo(String name, String phoneNumber, String address, String id, String attributions, Uri websiteUri, LatLng latlng) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.id = id;
        this.attributions = attributions;
        this.websiteUri = websiteUri;
        this.latlng = latlng;
    }

    public PlaceInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttributions() {
        return attributions;
    }

    public void setAttributions(String attributions) {
        this.attributions = attributions;
    }

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", attributions='" + attributions + '\'' +
                ", websiteUri=" + websiteUri +
                ", latlng=" + latlng +
                '}';
    }
}
