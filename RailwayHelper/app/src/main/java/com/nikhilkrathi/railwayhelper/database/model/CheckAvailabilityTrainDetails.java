package com.nikhilkrathi.railwayhelper.database.model;

public class CheckAvailabilityTrainDetails {

    String trainName, sourceID, destinationID, arrivalTime, departureTime, trainType, date;
    int trainID;

    public CheckAvailabilityTrainDetails(){

    }

    public CheckAvailabilityTrainDetails(String trainName, int trainID, String sourceID, String destinationID, String arrivalTime, String departureTime, String trainType, String date ){
        this.trainName = trainName;
        this.trainID = trainID;
        this.sourceID = sourceID;
        this.destinationID = destinationID;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.trainType = trainType;
        this.date = date;
    }

    public String getTrainName(){
        return trainName;
    }

    public void setTrainName(String trainName){
        this.trainName = trainName;
    }

    public String getSourceID(){
        return sourceID;
    }

    public void setSourceID(String sourceID){
        this.sourceID = sourceID;
    }

    public String getDestinationID(){
        return destinationID;
    }

    public void setDestinationID(String destinationID){
        this.destinationID = destinationID;
    }

    public String getTrainType(){
        return trainType;
    }

    public void setTrainType(String trainType){
        this.trainType = trainType;
    }

    public String getArrivalTime(){
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime(){
        return departureTime;
    }

    public void setDepartureTime(String departureTime){
        this.departureTime = departureTime;
    }

    public int getTrainID(){
        return trainID;
    }

    public void setTrainID(int trainID){
        this.trainID = trainID;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

}