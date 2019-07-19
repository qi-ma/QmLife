/**
 * Copyright 2019 bejson.com
 */
package com.qm.qmlife.business.model.air;
import com.qm.qmlife.business.model.Basic;
import com.qm.qmlife.business.model.Update;

import java.util.List;

/**
 * Auto-generated: 2019-07-15 18:57:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class HeWeather6 {

    private Basic basic;
    private Update update;
    private String status;
    private Air_now_city air_now_city;
    private List<Air_now_station> air_now_station;
    public void setBasic(Basic basic) {
        this.basic = basic;
    }
    public Basic getBasic() {
        return basic;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
    public Update getUpdate() {
        return update;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setAir_now_city(Air_now_city air_now_city) {
        this.air_now_city = air_now_city;
    }
    public Air_now_city getAir_now_city() {
        return air_now_city;
    }

    public void setAir_now_station(List<Air_now_station> air_now_station) {
        this.air_now_station = air_now_station;
    }
    public List<Air_now_station> getAir_now_station() {
        return air_now_station;
    }

}