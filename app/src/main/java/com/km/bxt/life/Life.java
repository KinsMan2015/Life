package com.km.bxt.life;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by xintong on 2015/7/6.
 */
public class Life implements Serializable {
    private UUID mUUID;
    private String mTime = "time"; // 记录的时间
    private int mBirthYear;
    private int mBirthMonth;
    private int mBirthDay;
    private int mDeathOld;
    /*****************/
    private String mOld;
    private int mYear;
    private int mMonth;
    private int mWeek;
    private int mDay;
    private int mHour;
    private int mMinute;
    /*******************/
    private int mDeath;
    private int mEat;
    private int mMakeLove;
    private int mWeekends;
    private int mHoliday;

    Life() {
    }

    public UUID getUUID() {
        return UUID.randomUUID();
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public int getDeathOld() {
        return mDeathOld;
    }

    public void setDeathOld(int deathOld) {
        mDeathOld = deathOld;
    }

    public int getBirthDay() {
        return mBirthDay;
    }

    public void setBirthDay(int birthDay) {
        mBirthDay = birthDay;
    }

    public int getBirthMonth() {
        return mBirthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        mBirthMonth = birthMonth;
    }

    public int getBirthYear() {
        return mBirthYear;
    }

    public void setBirthYear(int birthYear) {
        mBirthYear = birthYear;
    }

    public String getOld() {
        return mOld;
    }

    public void setOld(String old) {
        mOld = old;
    }

    public int getHoliday() {
        return mHoliday;
    }

    public void setHoliday(int holiday) {
        mHoliday = holiday;
    }

    public int getWeekends() {
        return mWeekends;
    }

    public void setWeekends(int weekends) {
        mWeekends = weekends;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getMakeLove() {
        return mMakeLove;
    }

    public void setMakeLove(int makeLove) {
        mMakeLove = makeLove;
    }

    public int getEat() {
        return mEat;
    }

    public void setEat(int eat) {
        mEat = eat;
    }

    public int getDeath() {
        return mDeath;
    }

    public void setDeath(int death) {
        mDeath = death;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public int getWeek() {
        return mWeek;
    }

    public void setWeek(int week) {
        mWeek = week;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }
}
