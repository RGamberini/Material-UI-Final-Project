package sample;

import com.jfoenix.controls.JFXListCell;

import java.util.Comparator;

/**
 * Created by Nick on 12/9/2015.
 */
public class RudeCellComparator implements Comparator<RudeCell>{
    @Override
    public int compare(RudeCell o1, RudeCell o2) {
        if(o1 instanceof Student && o2 instanceof Student) {
            return ((Student) o1).lastName.compareTo(((Student)  o2).lastName);
        } else if(o1 instanceof TitleCell) {
            return 0;
        } else if(o2 instanceof TitleCell) {
            return 0;
        }
        else {
            System.out.println("Uncomparable " + o1.getClass() + " and " + o2.getClass());
            return 0;
        }
    }
}
