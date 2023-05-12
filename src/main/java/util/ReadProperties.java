package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class ReadProperties {
    private static Properties prop = null;

    private static Properties getProperties() {
        if (prop == null) {
            prop = new Properties();
            try {
                String fileName = (new File(ReadProperties.class.getProtectionDomain().getCodeSource().getLocation().toURI())).getParent();
                fileName = String.valueOf(fileName) + File.separator + "smtp.properties";
                File file = new File(fileName);
                if (file.exists()) {
                    prop.load(new FileReader(file));
                } else {
                    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                    prop.load(classloader.getResourceAsStream("smtp.properties"));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return prop;
    }

    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperties().getProperty(key));
    }

    public static Long getLongProperty(String key) {
        return Long.valueOf(Long.parseLong(getProperties().getProperty(key)));
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperties().getProperty(key));
    }
}
