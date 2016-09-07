package com.ufo.widgetdemo.recyclerview.expandchecked;


import android.os.Parcelable;

import java.util.List;

/**
 * Created by tjpld on 16/8/26.
 */
public interface IParent extends Parcelable {

    List<? extends IChild> getChildrenList();

    void setChildrenList(List<? extends IChild> childrenList);

    void setChecked(boolean checked);

    boolean isChecked();

    String getDisplayName();

    void setDisplayName(String displayName);

    boolean isChildrenCheckedAll();


}
