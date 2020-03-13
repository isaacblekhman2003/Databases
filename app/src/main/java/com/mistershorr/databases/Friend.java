package com.mistershorr.databases;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {
    @Override
    public String toString() {
        return "Friend{" +
                "clumsiness=" + clumsiness +
                ", gymFrequency=" + gymFrequency +
                ", moneyOwed=" + moneyOwed +
                ", name='" + name + '\'' +
                ", trustworthiness=" + trustworthiness +
                '}';
    }

    public int getClumsiness() {
        return clumsiness;
    }

    public void setClumsiness(int clumsiness) {
        this.clumsiness = clumsiness;
    }

    public double getGymFrequency() {
        return gymFrequency;
    }

    public void setGymFrequency(double gymFrequency) {
        this.gymFrequency = gymFrequency;
    }

    public double getMoneyOwed() {
        return moneyOwed;
    }

    public void setMoneyOwed(double moneyOwed) {
        this.moneyOwed = moneyOwed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrustworthiness() {
        return trustworthiness;
    }

    public void setTrustworthiness(int trustworthiness) {
        this.trustworthiness = trustworthiness;
    }

    private int clumsiness;
    private double gymFrequency;
    private double moneyOwed;
    private String name;
    private int trustworthiness;
    private boolean awesomeness;
    private String ownerId;
    private String objectId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isAwesomeness() {
        return awesomeness;
    }

    public void setAwesomeness(boolean awesomeness) {
        this.awesomeness = awesomeness;
    }

    public Friend(int clumsiness, double gymFrequency, double moneyOwed, String name, int trustworthiness,boolean awesomeness) {
        this.clumsiness = clumsiness;
        this.gymFrequency = gymFrequency;
        this.moneyOwed = moneyOwed;
        this.name = name;
        this.trustworthiness = trustworthiness;
        this.awesomeness=awesomeness;
    }

    public Friend(){


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.clumsiness);
        dest.writeDouble(this.gymFrequency);
        dest.writeDouble(this.moneyOwed);
        dest.writeString(this.name);
        dest.writeInt(this.trustworthiness);
        dest.writeByte(this.awesomeness ? (byte) 1 : (byte) 0);
        dest.writeString(this.ownerId);
        dest.writeString(this.objectId);
    }

    protected Friend(Parcel in) {
        this.clumsiness = in.readInt();
        this.gymFrequency = in.readDouble();
        this.moneyOwed = in.readDouble();
        this.name = in.readString();
        this.trustworthiness = in.readInt();
        this.awesomeness = in.readByte() != 0;
        this.ownerId = in.readString();
        this.objectId = in.readString();
    }

    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
