package com.example.security_project;

public class Profile {

    String pnumber,name;

    public Profile(String pnumber, String name) {
        this.pnumber = pnumber;
        this.name = name;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profile() {
    }
}
