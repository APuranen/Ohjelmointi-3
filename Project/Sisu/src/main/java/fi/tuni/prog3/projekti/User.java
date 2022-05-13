/**
 * Ohjelmointi-3 Harjoitustyö: Sisu-projekti, User.
 * @author Antti-Jussi Isoviita, H283435
 * @author Aleksi Puranen, H283752
 */
package fi.tuni.prog3.projekti;

/**
 * Class for creating and saving student information
 */
public class User{
    public String studentName;
    public String studentNumber;
    public String studentDegree;

    /**
     * Initializes new user (student) object.
     * @param name Name of the student.
     * @param number Studentnumber of the student.
     * @param degree Degree of the student.
     */
    public User(String name, String number, String degree){
        this.studentName = name;
        this.studentNumber = number;
        this.studentDegree = degree;
    }

    /**
     * Returns stored student name.
     * @return Name of the student.
     */
    public String getName(){return studentName;}
    /**
     * Returns stored student number.
     * @return Studentnumber of the student.
     */
    public String getNumber(){return studentNumber;}
    /**
     * Returns stored student degree.
     * @return Degree of the student.
     */
    public String getDegree(){return studentDegree;}
}
