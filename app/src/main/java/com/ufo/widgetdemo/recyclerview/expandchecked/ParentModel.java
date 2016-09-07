package com.ufo.widgetdemo.recyclerview.expandchecked;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjpld on 16/8/26.
 */
public class ParentModel implements IParent {

    private String displayName;
    private boolean isChecked;

    private List<ChildModel> childrenList;

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public List<ChildModel> getChildrenList() {
        return childrenList;
    }

    @Override
    public void setChildrenList(List<? extends IChild> childrenList) {
        this.childrenList = (List<ChildModel>) childrenList;
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
    public boolean isChildrenCheckedAll() {
        if (childrenList.size() > 0) {
            boolean ck = true;
            for (IChild child : childrenList) {
                ck = child.isChecked() & ck;
                if (!ck) break;
            }
            return ck;
        } else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeTypedList(childrenList);
    }

    public static final Parcelable.Creator<ParentModel> CREATOR = new Parcelable.Creator<ParentModel>() {
        @Override
        public ParentModel createFromParcel(Parcel source) {
            ParentModel model = new ParentModel();
            model.setDisplayName(source.readString());
            model.setChecked(source.readByte() != 0);
            List<ChildModel> list = new ArrayList<>();
            source.readTypedList(list, ChildModel.CREATOR);

            model.setChildrenList(list);
            return model;
        }

        @Override
        public ParentModel[] newArray(int size) {
            return new ParentModel[size];
        }
    };
}
