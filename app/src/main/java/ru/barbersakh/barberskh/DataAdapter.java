package ru.barbersakh.barberskh;

/**
 * Created by Juned on 2/8/2017.
 */

public class DataAdapter {
    private String id;
    public String ImageURL;
    public String ImageTitle;
    public String ACompany;
    public String IdSpecialist;
    public String FIOSpecialist;
    public String PhotoSpecialist;

    public String getIdSpecialist() {
        return IdSpecialist;
    }

    public void setIdSpecialist(String idSpecialist) {
        this.IdSpecialist = idSpecialist;
    }

    public String getFIOSpecialist() {
        return FIOSpecialist;
    }

    public void setFIOSpecialist(String fioSpecialist) {
        this.FIOSpecialist = fioSpecialist;
    }

    public String getPhotoSpecialist() {
        return PhotoSpecialist;
    }

    public void setPhotoSpecialist(String photoSpecialist) {
        this.PhotoSpecialist = photoSpecialist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {

        return ImageURL;
    }

    public void setImageUrl(String imageServerUrl) {

        this.ImageURL = imageServerUrl;
    }

    public String getImageTitle() {

        return ImageTitle;
    }

    public void setImageTitle(String Imagetitlename) {

        this.ImageTitle = Imagetitlename;
    }

    public String getAddressCompany() {
        return ACompany;
    }

    public void setAddressCompany(String addressCompany) {
        this.ACompany = addressCompany;
    }
}