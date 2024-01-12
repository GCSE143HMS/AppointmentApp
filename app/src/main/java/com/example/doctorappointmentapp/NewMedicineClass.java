package com.example.doctorappointmentapp;

public class NewMedicineClass {
    private String medId;
    private String medicineName;
    private String qty;
    private String price;
    private String description;
    private String medType;

    public NewMedicineClass(String medId, String medicineName, String qty, String price, String description, String medType) {
        this.medId = medId;
        this.medicineName = medicineName;
        this.qty = qty;
        this.price = price;
        this.description = description;
        this.medType = medType;
    }

    public NewMedicineClass(){}

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }
}
