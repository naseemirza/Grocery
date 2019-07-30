package solutions.thinkbiz.grocery.Checkout;

/**
 * Created by User on 29-Mar-19.
 */

public class OrderModel {

    private String Pid;
    private String cartId;
    private String image;
    private String name;
    private String currency;
    private String totalprice;
    private String price;
    private String quantity;
    String image_path = "https://demotbs.com/dev/grocery//assets/uploads/product/";

    public OrderModel(String pid, String cartId, String image, String name, String currency, String totalprice, String price, String quantity) {
        this.Pid = pid;
        this.cartId = cartId;
        this.image = image_path+image;
        this.name = name;
        this.currency = currency;
        this.totalprice = totalprice;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getTotalprice() {
        return Double.parseDouble(totalprice);
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
