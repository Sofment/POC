/**
 Authors:

 Maxim Sushkevich (msushkevich@gmail.com) and Yahor Paulavets (paulavets.pride@gmail.com)

 This file is part of Gobrotium project (https://github.com/a-a-a-CBEI-I-IEE-M9ICO/GoBrotium.git)

 Gobrotium project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Gobrotium is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Gobrotium project.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.company.app;

import com.company.enums.ConfigParameter;
import com.company.utils.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;

    public ConfigManager() {
        init();
    }

    private void init() {
        try {
            loadProperties();
        } catch (IOException e) {
            closeApp("Unable to load configuration file: " + Constant.CONFIGURATION_FILE_PATH);
        }
    }

    public String[] getSentToEmails() {
        String value = getProperty(ConfigParameter.SEND_TO.name());
        if(value == null) {
            return new String[]{""}; // todo default email
        }
        return value.split(",");
    }

    public String getProperty(String propertyKey) {
        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            if(!key.equalsIgnoreCase(propertyKey))
                continue;
            String value = properties.getProperty(key);
            return value;
        }
        return null;
    }

    private static void loadProperties() throws IOException {
        File configProperties = new File(Constant.CONFIGURATION_FILE_PATH);
        FileInputStream fileInput = new FileInputStream(configProperties);
        Properties properties = new Properties();
        properties.load(fileInput);
        fileInput.close();

        setProperties(properties);
    }

    public static void setProperties(Properties $properties) {
        properties = $properties;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void closeApp(String s) {
        System.exit(0);
    }
}
