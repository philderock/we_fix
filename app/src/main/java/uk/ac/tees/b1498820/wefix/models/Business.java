package uk.ac.tees.b1498820.wefix.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Business implements Parcelable {
    private String UserId, BusinessName, Description, Address, ServicesTag, Category, PhoneNumber;
    private Double Longtitude, Latitude;

    public Business(String userId, String businessName, String description,
                    String address, String servicesTag, String category,
                    Double longtitude, Double latitude, String phoneNumber) {
        UserId = userId;
        BusinessName = businessName;
        Description = description;
        Address = address;
        ServicesTag = servicesTag;
        Category = category;
        Longtitude = longtitude;
        Latitude = latitude;
        PhoneNumber = phoneNumber;
    }
    public Business(){}


    protected Business(Parcel in) {
        UserId = in.readString();
        BusinessName = in.readString();
        Description = in.readString();
        Address = in.readString();
        ServicesTag = in.readString();
        Category = in.readString();
        PhoneNumber = in.readString();
        if (in.readByte() == 0) {
            Longtitude = null;
        } else {
            Longtitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            Latitude = null;
        } else {
            Latitude = in.readDouble();
        }

    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        @Override
        public Business createFromParcel(Parcel in) {
            return new Business(in);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getServicesTag() {
        return ServicesTag;
    }

    public void setServicesTag(String servicesTag) {
        ServicesTag = servicesTag;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Double getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(Double longtitude) {
        Longtitude = longtitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.UserId);
        parcel.writeString(this.BusinessName);
        parcel.writeString(this.Description);
        parcel.writeString(this.Address);
        parcel.writeString(this.ServicesTag);
        parcel.writeString(this.Category);
        parcel.writeString(this.PhoneNumber);
        parcel.writeDouble(this.Longtitude);
        parcel.writeDouble(this.Latitude);

    }
}

