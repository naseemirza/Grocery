package solutions.thinkbiz.grocery.ShopByPkg;

/**
 * Created by User on 12-Feb-19.
 */

public class ShopbyModel {

    private int Imagepath;
    private String Name;

    public ShopbyModel(int imagepath, String name) {
        Imagepath = imagepath;
        Name = name;
    }

    public int getImagepath() {
        return Imagepath;
    }

    public void setImagepath(int imagepath) {
        Imagepath = imagepath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
