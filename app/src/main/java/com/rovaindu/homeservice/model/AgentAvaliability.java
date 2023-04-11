package com.rovaindu.homeservice.model;

import java.io.Serializable;
import java.util.List;

public class AgentAvaliability implements Serializable {
    private int id;
    private String day;
    private String dayt;
    private List<DayAvaliabilty> dayAvaliabilties;


    public AgentAvaliability(int id, String day, String dayt, List<DayAvaliabilty> dayAvaliabilties) {
        this.id = id;
        this.day = day;
        this.dayt = dayt;
        this.dayAvaliabilties = dayAvaliabilties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayt() {
        return dayt;
    }

    public void setDayt(String dayt) {
        this.dayt = dayt;
    }

    public List<DayAvaliabilty> getDayAvaliabilties() {
        return dayAvaliabilties;
    }

    public void setDayAvaliabilties(List<DayAvaliabilty> dayAvaliabilties) {
        this.dayAvaliabilties = dayAvaliabilties;
    }

    public static class  DayAvaliabilty implements Serializable {

        private String date;
        private String startdate;
        private int statue;

        public DayAvaliabilty(String date, String startdate, int statue) {
            this.date = date;
            this.startdate = startdate;
            this.statue = statue;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public int getStatue() {
            return statue;
        }

        public void setStatue(int statue) {
            this.statue = statue;
        }
    }
}
