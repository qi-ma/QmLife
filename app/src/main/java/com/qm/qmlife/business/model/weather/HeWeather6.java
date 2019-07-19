
package com.qm.qmlife.business.model.weather;

import com.qm.qmlife.business.model.Basic;
import com.qm.qmlife.business.model.Update;

public class HeWeather6 {

    private Basic basic;
    private Update update;
    private String status;
    private Now now;
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

    public void setNow(Now now) {
        this.now = now;
    }
    public Now getNow() {
        return now;
    }

}