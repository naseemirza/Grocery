package solutions.thinkbiz.grocery.TopOffersPkg;

/**
 * Created by User on 12-Feb-19.
 */

public class TopOffersModel {

    private int mImageUrl;
    private String offtext;
    private String mName;
    private String mCurrency;
    private String mPrice;

    public TopOffersModel(int mImageUrl, String offtext, String mName, String mCurrency, String mPrice) {
        this.mImageUrl = mImageUrl;
        this.offtext = offtext;
        this.mName = mName;
        this.mCurrency = mCurrency;
        this.mPrice = mPrice;
    }

    public int getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(int mImageUrl) {
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
}
