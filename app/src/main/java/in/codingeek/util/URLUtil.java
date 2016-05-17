package in.codingeek.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by root on 6/3/16.
 */
public class URLUtil {
    private static Logger logger = Logger.getLogger("URLUtil");

    public String constructUrl(String baseUrl, HashMap<String, Object> paramaters) {

        Iterator<Map.Entry<String, Object>> iterator = paramaters.entrySet().iterator();
        Map.Entry<String, Object> mapEntry = null;
        Integer counter = 1;

        while(iterator.hasNext()){
            if (counter ==1) baseUrl = baseUrl + "?";
            else baseUrl = baseUrl + "&";

            mapEntry = iterator.next();
            baseUrl = baseUrl + mapEntry.getKey() + "=" + mapEntry.getValue();
            counter++;
        }

        return baseUrl;
    }
}
