package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Nick on 12/17/2015.
 */
public class RudeProperty extends SimpleStringProperty{
    private boolean readOnly = false;
    public RudeProperty() {
    }

    public RudeProperty(Object bean, String name) {
        super(bean, name);
    }

    public RudeProperty(Object bean, String name, String initialValue) {
        super(bean, name, initialValue);
    }

    public RudeProperty(String initialValue) {
        super(initialValue);
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
