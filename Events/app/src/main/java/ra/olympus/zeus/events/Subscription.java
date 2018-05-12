package ra.olympus.zeus.events;

public class Subscription {
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    private int categoryID;
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
