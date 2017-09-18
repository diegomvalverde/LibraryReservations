package libraryServices;

import fileServices.XMLFile;

import java.util.ArrayList;

public class Room
{
    private static int roomCounter = 1;
    private String id;
    private String location;
    private int capacitance;
    private String state;
    private int score;
    private String scoreCode;
    private XMLFile xmlFile = new XMLFile();
    private ArrayList<Resource> resources = new ArrayList<>();

    public Room(String location, int capacitance, String state) {
        xmlFile.getRoomsCounter();
        this.id = genrateId();
        this.location = location;
        this.capacitance = capacitance;
        this.state = state;
        this.score = 100;
        roomCounter++;
        xmlFile.addRoom(this);
    }

    public Room(String id, String location, int capacitance, String state, int score,
                ArrayList<Resource> resources) {
        this.id = id;
        this.location = location;
        this.capacitance = capacitance;
        this.state = state;
        this.score = score;
        this.resources = resources;
    }

    private String genrateId()
    {
        if(roomCounter < 10)
        {
            return "SAL-" + "00" + roomCounter;
        }else if(roomCounter < 100)
        {
            return "SAL-" + "0" + roomCounter;
        }else
        {
            return "SAL-" + roomCounter;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacitance() {
        return capacitance;
    }

    public void setCapacitance(int capacitance) {
        this.capacitance = capacitance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public XMLFile getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(XMLFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    public static int getRoomCounter() {
        return roomCounter;
    }

    public static void setRoomCounter(int roomCounter) {
        Room.roomCounter = roomCounter;
    }
}
