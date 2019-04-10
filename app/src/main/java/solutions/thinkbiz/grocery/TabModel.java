package solutions.thinkbiz.grocery;

/**
 * Created by User on 08-Apr-19.
 */

public class TabModel {


    private String id;
    private String mName;

    public TabModel(String id, String mName) {
        this.id = id;
        this.mName = mName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
