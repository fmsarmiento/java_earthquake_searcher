import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry qe : quakeData) {
            if(qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry qe : quakeData) {
            if(qe.getLocation().distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> result = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if((qe.getDepth() > minDepth) && (qe.getDepth() < maxDepth)) {
                result.add(qe);
            }
        }
        return result;
    }
    
    private boolean finding_where(String where, String phrase, String entry) {
        boolean found=false;
        if (where.equals("start")) {
            found = entry.startsWith(phrase);
        }
        else if (where.equals("end")) {
            found = entry.endsWith(phrase);
        }
        else if (where.equals("any")) {
            int check = entry.indexOf(phrase);
            if (check != -1) {
                found = true;
            }
            else {
                found = false;
            }
        }
        else {
            System.out.println("Error in function finding_where: key ''where'' not found.");
        }
        return found;
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, String phrase) {
        ArrayList<QuakeEntry> result = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe: quakeData) {
            String curr = qe.getInfo();
            if(finding_where(where, phrase, curr)) {
                result.add(qe);
            }
        }
        return result;
    }
    
    public void quakesByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
        System.out.println("read data for "+quakeData.size()+" quakes");
        ArrayList<QuakeEntry> filtered = filterByPhrase(quakeData, "any", "Creek");
        for (QuakeEntry qe : filtered) {
            System.out.println(qe);
        }
        System.out.println("Data for phrase: "+filtered.size());
    }
    
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> quakeData = parser.read(source);
        System.out.println("read data for "+quakeData.size()+" quakes");
        ArrayList<QuakeEntry> filtered = filterByDepth(quakeData, -10000.0, -8000.0);
        for (QuakeEntry qe : filtered) {
            System.out.println(qe);
        }
        System.out.println("Data for chosen depth: "+filtered.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        double magMin = 5;
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData  = parser.read(source);
        System.out.println("read data for "+quakeData.size()+" quakes");
        ArrayList<QuakeEntry> filtered_data = filterByMagnitude(quakeData, magMin);
        for (QuakeEntry x : filtered_data) {
            System.out.println(x);
        }
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> quakeData  = parser.read(source);
        System.out.println("read data for "+quakeData.size()+" quakes");
        //Location city = new Location(35.988, -78.907); // This location is Durham, NC
        Location city =  new Location(38.17, -118.82); // This location is Bridgeport, CA
        ArrayList<QuakeEntry> filtered = filterByDistanceFrom(quakeData,1000*1000,city); //m to km 1000*1000
        for (QuakeEntry qe : filtered) {
            System.out.println(qe);
        }
        System.out.println("Data for closest quakes: "+filtered.size());
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
    
    
}
