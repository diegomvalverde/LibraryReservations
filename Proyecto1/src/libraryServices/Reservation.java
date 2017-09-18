package libraryServices;

import fileServices.XMLFile;

public class Reservation
{
    private String subject;
    private String state;
    private int day;
    private String month;
    private double beginTime;
    private double endTime;
    private int organizer;
    private XMLFile xmlFile = new XMLFile();
    private String room;

    public Reservation(String subject, int day, String month, double beginTime, double endTime, int organizer, String room) {
        this.subject = subject;
        this.day = day;
        this.month = month;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.room = room;
        xmlFile.addReservation(this);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(double beginTime) {
        this.beginTime = beginTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public int getOrganizer() {
        return organizer;
    }

    public void setOrganizer(int organizer) {
        this.organizer = organizer;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "subject='" + subject + '\'' +
                ", state='" + state + '\'' +
                ", day=" + day +
                ", month='" + month + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", organizer=" + organizer +
                ", room='" + room + '\'' +
                '}';
    }
}
