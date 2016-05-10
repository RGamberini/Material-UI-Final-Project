package sample;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Nick on 12/16/2015.
 */
public class RudeValidator {
    public static boolean validate(Map<String, RudeProperty> propertyMap) {
        for (Map.Entry<String, RudeProperty> entry: propertyMap.entrySet()) {
            if (entry.getValue().get().trim().equals("")) {
                return false;
            }
        }
        return true;
    }
}
