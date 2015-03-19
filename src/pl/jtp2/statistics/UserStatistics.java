package pl.jtp2.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserStatistics { //singleton
    private static volatile UserStatistics userStatistics = null;

    private HashMap<String, Integer> clickStats = new HashMap<String, Integer>();

    public static UserStatistics getInstance() {
        if(userStatistics == null) {
            synchronized (UserStatistics.class) {
                if(userStatistics == null) {
                    userStatistics = new UserStatistics();
                }
            }
        }
        return userStatistics;
    }

    private UserStatistics() {
    }

    public synchronized void addAction(String source) {
        int tmp;
        if(clickStats.containsKey(source)) {
            tmp = clickStats.get(source);
            tmp += 1;
            clickStats.put(source, tmp);
        } else {
            clickStats.put(source, 1);
        }
    }

    public void printMap() {
        Iterator i = clickStats.entrySet().iterator();

        while(i.hasNext()) {
            Map.Entry mEntry = (Map.Entry) i.next();
            System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
        }
    }
}
