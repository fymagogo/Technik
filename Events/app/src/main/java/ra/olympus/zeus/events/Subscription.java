package ra.olympus.zeus.events;

public class Subscription {
    private int imageID;
    private String categoryName;


    public Subscription(){}
    public Subscription(int imageID, String categoryName) {
        this.imageID = imageID;
        this.categoryName = categoryName;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
