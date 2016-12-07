package com.purityboy.jnbus.utils;

/**
 * Created by John on 2016/12/6.
 * 计算经纬度的距离
 */

public class EarthDistance {

    private static double EARTH_RADIUS = 6378137;   //单位米

    /**
     * 根据两点的经纬度计算距离
     * @param lat1  位置1的纬度
     * @param lng1  位置1的经度
     * @param lat2  位置2的纬度
     * @param lng2  位置2的经度
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double difference2 = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(difference2 / 2), 2)));
        distance = distance * EARTH_RADIUS;
        return distance;
    }


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
