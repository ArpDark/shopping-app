package com.example.shoppingapp.models;

import java.io.Serializable;

public class Place implements Serializable {
    private String name,picture,location,type;

    /*public Place(String p_name, String picture, String location, String type) {
        this.p_name = p_name;
        this.picture = picture;
        this.location = location;
        this.type = type;
    }
    public Place()
    {

    }*/

    public String getname() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }
}
