package solutions.thinkbiz.grocery.History;

/**
 * Created by User on 17-Apr-19.
 */

public class HistoryListModel {

    private String id;
    private String Uid;
    private String itemnumber;
    private String totalamount;
    private String date;
    private String status;

    public HistoryListModel(String id,String Uid, String itemnumber, String totalamount, String date, String status) {
        this.id = id;
        this.Uid = Uid;
        this.itemnumber = itemnumber;
        this.totalamount = totalamount;
        this.date = date;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public void setItemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
