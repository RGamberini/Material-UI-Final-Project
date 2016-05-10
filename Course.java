package sample;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.image.Image;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nick on 12/17/2015.
 */
public class Course extends RudeObject{
    public RudeProperty courseTitle, courseNumber, CRNNumber, numberOfCredits, classRoomAddress, textbook, faculty;
    public ObservableList<RudeObject> students;

    public Course() {
        super();
        //Initialization stuff
        propertyMap = new HashMap<>();
        extraLists = new HashMap<>();

        this.courseTitle = new RudeProperty();
        propertyMap.put("Course Title", courseTitleProperty());

        this.courseNumber = new RudeProperty();
        propertyMap.put("Course Number", courseNumberProperty());

        this.CRNNumber = new RudeProperty();
        propertyMap.put("CRN Number", CRNNumberProperty());

        this.numberOfCredits = new RudeProperty();
        propertyMap.put("Number Of Credits", numberOfCredits);

        this.classRoomAddress= new RudeProperty();
        propertyMap.put("Class Room Address", classRoomAddressProperty());

        this.textbook = new RudeProperty();
        propertyMap.put("Textbook", textbookProperty());

        this.faculty = new RudeProperty();
        propertyMap.put("Faculty Member", facultyProperty());

        this.subLabelProperty = new RudeProperty();
        this.profileImage = new SimpleObjectProperty<>();
        try {
            setProfileImage(new Image(getClass().getResource("/resources/avatars/course.png").toURI().toURL().toExternalForm()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        students = FXCollections.observableArrayList();
        extraLists.put("Students", students);

        layoutInit();
    }

    public Course(String classRoomAddress, String courseNumber, String courseTitle, String CRNNumber, String numberOfCredits) {
        this();
        setClassRoomAddress(classRoomAddress);
        setCourseNumber(courseNumber);
        setCourseTitle(courseTitle);
        setCRNNumber(CRNNumber);
        setNumberOfCredits(numberOfCredits);

        //End Initialization stuff
    }

    public void layoutInit() {
        mainLabel.textProperty().bind(courseTitleProperty());

        RudeEditIcon edit = new RudeEditIcon(this, mainLabel, courseTitleProperty());
        this.icons.add(edit);
        this.setMargin(edit, new Insets(0, 1, 0, 0));

        RudeIcon delete = new RudeDeleteIcon(this);
        this.icons.add(delete);

        super.layoutInit();
    }

    @Override
    public String getDEFAULT_MAIN_SORT_PROPERTY() {
        return "Course Title";
    }

    @Override
    public String getDEFAULT_SUB_SORT_PROPERTY() {
        return "Number Of Credits";
    }

    @Override
    public void initHeader(IconCell name, IconCell headerCell1, IconCell headerCell2) {
        name.mainLabel.textProperty().bind(courseTitleProperty());
        name.icons.clear();
        name.icons.add(new RudeEditIcon(this, name.mainLabel, courseTitleProperty()));

        headerCell1.mainLabel.textProperty().bind(Bindings.concat(numberOfCreditsProperty(), " Credits"));
        headerCell1.icons.clear();
        headerCell1.icons.add(new RudeEditIcon(headerCell1, headerCell1.mainLabel) {
            @Override
            public void assignText(String text) {
                setNumberOfCredits(text.split(" ")[0]);
            }
        });

        headerCell2.mainLabel.textProperty().bind(this.classRoomAddressProperty());
        headerCell2.icons.clear();
        headerCell2.icons.add(new RudeEditIcon(headerCell2, headerCell2.mainLabel, this.classRoomAddressProperty()));

        MaterialIconView addressIcon = new MaterialIconView(MaterialIcon.PLACE);
        addressIcon.setStyleClass("profile-card-icon");
        addressIcon.setSize("25");
        headerCell2.mainLabel.setGraphic(addressIcon);
    }

    @Override
    public void initInputHeader(JFXTextField nameField, JFXTextField headerCell1Field, JFXTextField headerCell2Field) {
        courseTitleProperty().bindBidirectional(nameField.textProperty());

        setNumberOfCredits("");
        ChangeListener listener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    headerCell1Field.textProperty().unbind();
                    headerCell1Field.textProperty().addListener((_o, _oldVal, _newVal) -> {
                        setNumberOfCredits(_newVal.split(" ")[0]);
                    });
                } else {
                    headerCell1Field.textProperty().bind(Bindings.concat(numberOfCreditsProperty(), " Credits"));
                }
            }
        };
        headerCell1Field.focusedProperty().addListener(listener);
        headerCell1Field.setPromptText("Credits");

        classRoomAddressProperty().bindBidirectional(headerCell2Field.textProperty());
        headerCell2Field.setPromptText("Address");
    }

    @Override
    public RudeObject randomInstance() {
        return RandomRudeObjectFactory.randomCourse();
    }

    public String getClassRoomAddress() {
        return classRoomAddress.get();
    }

    public RudeProperty classRoomAddressProperty() {
        return classRoomAddress;
    }

    public void setClassRoomAddress(String classRoomAddress) {
        this.classRoomAddress.set(classRoomAddress);
    }

    public String getCourseNumber() {
        return courseNumber.get();
    }

    public RudeProperty courseNumberProperty() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber.set(courseNumber);
    }

    public String getCourseTitle() {
        return courseTitle.get();
    }

    public RudeProperty courseTitleProperty() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle.set(courseTitle);
    }

    public String getCRNNumber() {
        return CRNNumber.get();
    }

    public RudeProperty CRNNumberProperty() {
        return CRNNumber;
    }

    public void setCRNNumber(String CRNNumber) {
        this.CRNNumber.set(CRNNumber);
    }

    public String getNumberOfCredits() {
        return numberOfCredits.get();
    }

    public RudeProperty numberOfCreditsProperty() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(String numberOfCredits) {
        this.numberOfCredits.set(numberOfCredits);
    }

    public String getTextbook() {
        return textbook.get();
    }

    public RudeProperty textbookProperty() {
        return textbook;
    }

    public void setTextbook(String textbook) {
        this.textbook.set(textbook);
    }

    public String getFaculty() {
        return faculty.get();
    }

    public RudeProperty facultyProperty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty.set(faculty);
    }
}
