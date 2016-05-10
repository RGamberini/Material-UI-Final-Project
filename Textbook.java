package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.image.Image;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Created by Nick on 12/17/2015.
 */
public class Textbook extends RudeObject {
    RudeProperty title, author, publisher, price, ISBNNumber, year;
    public Textbook() {
        super();
        //Initialization stuff
        //TODO: Both of these can be put in the RudeObject constructor
        propertyMap = new HashMap<>();
        subLabelProperty = new RudeProperty();

        this.author = new RudeProperty();
        propertyMap.put("Author", author);

        this.ISBNNumber = new RudeProperty();
        propertyMap.put("ISBN Number", ISBNNumber);

        this.price = new RudeProperty();
        propertyMap.put("Price", price);

        this.publisher = new RudeProperty();
        propertyMap.put("Publisher", publisher);

        this.title = new RudeProperty();
        propertyMap.put("Title", title);

        this.year = new RudeProperty();
        propertyMap.put("Year Published", year);

        this.profileImage = new SimpleObjectProperty<>();
        try {
            setProfileImage(new Image(getClass().getResource("/resources/avatars/book.png").toURI().toURL().toExternalForm()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        layoutInit();

    }

    public Textbook(String author, String ISBNNumber, String price, String publisher, String title, String year) {
        this();
        setAuthor(author);
        setISBNNumber(ISBNNumber);
        setPrice(price);
        setPublisher(publisher);
        setTitle(title);
        setYear(year);
    }

    public void layoutInit() {
        mainLabel.textProperty().bind(titleProperty());

        RudeEditIcon edit = new RudeEditIcon(this, mainLabel, titleProperty());
        this.icons.add(edit);
        this.setMargin(edit, new Insets(0, 1, 0, 0));

        RudeIcon delete = new RudeDeleteIcon(this);
        this.icons.add(delete);

        super.layoutInit();
    }

    @Override
    public String getDEFAULT_MAIN_SORT_PROPERTY() {
        return "Title";
    }

    @Override
    public String getDEFAULT_SUB_SORT_PROPERTY() {
        return "Year Published";
    }

    @Override
    public void initHeader(IconCell name, IconCell headerCell1, IconCell headerCell2) {
        name.mainLabel.textProperty().bind(titleProperty());
        name.icons.clear();
        name.icons.add(new RudeEditIcon(this, name.mainLabel, titleProperty()));

        headerCell1.mainLabel.textProperty().bind(authorProperty());
        headerCell1.icons.clear();
        headerCell1.icons.add(new RudeEditIcon(headerCell2, headerCell2.mainLabel, authorProperty()));

        headerCell2.mainLabel.textProperty().bind(publisherProperty());
        headerCell2.icons.clear();
        headerCell2.icons.add(new RudeEditIcon(headerCell2, headerCell2.mainLabel, publisherProperty()));
    }

    @Override
    public void initInputHeader(JFXTextField nameField, JFXTextField headerCell1Field, JFXTextField headerCell2Field) {
        title.bindBidirectional(nameField.textProperty());

        author.bindBidirectional(headerCell1Field.textProperty());
        headerCell1Field.setPromptText("Author");

        publisherProperty().bindBidirectional(headerCell2Field.textProperty());
        headerCell2Field.setPromptText("Publisher");
    }

    @Override
    public RudeObject randomInstance() {
        return RandomRudeObjectFactory.randomTextbook();
    }

    public String getAuthor() {
        return author.get();
    }

    public RudeProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getISBNNumber() {
        return ISBNNumber.get();
    }

    public RudeProperty ISBNNumberProperty() {
        return ISBNNumber;
    }

    public void setISBNNumber(String ISBNNumber) {
        this.ISBNNumber.set(ISBNNumber);
    }

    public String getPrice() {
        return price.get();
    }

    public RudeProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public RudeProperty publisherProperty() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
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

    public String getYear() {
        return year.get();
    }

    public RudeProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }
}
