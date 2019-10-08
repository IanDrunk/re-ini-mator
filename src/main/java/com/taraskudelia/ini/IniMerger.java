package com.taraskudelia.ini;

import org.ini4j.Ini;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Taras Kudelia
 * @since 08 Oct 2019
 */
public class IniMerger {

    public static Wini merge(Ini baseModel, Ini supModel) {
        Wini result = new Wini();

        // Get all section names
        Set<String> baseSectionNames = baseModel.keySet();
        Set<String> supSectionNames = supModel.keySet();

        // Add all sections from the base model to the result
        baseSectionNames.forEach(name -> result.put(name, baseModel.get(name)));

        // Define unique sections from the supplementary model and put them to the result model
        // If we found repeating Section - save is for the future resolution
        Set<String> conflictingSections = new HashSet<>();
        supSectionNames.forEach(name -> {
            if (!baseSectionNames.contains(name)) {
                result.put(name, baseModel.get(name));
            } else {
                conflictingSections.add(name);
            }
        });

        // TODO
        //  - merge sections
        //  - check for key-values in sections
        //  - merge

        return result;
    }

    private static void addSectionToIni(Ini.Section section, Ini iniModel) {
//        iniModel.add();
    }

}
