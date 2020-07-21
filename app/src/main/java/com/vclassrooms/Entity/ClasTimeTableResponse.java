package com.vclassrooms.Entity;

import java.util.List;

/**
 * Created by Rahul on 12,July,2020
 */
public class ClasTimeTableResponse {

    private int StatusCode;

    private String StatusMessaage;

    private Data data;

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public int getStatusCode() {
        return this.StatusCode;
    }

    public void setStatusMessaage(String StatusMessaage) {
        this.StatusMessaage = StatusMessaage;
    }

    public String getStatusMessaage() {
        return this.StatusMessaage;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }



    public class Data {
        private List<Timetable> timetable;

        public void setTimetable(List<Timetable> timetable) {
            this.timetable = timetable;
        }

        public List<Timetable> getTimetable() {
            return this.timetable;
        }
    }

}

