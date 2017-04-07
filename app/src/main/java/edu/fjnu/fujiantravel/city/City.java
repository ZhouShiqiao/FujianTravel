package edu.fjnu.fujiantravel.city;

/**
 * Created by ZhouShiqiao on 2017/4/6 0006.
 */

public class City {
    public final static int QUERYCITYLIST = 50;
    public final static int QUERYCITYLIST_SUCCESS = 500;
    public final static int QUERYCITYLIST_ERROR = 501;

    private String cid;
    private String name;
    private String parent;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
