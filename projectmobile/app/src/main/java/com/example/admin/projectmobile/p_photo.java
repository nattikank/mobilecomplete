package com.example.admin.projectmobile;

public class p_photo {
    private String prograp;
    private String namegrap;
    private String picgrap;

    public String getPrograp() {
        return prograp;
    }

    public void setPrograp(String prograp) {
        this.prograp = prograp;
    }

    public p_photo(String prograp, String namegrap, String picgrap, int numpic) {
        this.prograp = prograp;
        this.namegrap = namegrap;
        this.picgrap = picgrap;
        this.numpic = numpic;
    }

    public String getNamegrap() {

        return namegrap;
    }

    public void setNamegrap(String namegrap) {
        this.namegrap = namegrap;
    }

    public String getPicgrap() {
        return picgrap;
    }

    public void setPicgrap(String picgrap) {
        this.picgrap = picgrap;
    }

    public int getNumpic() {
        return numpic;
    }

    public void setNumpic(int numpic) {
        this.numpic = numpic;
    }

    private int numpic;
}
