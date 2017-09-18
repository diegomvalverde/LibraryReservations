package users;

import fileServices.XMLFile;
import libraryServices.Incident;
import libraryServices.Reservation;

import java.util.ArrayList;

public class Student
{
    private static ArrayList<Student> students = new ArrayList<>();
    private String name;
    private int id;
    private int score;
    private int contact;
    private ArrayList<Incident> incidents = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private String email;
    private XMLFile xmlfile = new XMLFile();

    public static void loadStudents()
    {
        students = XMLFile.loadStudents();
    }

    public Student(String pName, int pId, String pEmail, int contact) {
        name = pName;
        this.id = pId;
        this.email = pEmail;
        score = 100;
        this.contact = contact;
        xmlfile.addStudent(this);
        addToStudents();
    }


    private void addToStudents()
    {
        if(!students.contains(this))
        {
            students.add(this);
        }
    }
    public Student(String pName, int pId, int pScore, String pEmail, int contact)
    {
        name = pName;
        id = pId;
        score = pScore;
        this.email = pEmail;
        this.contact = contact;
    }

    public void addIncident(String pSummary)
    {
        incidents.add(new Incident(pSummary));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setIncidents(ArrayList<Incident> incidents) {
        this.incidents = incidents;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Incident> getIncidents() {
        return incidents;
    }

    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", score=" + score +
                '}';
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static void setStudents(ArrayList<Student> students) {
        Student.students = students;
    }
}