
package com.example.haglandroidapp.Models.PostThresoldPriceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProductStore {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lonlat")
    @Expose
    private Object lonlat;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("retail_category")
    @Expose
    private Object retailCategory;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("store_qr_id")
    @Expose
    private String storeQrId;
    @SerializedName("store_qr_url")
    @Expose
    private String storeQrUrl;
    @SerializedName("qr_code_urls")
    @Expose
    private PostProductQrCodeUrls qrCodeUrls;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getLonlat() {
        return lonlat;
    }

    public void setLonlat(Object lonlat) {
        this.lonlat = lonlat;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getRetailCategory() {
        return retailCategory;
    }

    public void setRetailCategory(Object retailCategory) {
        this.retailCategory = retailCategory;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStoreQrId() {
        return storeQrId;
    }

    public void setStoreQrId(String storeQrId) {
        this.storeQrId = storeQrId;
    }

    public String getStoreQrUrl() {
        return storeQrUrl;
    }

    public void setStoreQrUrl(String storeQrUrl) {
        this.storeQrUrl = storeQrUrl;
    }

    public PostProductQrCodeUrls getQrCodeUrls() {
        return qrCodeUrls;
    }

    public void setQrCodeUrls(PostProductQrCodeUrls qrCodeUrls) {
        this.qrCodeUrls = qrCodeUrls;
    }

}
