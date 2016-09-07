package com.ufo.widgetdemo.recyclerview.timeline;

/**
 * Created by tjpld on 16/8/1.
 */
public class TimeLineModel {

    private String name;
    private int age;


    public TimeLineModel(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

}
