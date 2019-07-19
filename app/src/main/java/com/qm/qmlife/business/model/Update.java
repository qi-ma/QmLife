
package com.qm.qmlife.business.model;
import java.util.Date;


public class Update {

    private Date loc;
    private Date utc;
    public void setLoc(Date loc) {
        this.loc = loc;
    }
    public Date getLoc() {
        return loc;
    }

    public void setUtc(Date utc) {
        this.utc = utc;
    }
    public Date getUtc() {
        return utc;
    }

}