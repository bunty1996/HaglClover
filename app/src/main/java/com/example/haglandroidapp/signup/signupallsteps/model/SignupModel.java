
/**
 * Created by ${Raman} on ${30/11/2020}.
 */
package com.example.haglandroidapp.signup.signupallsteps.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SignupModel implements Parcelable, Serializable {
    String business_name;
    String owner_name;
    String business_phone;
    String cell_phone;
    String email;
    String address;
    String city;
    String state;
    String zipcode;
    String auth_token;
    String user_id;
    String merchant_id;
    String contact_name;
    String country;
    public SignupModel(){

    }
    public SignupModel(String contact_name, String business_name, String owner_name,
                       String business_phone, String cell_phone, String email, String address,
                       String city, String state, String zipcode, String auth_token, String user_id,
                       String merchant_id,String country) {
        this.contact_name = contact_name;
        this.business_name = business_name;
        this.owner_name = owner_name;
        this.business_phone = business_phone;
        this.cell_phone = cell_phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.auth_token = auth_token;
        this.user_id = user_id;
        this.merchant_id = merchant_id;
        this.country = country;

    }

    protected SignupModel(Parcel in) {
        contact_name = in.readString();
        business_name = in.readString();
        owner_name = in.readString();
        business_phone = in.readString();
        cell_phone = in.readString();
        email = in.readString();
        address = in.readString();
        city = in.readString();
        state = in.readString();
        zipcode = in.readString();
        auth_token = in.readString();
        user_id = in.readString();
        merchant_id = in.readString();
        country = in.readString();

    }

    public static final Creator<SignupModel> CREATOR = new Creator<SignupModel>() {
        @Override
        public SignupModel createFromParcel(Parcel in) {
            return new SignupModel(in);
        }

        @Override
        public SignupModel[] newArray(int size) {
            return new SignupModel[size];
        }
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getBusiness_phone() {
        return business_phone;
    }

    public void setBusiness_phone(String business_phone) {
        this.business_phone = business_phone;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    @Override
    public String toString() {
        return "SignupModel{" +
                "contact_name='" + contact_name + '\'' +
                "business_name='" + business_name + '\'' +
                ", owner_name='" + owner_name + '\'' +
                ", business_phone='" + business_phone + '\'' +
                ", cell_phone='" + cell_phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", auth_token='" + auth_token + '\'' +
                ", user_id='" + user_id + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", country='" + country + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(contact_name);
        parcel.writeString(business_name);
        parcel.writeString(owner_name);
        parcel.writeString(business_phone);
        parcel.writeString(cell_phone);
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(zipcode);
        parcel.writeString(auth_token);
        parcel.writeString(user_id);
        parcel.writeString(merchant_id);
        parcel.writeString(country);
    }
}
