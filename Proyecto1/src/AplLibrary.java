

import libraryServices.*;
import users.Student;

public class AplLibrary
{
    public static void main(String Args[])
    {
        Student.loadStudents();
        Student student = new Student("Tec", 2016078675, "tec@tec.tec", 22785018);
        Room room = new Room("Primer piso", 36, "Activa");
        Reservation reservation = new Reservation("Estudiar", 2, "january", 7.30, 13.30, student.getId(), room.getId());

        for (Student tmp : Student.getStudents())
        {
            for (Reservation tmp2: tmp.getReservations())
            {
                System.out.println(tmp2.toString());
            }
        }

//        Student student = new Student("Diego Méndez", 2016078675, "diegomvalverde@outlook.com", 64475306);
//        System.out.println(student.getId());

//        Room room = new Room("Segundo piso", 10, "Desocupada", 100);
//        XMLFile xmlfile = new XMLFile();
//        Reservation tmp = new Reservation("Estudiar",2,"august",7.30,13.30, student,
//                room);
//        xmlfile.addReservation(tmp);

    }
}
