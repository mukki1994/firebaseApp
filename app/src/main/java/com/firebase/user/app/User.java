package com.firebase.user.app;

public class User {

    private String id;
    private String name;
    private long phone_number_value;



    public User(){}

    public User(String id, String name, long phone_number_value) {
        this.id = id;
        this.name = name;
        this.phone_number_value = phone_number_value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone_number_value() {
        return phone_number_value;
    }

    public void setPhone_number_value(long phone_number_value) {
        this.phone_number_value = phone_number_value;
    }
}
