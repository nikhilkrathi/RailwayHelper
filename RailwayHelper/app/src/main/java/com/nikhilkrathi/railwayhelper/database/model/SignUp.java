package com.nikhilkrathi.railwayhelper.database.model;

public class SignUp {

    String fullName, emailID, password, reEnterPassword, mobile_no, city, gender;
    int age, ID;

    public SignUp(){

    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getEmailID(){
        return emailID;
    }

    public void setEmailID(String emailID){
        this.emailID = emailID;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getReEnterPassword(){
        return reEnterPassword;
    }

    public void setReEnterPassword(String reEnterPassword){
        this.reEnterPassword = reEnterPassword;
    }

    public String getMobile_no(){
        return mobile_no;
    }

    public void setMobile_no(String mobile_no){
        this.mobile_no = mobile_no;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }



}