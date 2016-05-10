package sample;

import javafx.scene.image.Image;

/**
 * Created by Nick on 12/17/2015.
 */
public class Faculty extends Person {
    private RudeProperty officeAddress, title, department, payScale;

    public Faculty() {
        super();

        this.department = new RudeProperty();
        this.propertyMap.put("Department", department);

        this.officeAddress = new RudeProperty();
        this.propertyMap.put("Office Address", officeAddress);

        this.payScale = new RudeProperty();
        this.propertyMap.put("Pay Scale", payScale);

        this.title = new RudeProperty();
        this.propertyMap.put("Title", title);

    }

    public Faculty(String firstName, String lastName, String phone, String homeAddress, Image profileImage, String department, String officeAddress, String payScale, String title) {
        this();
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setHomeAddress(homeAddress);
        setProfileImage(profileImage);
        setDepartment(department);
        setOfficeAddress(officeAddress);
        setPayScale(payScale);
        setTitle(title);
    }

    @Override
    public RudeObject randomInstance() {
        return RandomRudeObjectFactory.randomFaculty();
    }

    public String getDepartment() {
        return department.get();
    }

    public RudeProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getOfficeAddress() {
        return officeAddress.get();
    }

    public RudeProperty officeAddressProperty() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress.set(officeAddress);
    }

    public String getPayScale() {
        return payScale.get();
    }

    public RudeProperty payScaleProperty() {
        return payScale;
    }

    public void setPayScale(String payScale) {
        this.payScale.set(payScale);
    }

    public String getTitle() {
        return title.get();
    }

    public RudeProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}
