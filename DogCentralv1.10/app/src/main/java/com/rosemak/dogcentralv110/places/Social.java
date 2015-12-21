package com.rosemak.dogcentralv110.places;

import java.io.Serializable;

/**
 * Created by stevierose on 12/19/15.
 */
public class Social implements Serializable {

    public int mDay;
    public int mMonth;
    public int mYear;

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }
}
