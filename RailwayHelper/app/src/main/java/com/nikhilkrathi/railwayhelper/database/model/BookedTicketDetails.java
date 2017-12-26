package com.nikhilkrathi.railwayhelper.database.model;;

public class BookedTicketDetails{
    String pnr, date, emailID, passengerDetails;
    int trainNo;
    public BookedTicketDetails(){

    }

    public BookedTicketDetails(String pnr, String date, int trainNo, String emailID, String passengerDetails){
        this.pnr = pnr;
        this.date = date;
        this.trainNo = trainNo;
        this.emailID = emailID;
        this.passengerDetails = passengerDetails;

    }

    public String getPNR(){
        return pnr;
    }

    public void setPNR(String pnr){
        this.pnr = pnr;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public int getTrainNo(){
        return trainNo;
    }

    public void setTrainNo(int trainNo){
        this.trainNo = trainNo;
    }

    public String getEmailID(){
        return emailID;
    }

    public void setEmailID(String emailID){
        this.emailID = emailID;
    }

    public String getPassengerDetails(){
        return passengerDetails;
    }

    public void setPassengerDetails(String passengerDetails){
        this.passengerDetails = passengerDetails;
    }
}