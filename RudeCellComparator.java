package sample;

import javafx.scene.Node;

import java.util.Comparator;

/**
 * Created by Nick on 12/9/2015.
 */
public class RudeCellComparator implements Comparator<Node>{
    private String sortProperty;
    public RudeCellComparator(String sortProperty) {
        this.sortProperty = sortProperty;
    }
    private static int i = 0;

    @Override
    public int compare(Node o1, Node o2) {
        if(o1 instanceof RudeObject && o2 instanceof RudeObject) {
            String property1 = ((RudeObject) o1).propertyMap.get(sortProperty).get();
            String property2 = ((RudeObject) o2).propertyMap.get(sortProperty).get();
            try {
                double x1 = Double.parseDouble(property1);
                double x2 = Double.parseDouble(property2);
                return (int) (x1 - x2);
            } catch (NumberFormatException e) {}
            int n = String.CASE_INSENSITIVE_ORDER.compare(property1, property2);
            return (n != 0) ? n : property1.compareTo(property2);
        } else if(o1 instanceof IconCell && o2 instanceof IconCell) {
            String mainLabel1 = ((IconCell) o1).mainLabel.getText(), mainLabel2 = ((IconCell) o2).mainLabel.getText();
            int n = String.CASE_INSENSITIVE_ORDER.compare(mainLabel1, mainLabel2);
            return (n != 0) ? n : mainLabel1.compareTo(mainLabel2);
        }
        else if(o1 instanceof TitleCell || o2 instanceof TitleCell) {
            //System.out.println(i++);
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
