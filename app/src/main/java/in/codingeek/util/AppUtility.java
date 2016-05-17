package in.codingeek.util;

import java.util.Properties;

/**
 * Created by root on 6/3/16.
 */
public class AppUtility {
    private Properties properties;

    public AppUtility(Properties properties) {
        this.properties = properties;
    }

    public String getPropertyValue(String property) {
        return this.properties.getProperty(property);
    }
}
