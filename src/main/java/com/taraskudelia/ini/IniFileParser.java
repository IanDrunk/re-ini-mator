package com.taraskudelia.ini;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Taras Kudelia
 * @since 08 Oct 2019
 */
public class IniFileParser {

    public static Map<File, Wini> parse(List<File> iniFiles) {
        Map<File, Wini> result = new HashMap<>();
        iniFiles.forEach(iniFile -> {
            try {
                result.put(iniFile, new Wini(iniFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

}
