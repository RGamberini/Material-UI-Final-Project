package sample;

import com.jfoenix.controls.JFXListCell;

import java.util.Comparator;

/**
 * Created by Nick on 12/9/2015.
 */
public class RudeCellComparator implements Comparator<RudeCell>{
    private String sortProperty;
    public RudeCellComparator(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    @Override
    public int compare(RudeCell o1, RudeCell o2) {
        if(o1 instanceof Person && o2 instanceof Person) {
            String lastName1 = ((Person) o1).propertyMap.get(sortProperty).get(), lastName2 = ((Person) o2).propertyMap.get(sortProperty).get();
//            System.out.println(lastName1 + " " + lastName2);
            int n = String.CASE_INSENSITIVE_ORDER.compare(lastName1, lastName2);
            return (n != 0) ? n : lastName1.compareTo(lastName2);
        } else if(o1 instanceof TitleCell || o2 instanceof TitleCell) {
            return 0;
        }
//        else if(o1 instanceof IconCell && o2 instanceof IconCell) {
//            String mainLabel1 = ((IconCell) o1).mainLabel.getText(), mainLabel2 = ((IconCell) o2).mainLabel.getText();
//            int n = String.CASE_INSENSITIVE_ORDER.compare(mainLabel1, mainLabel2);
//            return (n != 0) ? n : mainLabel1.compareTo(mainLabel2);
//        }
        else {
            System.out.println("Uncomparable " + o1.getClass() + " and " + o2.getClass());
            return 0;
        }
    }
}
