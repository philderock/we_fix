package uk.ac.tees.b1498820.wefix.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    protected User(Parcel in) {
        Id = in.readString();
        Email = in.readString();
        FullName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public User(String id, String email, String fullName) {
        Id = id;
        Email = email;
        FullName = fullName;
    }

    public User() {
    }

    private String Id, Email, FullName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.Id);
        parcel.writeString(this.Email);
        parcel.writeString(this.FullName);
    }
}
