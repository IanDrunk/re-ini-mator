package com.taraskudelia.ini;

import com.taraskudelia.ini.model.SectionModel;
import org.ini4j.BasicProfile;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
     * @param suppModel - model of the supplementary Ini file
     * @return Wini model - result of merging two models.
     */
    public static Wini merge(Ini baseModel, Ini suppModel) {
        Wini result = new Wini();

        // Get all section names
        Set<String> baseSectionNames = baseModel.keySet();
        Set<String> suppSectionNames = suppModel.keySet();
        Set<String> allSectionNames = new HashSet<>(baseSectionNames);
        allSectionNames.addAll(suppSectionNames);

        // Sort what need to be merged
        List<String> sorterNames = allSectionNames.stream().sorted().collect(Collectors.toList());
        sorterNames.forEach(sectionName -> {
            // Check name presence in models
            boolean isInBase = baseSectionNames.contains(sectionName);
            boolean isInSupp = suppSectionNames.contains(sectionName);

            if (!isInBase && !isInSupp) {
                // Something gone wrong
                return;
            }

            // If section is unique across both models - add to the result.
            // Else - we apply merge and add the resulting section.
            Ini.Section resultSection = isInBase && isInSupp
                    ? mergeSections(baseModel.get(sectionName), suppModel.get(sectionName)) // section collision
                    : (isInBase ? baseModel : suppModel).get(sectionName);
            result.put(sectionName, resultSection);

        });

        return result;
    }

    private static SectionModel mergeSections(Ini.Section base, Ini.Section supp) {
        String sectionName = base.getName();
        SectionModel baseSection = new SectionModel(sectionName, base.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (key, value) -> value)));
        SectionModel suppSection = new SectionModel(sectionName, supp.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (key, value) -> value)));
        return SectionModel.merge(baseSection, suppSection);
    }

}
