public class Time {
    private int hour;
    private int min;
    private int day;
    private int month;
    private int year;

    public void setTime(int hour, int min, int day, int month, int year) {
        this.hour = hour;
        this.min = min;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
