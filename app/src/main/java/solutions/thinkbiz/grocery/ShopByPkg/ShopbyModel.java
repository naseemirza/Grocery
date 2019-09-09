package solutions.thinkbiz.grocery.ShopByPkg;

/**
 * Created by User on 12-Feb-19.
 */

public class ShopbyModel {

    private String id;
    private String mImageUrl;
    private String mName;
    //String image_path = "https://demotbs.com/dev/grocery//assets/uploads/category/";
    String image_path ="http://memorstoreonline.com/assets/uploads/category/";

    public ShopbyModel(String id, String mImageUrl, String mName) {
        this.id = id;
        this.mImageUrl =image_path+mImageUrl;
        this.mName = mName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
