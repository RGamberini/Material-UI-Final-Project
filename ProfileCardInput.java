package sample;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Nick on 12/14/2015.
 */
public class ProfileCardInput extends ProfileCard {
    private RudeObject personToBe;
    private TabPane tabPane;
    public JFXButton submit = new JFXButton(), cancel = new JFXButton();
    public JFXButton[] buttons = {submit, cancel};

    public ProfileCardInput(Class<? extends RudeObject> cls, Map<Class<? extends RudeObject>, ArrayList<RudeObject>> fullSet) {
        super();
        try {
            personToBe = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        profileImage.imageProperty().bind(personToBe.profileImageProperty());
        //personToBe.setProfileImage(RandomRudeObjectFactory.randomProfilePicture());

        name.getChildren().remove(name.mainLabel);
        name.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField nameField = new JFXTextField();
        nameField.setPromptText("Name");
        name.getChildren().add(nameField);

        headerCell1.getChildren().remove(headerCell1.mainLabel);
        headerCell1.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField headerCell1Field = new JFXTextField();
        headerCell1.getChildren().add(headerCell1Field);

        headerCell2.getChildren().remove(headerCell2.mainLabel);
        headerCell2.setStyle("-fx-border-style: hidden hidden hidden hidden;");
        JFXTextField headerCell2Field = new JFXTextField();
        headerCell2.getChildren().add(headerCell2Field);

        personToBe.initInputHeader(nameField, headerCell1Field, headerCell2Field);
        if (personToBe.extraLists != null) {
            this.getChildren().remove(tabPane);
            this.getChildren().remove(propertyList);

            tabPane = new JFXTabPane();
            this.getChildren().add(tabPane);
            Tab newTabe = new Tab();
            newTabe.setText("Properties");
            tabPane.getTabs().add(newTabe);
            newTabe.setContent(propertyList);

            for(Map.Entry<String, ObservableList<RudeObject>> entry: personToBe.extraLists.entrySet()) {
                Tab newTab = new Tab();
                tabPane.getTabs().add(newTab);
                newTab.setText(entry.getKey());

                VBox tempVBox = new VBox();

                JFXComboBox<RudeObject> comboBox = new JFXComboBox<>();
                comboBox.getItems().addAll(fullSet.get(personToBe.extraListClass));
                comboBox.setMaxWidth(Double.MAX_VALUE);
                comboBox.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
                    entry.getValue().add(newVal);
                });

                JFXListView<RudeObject> newListView = new JFXListView();
                VBox.setVgrow(newListView, Priority.ALWAYS);
                newListView.setItems(entry.getValue());

                tempVBox.getChildren().addAll(comboBox, newListView);
                newTab.setContent(tempVBox);
            }
        }
        for (Map.Entry<String, RudeProperty> entry: personToBe.propertyMap.entrySet()) {
            IconCell cell = new IconCell();
            cell.mainLabel.setText(entry.getKey());
            cell.setExempt(true);
            HBox.setMargin(cell.mainLabel, new Insets(0, 0, 0, 16));

            if (!entry.getValue().isReadOnly()) {
                JFXTextField subLabel = new JFXTextField();
                subLabel.setPromptText(entry.getKey());
                subLabel.textProperty().bindBidirectional(entry.getValue());
                cell.getChildren().add(subLabel);
                cell.setMargin(subLabel, new Insets(0, 5, 0, 5));
            } else {
                Label subLabel = new Label(entry.getValue().get());
                cell.getChildren().add(subLabel);
                cell.setMargin(subLabel, new Insets(5, 135, 5, 0));
            }

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

    public RudeObject getPersonToBe() {
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
