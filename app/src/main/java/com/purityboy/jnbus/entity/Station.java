package com.purityboy.jnbus.entity;

/**
 * Created by John on 2016/11/22.
 * 站点
 */

public class Station {

    private String id;
    private int area;           //
    private String stationName; //站点名
    private double lng;         //站点的经度
    private double lat;         //站点的纬度

    public Station(String stationName) {
        this.stationName = stationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
