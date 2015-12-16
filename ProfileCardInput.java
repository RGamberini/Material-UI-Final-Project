package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.util.Map;

/**
 * Created by Nick on 12/14/2015.
 */
public class ProfileCardInput extends ProfileCard {
    private Person personToBe;
    public JFXButton submit = new JFXButton(), cancel = new JFXButton();
    public JFXButton[] buttons = {submit, cancel};

    public ProfileCardInput() {
        super();
        personToBe = new Person();
        profileImage.imageProperty().bind(personToBe.profileImageProperty());
        personToBe.setProfileImage(RandomPersonFactory.randomProfilePicture());
        personToBe.setFirstName("");
        personToBe.setLastName("");

        name.getChildren().remove(name.mainLabel);
        name.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField nameField = new JFXTextField();
        nameField.setPromptText("Name");
        name.getChildren().add(nameField);
        ChangeListener nameListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    nameField.textProperty().unbind();
                    nameField.textProperty().addListener((_o, _oldVal, _newVal) -> {
                        int i = _newVal.indexOf(" ");
                        if (i > -1 && i < _newVal.length() - 1) {
                            personToBe.setFirstName(_newVal.substring(0, i).trim());
                            personToBe.setLastName(_newVal.substring(i).trim());
                        } else {
                            personToBe.setFirstName(_newVal.trim());
                            personToBe.setLastName("");
                        }
                    });
                } else {
                    nameField.textProperty().bind(Bindings.concat(personToBe.firstNameProperty(), " ", personToBe.lastNameProperty()));
                }
            }
        };
        nameField.focusedProperty().addListener(nameListener);

        phone.getChildren().remove(phone.mainLabel);
        phone.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField phoneField = new JFXTextField();
        personToBe.phoneProperty().bindBidirectional(phoneField.textProperty());
        phoneField.setPromptText("Phone");
        phone.getChildren().add(phoneField);

        address.getChildren().remove(address.mainLabel);
        address.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField addressField = new JFXTextField();
        personToBe.homeAddressProperty().bindBidirectional(addressField.textProperty());
        addressField.setPromptText("Address");
        address.getChildren().add(addressField);

        for (Map.Entry<String, StringProperty> entry: personToBe.propertyMap.entrySet()) {
            IconCell cell = new IconCell();
            cell.mainLabel.setText(entry.getKey());
            cell.setExempt(true);
            HBox.setMargin(cell.mainLabel, new Insets(0, 0, 0, 16));

            JFXTextField subLabel = new JFXTextField();
            subLabel.setPromptText(entry.getKey());
            subLabel.textProperty().bindBidirectional(entry.getValue());
            cell.getChildren().add(subLabel);

            cell.setMargin(subLabel, new Insets(0, 5, 0, 5));
            listViewData.add(cell);
        }
        listViewData.sort(new RudeCellComparator("test"));

        //Buttons!
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.getStyleClass().add("split");
        VBox.setMargin(buttonContainer, new Insets(10, -16, 0, -16));

        for (JFXButton button: buttons) {
            button.setMaxHeight(Double.MAX_VALUE);
            button.setPadding(new Insets(4, 20, 4, 20));
            button.setRipplerFill(Paint.valueOf("#757575"));
            HBox.setMargin(button, new Insets(16, 16, 0, 0));
        }

        MaterialIconView send = new MaterialIconView(MaterialIcon.SEND);
        send.setFill(Paint.valueOf("WHITE"));
        send.setSize("19px");

        submit.getStyleClass().add("button-submit");
        submit.setText("Submit");

        submit.setGraphic(send);
        submit.setContentDisplay(ContentDisplay.RIGHT);
        submit.setGraphicTextGap(5);

        cancel.getStyleClass().add("button-cancel");
        cancel.setText("Cancel");

        buttonContainer.getChildren().addAll(cancel, submit);

        this.getChildren().add(buttonContainer);

        //Event handling
        cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->onCardClearProperty.get().handle(new FloatingCardEvent(FloatingCardEvent.CLEAR)));
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (RudeValidator.validate(personToBe.propertyMap)) {
                onCardAcceptProperty.get().handle(new FloatingCardEvent(FloatingCardEvent.ACCEPT));
                onCardClearProperty.get().handle(new FloatingCardEvent(FloatingCardEvent.CLEAR));
            } else {
                onCardErrorProperty.get().handle(new FloatingCardEvent(FloatingCardEvent.ERROR));
            }
        });
    }

    public Person getPersonToBe() {
        return personToBe;
    }

    //Custom events
    private ObjectProperty<EventHandler<? super FloatingCardEvent>> onCardClearProperty = new SimpleObjectProperty<>((clear)-> {});

    public EventHandler<? super FloatingCardEvent> getOnClear() {
        return onCardClearProperty.get();
    }

    public void setOnClear(EventHandler<? super FloatingCardEvent> handler) {
        this.onCardClearProperty.set(handler);
    }

    private ObjectProperty<EventHandler<? super FloatingCardEvent>> onCardErrorProperty = new SimpleObjectProperty<>((error)-> {});

    public EventHandler<? super FloatingCardEvent> getOnError() {
        return onCardErrorProperty.get();
    }

    public void setOnError(EventHandler<? super FloatingCardEvent> handler) {
        this.onCardErrorProperty.set(handler);
    }

    private ObjectProperty<EventHandler<? super FloatingCardEvent>> onCardAcceptProperty = new SimpleObjectProperty<>((accept)-> {});

    public EventHandler<? super FloatingCardEvent> getOnAccept() {
        return onCardAcceptProperty.get();
    }

    public void setOnAccept(EventHandler<? super FloatingCardEvent> handler) {
        this.onCardAcceptProperty.set(handler);
    }
}
