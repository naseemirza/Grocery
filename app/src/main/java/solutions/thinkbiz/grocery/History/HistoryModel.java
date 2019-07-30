package solutions.thinkbiz.grocery.History;

/**
 * Created by User on 16-Apr-19.
 */

public class HistoryModel {

    private String id;
    private String mImageUrl;
    private String mName;
    private String mCurrency;
    private String mPrice;
    private String qty;
    String image_path = "https://demotbs.com/dev/grocery//assets/uploads/product/";

    public HistoryModel(String id, String mImageUrl, String mName, String mCurrency, String mPrice,String qty) {
        this.id = id;
        this.mImageUrl = image_path+mImageUrl;
        this.mName = mName;
        this.mCurrency = mCurrency;
        this.mPrice = mPrice;
        this.qty = qty;
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

    public String getmCurrency() {
        return mCurrency;
    }

    public void setmCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
