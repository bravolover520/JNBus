package com.purityboy.jnbus.entity;

/**
 * Created by John on 2016/11/28.
 */

public class Bus {

    private String busId;
    private Double lng;
    private Double lat;
    private Double velocity;
    private String isArrvLft;       //?
    private int stationSeqNum;      //站点顺序（马上到哪一站）
    private String status;
    private String buslineId;
    private String actTime;
    private String cardId;          //车辆自编
    private String orgName;
    private Double averageVelocity;
    private int coordinate;

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public String getIsArrvLft() {
        return isArrvLft;
    }

    public void setIsArrvLft(String isArrvLft) {
        this.isArrvLft = isArrvLft;
    }

    public int getStationSeqNum() {
        return stationSeqNum;
    }

    public void setStationSeqNum(int stationSeqNum) {
        this.stationSeqNum = stationSeqNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuslineId() {
        return buslineId;
    }

    public void setBuslineId(String buslineId) {
        this.buslineId = buslineId;
    }

    public String getActTime() {
        return actTime;
    }

    public void setActTime(String actTime) {
        this.actTime = actTime;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Double getAverageVelocity() {
        return averageVelocity;
    }

    public void setAverageVelocity(Double averageVelocity) {
        this.averageVelocity = averageVelocity;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }
}
