package com.ufo.widgetdemo;

/**
 * Created by tjpld on 16/9/1.
 */
public class DataModel {

    private String title;
    private String subhead;
    private String activtyName;

    public DataModel(String title, String subhead,String activtyName) {
        this.title = title;
        this.subhead = subhead;
        this.activtyName = activtyName;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivtyName() {
        return activtyName;
    }

    public void setActivtyName(String activtyName) {
        this.activtyName = activtyName;
    }

}
