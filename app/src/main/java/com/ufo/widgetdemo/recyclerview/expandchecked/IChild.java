package com.ufo.widgetdemo.recyclerview.expandchecked;

import android.os.Parcelable;

/**
 * Created by tjpld on 16/8/26.
 */
public interface IChild extends Parcelable {
    void setChecked(boolean checked);

    boolean isChecked();

    String getDisplayName();

    void setDisplayName(String displayName);
}
