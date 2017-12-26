package com.nikhilkrathi.railwayhelper.database.model;

public class PassengerDetails {

    String name, gender;
    int age;

    public PassengerDetails(){

    }

    public PassengerDetails(String name, int age, String gender){
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){this.age = age;}

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

}
