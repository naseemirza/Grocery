package solutions.thinkbiz.grocery.Tabs.DealsoftheDayPkg;

/**
 * Created by User on 14-Feb-19.
 */

public class DealsModel {

    private String id;
    private String mImageUrl;
    private String offtext;
    private String mName;
    private String mCurrency;
    private String mPrice;
    private String descr;
    String image_path = "http://demotbs.com/dev/grocery//assets/uploads/product/";

    public DealsModel(String id, String mImageUrl, String offtext, String mName, String mCurrency, String mPrice, String descr) {
        this.id = id;
        this.mImageUrl = image_path+mImageUrl;
        this.offtext = offtext;
        this.mName = mName;
        this.mCurrency = mCurrency;
        this.mPrice = mPrice;
        this.descr = descr;
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

    public String getOfftext() {
        return offtext;
    }

    public void setOfftext(String offtext) {
        this.offtext = offtext;
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
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
