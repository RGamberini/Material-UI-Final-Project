package sample;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nick on 12/17/2015.
 */
public class Student extends Person {
    private RudeProperty gpa, major, numCredits;
    public ObservableList<RudeObject> coursesTaken, coursesCurrentlyTaken, coursesToBeTakenToGraduate;
    public Student() {
        super();
        extraLists = new HashMap<>();
        extraListClass = Course.class;

        this.gpa = new RudeProperty();
        this.propertyMap.put("GPA", gpa);

        this.major = new RudeProperty();
        this.propertyMap.put("Major", major);

        this.numCredits = new RudeProperty();
        this.numCredits.setReadOnly(true);
        this.propertyMap.put("Credits Taking", numCredits);

        coursesTaken = FXCollections.observableArrayList();
        extraLists.put("Courses Taken", coursesTaken);

        coursesCurrentlyTaken = FXCollections.observableArrayList();
        extraLists.put("Current Courses", coursesCurrentlyTaken);
        coursesCurrentlyTaken.addListener((ListChangeListener<RudeObject>) c -> setNumCredits(getNumCredits()));

        coursesToBeTakenToGraduate = FXCollections.observableArrayList();
        extraLists.put("Required Courses", coursesToBeTakenToGraduate);
//        this.coursesTaken = new RudeProperty();
//        this.propertyMap.put("Courses Taken", coursesTaken);
    }

    public Student(String firstName, String lastName, String phone, String homeAddress, Image profileImage, String gpa, String major) {
        this();
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setHomeAddress(homeAddress);
        setProfileImage(profileImage);
        setGpa(gpa);
        setMajor(major);
    }

    @Override
    public RudeObject randomInstance() {
        return RandomRudeObjectFactory.randomStudent();
    }

    public String getGpa() {
        return gpa.get();
    }

    public RudeProperty gpaProperty() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa.set(gpa);
    }

    public String getMajor() {
        return major.get();
    }

    public RudeProperty majorProperty() {
        return major;
    }

    public void setMajor(String major) {
        this.major.set(major);
    }

    public String getNumCredits() {
        double sum = 0;
        for (RudeObject rudeObject: coursesCurrentlyTaken) {
            sum += Double.valueOf(((Course) rudeObject).getNumberOfCredits());
        }
        return Double.toString(sum);
    }

    public RudeProperty numCreditsProperty() {
        return numCredits;
    }

    public void setNumCredits(String numCredits) {
        this.numCredits.set(numCredits);
    }
}
