/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Jovana
 */
public class SettingsLoader {

    public static SettingsLoader instance;

    private Properties properties;

    private SettingsLoader() throws FileNotFoundException, IOException {
        loadSettings();
    }

    public static SettingsLoader getInstance() throws IOException {
        if (instance == null) {
            instance = new SettingsLoader();
        }
        return instance;
    }

    private void loadSettings() throws FileNotFoundException, IOException {
        FileInputStream input = new FileInputStream("settings.properties");
        properties = new Properties();
        properties.load(input);
    }

    public String getValue(String key) {
        String value = properties.getProperty(key, "N/A");
        return value;
    }
}
