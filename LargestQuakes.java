
/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
public class LargestQuakes {
    
    public void findLargestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> quakeData  = parser.read(source);
        System.out.println("read data for "+quakeData.size());
        ArrayList<QuakeEntry> largest5 = getLargest(quakeData, 5);
        for (QuakeEntry elt : largest5) {
            System.out.println(elt);
        }
    }
    
    public int indexOfLargest(ArrayList<QuakeEntry> quakeData) {
        int indexMax = 0;
        double maxMag = quakeData.get(0).getMagnitude();
        for(int k=1;k<quakeData.size();k++){
            QuakeEntry elt = quakeData.get(k);
            if(elt.getMagnitude() > maxMag) {
                indexMax = k;
                maxMag = elt.getMagnitude();
            }
        }
        return indexMax;
    }
    
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> result = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        int indexMax;
        for(int k=0;k<howMany;k++) {
            indexMax = indexOfLargest(copy);
            result.add(copy.get(indexMax));
            copy.remove(indexMax);
        }
        return result;
    }
}
