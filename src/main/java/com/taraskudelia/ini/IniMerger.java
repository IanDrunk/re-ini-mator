package com.taraskudelia.ini;

import org.ini4j.BasicProfile;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Taras Kudelia
 * @since 08 Oct 2019
 */
public class IniMerger {

    /**
     * Merging two models.
     *
     * @implNote Here we are assuming that the baseModel is top priority and in case of the conflicting fields
     * base field will be accepted and the supplementary one will be dropped.
     *
     * @param baseModel - model of the base Ini file
     * @param supModel  - model of the supplementary Ini file
     * @return Wini model - result of merging two models.
     */
    public static Wini merge(Ini baseModel, Ini supModel) {
        Wini result = new Wini();

        // Get all section names
        Set<String> baseSectionNames = baseModel.keySet();
        Set<String> supSectionNames = supModel.keySet();
        Set<String> allSectionNames = new HashSet<>(baseSectionNames);
        allSectionNames.addAll(supSectionNames);

        // Sort what need to be merged
        List<String> sorterNames = allSectionNames.stream().sorted().collect(Collectors.toList());
        sorterNames.forEach(sectionName -> {
            // Check name presence in models
            boolean isInBase = baseSectionNames.contains(sectionName);
            boolean isInSup = supSectionNames.contains(sectionName);

            if (!isInBase && !isInSup) {
                // Something gone wrong
                return;
            }

            Ini.Section value = isInBase && isInSup
                    ? mergeSections(baseModel.get(sectionName), supModel.get(sectionName)) // section collision
                    : (isInBase ? baseModel : supModel).get(sectionName);
            result.put(sectionName, value);

        });


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

    private static Ini.Section mergeSections(Ini.Section base, Ini.Section sup) {
        BasicProfile.Section section = null;

        List<String> childrenNames = Arrays.asList(base.childrenNames());
        childrenNames.forEach(name -> {
//            if ()
        });

        return section;
    }

    private static void addSectionToIni(Ini.Section section, Ini iniModel) {
//        iniModel.add();
    }

}
