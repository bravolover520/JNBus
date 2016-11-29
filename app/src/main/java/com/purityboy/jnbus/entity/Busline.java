package com.purityboy.jnbus.entity;

import java.util.List;

/**
 * Created by John on 2016/11/22.
 * 线路
 */
public class Busline {

    /**
     * "id": "",
     * "area": 370100,
     * "lineName": "",
     * "startStationName": "",
     * "endStationName": "",
     * "stations": [],
     * "ticketPrice": "票价",
     * "operationTime": "运营时间",
     * "owner": "",
     * "linePoints":"",
     * "description": "",
     * "updateOwner": "sys-upgrade",
     * "updateTime": "Oct 22, 2016 09:12:29 PM",
     * "state": "0"
     */

    private String id;                  //线路编号
    private int area;                   //区域
    private String lineName;            //线路，例如106路
    private String startStationName;    //起始站
    private String endStationName;      //终点站
    private List<Station> stations;     //站点列表
    private String ticketPrice;         //票价
    private String operationTime;       //运营时间
    private String updateTime;          //更新时间
    private String state;               //使用状态

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

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
