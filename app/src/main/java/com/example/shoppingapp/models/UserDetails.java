package com.example.shoppingapp.models;

public class UserDetails {
    //instance members of the class
    private String name,userId,mobile,email,imageUrl;

    public UserDetails()
    {

    }
    public UserDetails(String name, String userId, String mobile, String email, String imageUrl) {
        this.name = name;
        this.userId = userId;
        this.mobile = mobile;
        this.email = email;
        this.imageUrl = imageUrl;
    }
    //getters=methods that are dedicated to the variables through which we can access
    // and retrieve data

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
