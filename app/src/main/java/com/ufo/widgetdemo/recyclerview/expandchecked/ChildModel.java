package com.ufo.widgetdemo.recyclerview.expandchecked;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tjpld on 16/8/26.
 */
public class ChildModel implements IChild {

    private String displayName;

    private boolean isChecked;

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    public static final Parcelable.Creator<ChildModel> CREATOR = new Parcelable.Creator<ChildModel>() {
        @Override
        public ChildModel createFromParcel(Parcel source) {
            ChildModel model = new ChildModel();
            model.setDisplayName(source.readString());
            model.setChecked(source.readByte() != 0);
            return model;
        }

        @Override
        public ChildModel[] newArray(int size) {
            return new ChildModel[size];
        }
    };

}
