package fileServices;

import libraryServices.Reservation;
import libraryServices.Resource;
import libraryServices.Room;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import users.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class XMLFile
{
    private static final String scheduleXMLPath = "resources/horariosDB.xml";
    private static final String studentsXMLPath = "resources/estudiantesDB.xml";
    private static final String roomsXMLPath = "resources/salasDB.xml";
    private static final String  reservationsXMLPath = "resources/reservasDB.xml";


    public static ArrayList<Student> loadStudents()
    {
        ArrayList<Student> tmp = new ArrayList<>();
        try {
            File fXmlFile = new File(studentsXMLPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("student");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                int id = Integer.parseInt(eElement.getAttribute("id"));
                String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                int score = Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent());
                String email = eElement.getElementsByTagName("email").item(0).getTextContent();
                int contact = Integer.parseInt(eElement.getElementsByTagName("contact").item(0).getTextContent());
                tmp.add(new Student(name, id, score, email, contact));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadStudentsReservations(tmp);
        return tmp;
    }

    private static void loadStudentsReservations(ArrayList<Student> pStudents)
    {
        for (Student student: pStudents)
        {
            ArrayList<Reservation> reservations = new ArrayList<>();
            try {
                File fXmlFile = new File(reservationsXMLPath);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("reservation");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);
                    Element eElement = (Element) nNode;
                    int organizer = Integer.parseInt(eElement.getElementsByTagName("organizer").item(0)
                            .getTextContent());
                    if(organizer == student.getId()) {
                        String subject = eElement.getElementsByTagName("subject").item(0).getTextContent();
                        int day = Integer.parseInt(eElement.getElementsByTagName("day").item(0).getTextContent());
                        String month = eElement.getElementsByTagName("month").item(0).getTextContent();
                        Double start = Double.parseDouble(eElement.getElementsByTagName("start").item(0).getTextContent());
                        Double end = Double.parseDouble(eElement.getElementsByTagName("end").item(0).getTextContent());
                        String room = eElement.getElementsByTagName("room").item(0).getTextContent();
                        reservations.add(new Reservation(subject,day,month,start,end, student.getId(), room));
                    }
                }
                student.setReservations(reservations);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param pId The student's id to see if the student exists on xml file
     * @return The fuction retun true if the student exists.
     */
    private boolean xmlHasStudent(int pId)
    {
        try {
            File fXmlFile = new File(studentsXMLPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("student");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                if (Objects.equals(eElement.getAttribute("id"), Integer.toString(pId))) {
                    return true;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     *
     * @param pStudent This object allows the xml writter to save all the student info in the xml
     */
    public void addStudent(Student pStudent) {

        int score = pStudent.getScore();
        int id = pStudent.getId();
        String name = pStudent.getName();

        if(!xmlHasStudent(id)) {
//            System.out.println(xmlHasStudent(id));

            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(new File(studentsXMLPath));

                Element root = document.getDocumentElement();

                Element student = document.createElement("student");
                student.setAttribute("id", Integer.toString(id));


                Element elementName = document.createElement("name");
                Element elementScore = document.createElement("score");
                Element elementContact = document.createElement("contact");
                Element elementEmail = document.createElement("email");

                elementName.appendChild(document.createTextNode(name));
                elementEmail.appendChild(document.createTextNode(pStudent.getEmail()));
                elementContact.appendChild(document.createTextNode(Integer.toString(pStudent.getContact())));
                elementScore.appendChild(document.createTextNode(Integer.toString(score)));

                student.appendChild(elementName);
                student.appendChild(elementScore);
                student.appendChild(elementContact);
                student.appendChild(elementEmail);

                root.appendChild(student);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(studentsXMLPath));

                transformer.transform(source, result);

            } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean xmlHasRoom(String pId)
    {
        try {
            File fXmlFile = new File(roomsXMLPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("room");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                if (Objects.equals(eElement.getAttribute("id"), pId)) {
                    return true;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     *
     * @param pId Student's id to browse the student onthe xml and delete it
     */
    public void removeStudent(int pId) {

        if (xmlHasStudent(pId)) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(new File(studentsXMLPath));

                Element root = document.getDocumentElement();
                document.getDocumentElement().normalize();

                NodeList nList = document.getElementsByTagName("student");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);
                    Element eElement = (Element) nNode;

                    if (Objects.equals(eElement.getAttribute("id"), Integer.toString(pId))) {
                        root.removeChild(nNode);
                    }

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(studentsXMLPath));

                    transformer.transform(source, result);
                }
            } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @param pRoom The room will be added to the xml file
     */
    public void addRoom(Room pRoom)
    {
        if(!xmlHasRoom(pRoom.getId())) {
            try {
                addSchedule(pRoom.getId());
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(new File(roomsXMLPath));
                Element root = document.getDocumentElement();
                NodeList nList = document.getElementsByTagName("counter");
                Node nNode = nList.item(0);
                Element elementCounter = (Element) nNode;
                if(elementCounter != null)
                {
                    root.removeChild(elementCounter);
                }

                Element counter = document.createElement("counter");
                counter.setAttribute("id", Integer.toString(Room.getRoomCounter()));

                Element room = document.createElement("room");
                room.setAttribute("id", pRoom.getId());

                Element elementLocation = document.createElement("lacation");
                elementLocation.appendChild(document.createTextNode(pRoom.getLocation()));

                Element elementCapacity = document.createElement("capacity");
                elementCapacity.appendChild(document.createTextNode(Integer.toString(pRoom.getCapacitance())));

                Element elementState = document.createElement("state");
                elementState.appendChild(document.createTextNode(pRoom.getState()));

                Element elementScore = document.createElement("score");
                elementScore.appendChild(document.createTextNode(Integer.toString(pRoom.getScore())));

                Element elementResources = document.createElement("resources");
                if (!pRoom.getResources().isEmpty()) {
                    for (Resource tmp : pRoom.getResources()) {
                        Element elementType = document.createElement("type");
                        elementType.appendChild(document.createTextNode(tmp.getSummary()));
                        elementResources.appendChild(elementType);
                    }
                } else {
                    Element elementType = document.createElement("type");
                    elementType.appendChild(document.createTextNode("No hay recursos"));
                    elementResources.appendChild(elementType);
                }
                room.appendChild(elementLocation);
                room.appendChild(elementCapacity);
                room.appendChild(elementState);
                room.appendChild(elementScore);
                room.appendChild(elementResources);

                root.appendChild(counter);
                root.appendChild(room);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(roomsXMLPath));

                transformer.transform(source, result);

            } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
                e.printStackTrace();
            }
        }

    }

    public void getRoomsCounter()
    {
        try {
            File fXmlFile = new File(roomsXMLPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("counter");
            Node nNode = nList.item(0);
            Element elementCounter = (Element) nNode;
            if(elementCounter != null) {
                Room.setRoomCounter(Integer.parseInt(elementCounter.getAttribute("id")));
            }else
            {
                Room.setRoomCounter(1);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void removeRoom(String pId)
    {
        if (xmlHasRoom(pId)) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(new File(roomsXMLPath));

                Element root = document.getDocumentElement();
                document.getDocumentElement().normalize();

                NodeList nList = document.getElementsByTagName("room");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);
                    Element eElement = (Element) nNode;

                    if (Objects.equals(eElement.getAttribute("id"), pId)) {
                        root.removeChild(nNode);
                    }

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(roomsXMLPath));

                    transformer.transform(source, result);
                }
            } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }

//    public Student loadStudent(int pId)
//    {
//        try {
//            File fXmlFile = new File(studentsXMLPath);
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(fXmlFile);
//            doc.getDocumentElement().normalize();
//
//            NodeList nList = doc.getElementsByTagName("student");
//
//            for (int temp = 0; temp < nList.getLength(); temp++) {
//
//                Node nNode = nList.item(temp);
//                Element eElement = (Element) nNode;
//
//                if (Objects.equals(eElement.getAttribute("id"), Integer.toString(pId))) {
//                    int id = Integer.parseInt(eElement.getAttribute("id"));
//                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
//                    int score = Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent());
//                    return new Student(name, id, score, "");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private Room loadRoom(String pId)
    {
        try {
            File fXmlFile = new File(roomsXMLPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("room");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;

                if (Objects.equals(eElement.getAttribute("id"), pId)) {
                    String id = eElement.getAttribute("id");
                    String location = eElement.getElementsByTagName("location").item(0).getTextContent();
                    int capatity = Integer.parseInt(eElement.getElementsByTagName("capacity").item(0).getTextContent());
                    String state = eElement.getElementsByTagName("state").item(0).getTextContent();
                    int score = Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent());
                    ArrayList<Resource> resources = new ArrayList<>();

                    NodeList nTypes = eElement.getElementsByTagName("resources");
                    for (int i = 0; i < nTypes.getLength(); i++)
                    {
//                        System.out.println(eElement.getElementsByTagName("type").item(0).getTextContent());
                        resources.add(new Resource(eElement.getElementsByTagName("type").item(0).getTextContent()));
                    }

                    return new Room(id, location, capatity, state, score, resources);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadSchedule(String pSchedule)
    {
        return "";
    }

    private boolean xmlHasReservation(String pId)
    {
        try {
            File fXmlFile = new File(reservationsXMLPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("reservation");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                if (Objects.equals(eElement.getAttribute("id"), pId)) {
                    return true;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void addReservation(Reservation pReservation)
    {
        if(!xmlHasReservation(pReservation.getOrganizer()+ "/" + pReservation.getRoom() +
                "/" + pReservation.getDay() + "/" + pReservation.getMonth() + "/" + pReservation.getBeginTime())) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(new File(reservationsXMLPath));
                Element root = document.getDocumentElement();


                Element elementReservation = document.createElement("reservation");
                elementReservation.setAttribute("id", pReservation.getOrganizer() + "/" + pReservation.getRoom() +
                        "/" + pReservation.getDay() + "/" + pReservation.getMonth() + "/" + pReservation.getBeginTime());

                Element elementSubject = document.createElement("subject");
                elementSubject.appendChild(document.createTextNode(pReservation.getSubject()));

                Element elementState = document.createElement("state");
                elementState.appendChild(document.createTextNode("A espera de confirmación"));

                Element elementRoom = document.createElement("room");
                elementRoom.appendChild(document.createTextNode(pReservation.getRoom()));

                Element elementDay = document.createElement("day");
                elementDay.appendChild(document.createTextNode(Integer.toString(pReservation.getDay())));

                Element elementMonth = document.createElement("month");
                elementMonth.appendChild(document.createTextNode(pReservation.getMonth()));

                Element elementStart = document.createElement("start");
                elementStart.appendChild(document.createTextNode(Double.toString(pReservation.getBeginTime())));

                Element elementEnd = document.createElement("end");
                elementEnd.appendChild(document.createTextNode(Double.toString(pReservation.getEndTime())));

                Element elementOrganizer = document.createElement("organizer");
                elementOrganizer.appendChild(document.createTextNode(Integer.toString(pReservation.getOrganizer())));

                elementReservation.appendChild(elementSubject);
                elementReservation.appendChild(elementState);
                elementReservation.appendChild(elementDay);
                elementReservation.appendChild(elementMonth);
                elementReservation.appendChild(elementStart);
                elementReservation.appendChild(elementEnd);
                elementReservation.appendChild(elementOrganizer);
                elementReservation.appendChild(elementRoom);

                root.appendChild(elementReservation);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(reservationsXMLPath));

                transformer.transform(source, result);

            } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean xmlHasSchedule(String pId)
    {
        try {
        File fXmlFile = new File(scheduleXMLPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("room");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            if (Objects.equals(eElement.getAttribute("id"), pId)) {
                return true;
            }
        }
    } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
    }
        return false;
    }


    private void addSchedule(String pId)
    {
//        System.out.println("Estoy vivo");
        if(!xmlHasSchedule(pId)) try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(scheduleXMLPath));

            Element root = document.getDocumentElement();

            Element room = document.createElement("room");
            room.setAttribute("id", pId);

            Element elementMonth = null;

            for (int i = 1; i < 13; i++) {
                Calendar time = Calendar.getInstance();
                time.set(time.get(Calendar.YEAR), i, 0);
                int days = time.getActualMaximum(Calendar.DAY_OF_MONTH);
                elementMonth = document.createElement(IConstants.months[i-1]);
//                System.out.println(days);

                for (int e = 1; e < days + 1; e++) {
                    Element elementDay = document.createElement("day");
                    elementDay.setAttribute("id", Integer.toString(e));

                    Element elementStart = document.createElement("start");
                    elementStart.appendChild(document.createTextNode(Double.toString(7.30)));

                    Element elementEnd = document.createElement("end");
                    elementEnd.appendChild(document.createTextNode(Double.toString(20.30)));

                    Element elementeReservation = document.createElement("reservation");
                    elementeReservation.appendChild(document.createTextNode("No hay reservaciones"));

                    elementDay.appendChild(elementStart);
                    elementDay.appendChild(elementEnd);
                    elementDay.appendChild(elementeReservation);
                    elementMonth.appendChild(elementDay);
                }
                room.appendChild(elementMonth);
            }

            root.appendChild(room);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(scheduleXMLPath));

            transformer.transform(source, result);

        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void removeReservation(String pReservation)
    {

    }

    private String loadReservation()
    {
        return "";
    }
}
