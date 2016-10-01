/**
 * 
 */
package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author Yevgeny Go
 *
 */
public class PropHandler {
    private Properties properties;

    /**
     * 
     * @param propFile
     * throws FileNotFoundException 
     * @throws IOException 
     */
    public PropHandler(String propFileName) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(propFileName));
    }

    /**
     * 
     * @return Properties prop
     */
    public Properties getProperties() {
        return properties;
    }
    
    /**
     * 
     * @param key
     * @return value
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String toString() {
        StringBuffer propStr = new StringBuffer("Properties: ");
        for (Entry<Object, Object> entry : properties.entrySet()) {
            propStr.append(entry.getKey() + " => " + entry.getValue() + "\n");
        }
        return propStr.toString();
    }
}
