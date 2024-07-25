package com.routdoo.dailyroutine.cms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.util
 * fileName       : PropertieConfig
 * author         : rhkdg
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        rhkdg       최초 생성
 */
@Component
public class PropertiesConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String CLASSPATH = "classpath:";
    private static String CLASSPATH_FILE = "properties/";
    private static String SUFFIX = ".properties";


    public String getProperty(String fileName,String code) {

        String propertiesPath = "";

        try(
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CLASSPATH_FILE + "upload/"+fileName + SUFFIX);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ){
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            if(lines.size()  > 0) {
                for (int i = 0; i < lines.size(); i++) {
                    String str = lines.get(i);

                    boolean isAnnotation = false;
                    for (int j = 0; j < str.length(); j++) {
                        if (str.charAt(j) == '#') {
                            isAnnotation = true;
                            break;
                        }
                    }

                    if (!isAnnotation) {
                        if (str.contains(code)) {
                            String[] split = str.split("=");
                            propertiesPath = split[1];
                        }
                    }
                }
            }else {
                throw new Exception("resource path empty ");
            }
        }catch (Exception e){
            logger.error(" porperties file found error : {}",e.getMessage());
            return null;
        }

        return propertiesPath;
    }


    public String getGlobalsProperty() {
        String propertiesPath = "";

        try(
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CLASSPATH_FILE + "globals" + SUFFIX);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ){
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            if(lines.size()  > 0) {
                for (int i = 0; i < lines.size(); i++) {
                    String str = lines.get(i);

                    boolean isAnnotation = false;
                    for (int j = 0; j < str.length(); j++) {
                        if (str.charAt(j) == '#') {
                            isAnnotation = true;
                            break;
                        }
                    }

                    if (!isAnnotation) {
                        if (str.contains("Globals.upload.path")) {
                            String[] split = str.split("=");
                            propertiesPath = split[1];
                        }
                    }
                }
            }else {
                throw new Exception("resource path empty ");
            }
        }catch (Exception e){
            logger.error(" porperties file found error : {}",e.getMessage());
            return null;
        }

        return propertiesPath;
    }
}
