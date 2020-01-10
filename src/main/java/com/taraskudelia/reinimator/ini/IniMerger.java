package com.taraskudelia.reinimator.ini;

import com.taraskudelia.reinimator.ini.model.SectionModel;
import org.ini4j.Wini;

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
     * @implNote Here we are assuming that the mainModel is top priority and in case of the conflicting fields
     * base field will be accepted and the supplementary one will be dropped.
     *
     * @param mainModel - model of the main Ini file
     * @param suppModel - model of the supplementary Ini file
     * @return Wini model - result of merging two models.
     */
    public static Wini merge(Wini mainModel, Wini suppModel) {
        Wini result = new Wini();

        // Get all section names
        Set<String> mainSectionNames = mainModel.keySet();
        Set<String> suppSectionNames = suppModel.keySet();
        Set<String> allSectionNames = new HashSet<>(mainSectionNames);
        allSectionNames.addAll(suppSectionNames);

        // Sort what need to be merged
        List<String> sorterNames = allSectionNames.stream().sorted().collect(Collectors.toList());
        sorterNames.forEach(sectionName -> {
            // Check name presence in models
            boolean isInMain = mainSectionNames.contains(sectionName);
            boolean isInSupp = suppSectionNames.contains(sectionName);

            if (!isInMain && !isInSupp) {
                // Something gone wrong
                return;
            }

            // If section is unique across both models - add to the result.
            // Else - we apply merge and add the resulting section.
            Wini.Section resultSection = isInMain && isInSupp
                    ? mergeSections(mainModel.get(sectionName), suppModel.get(sectionName)) // section collision
                    : (isInMain ? mainModel : suppModel).get(sectionName);
            result.put(sectionName, resultSection);
        });
        return result;
    }

    /**
     * Merges two ini section models (applying )
     *
     * @param main - main ini section model
     * @param supp - supplementary ini section model
     * @return resulting merged ini section model
     */
    private static SectionModel mergeSections(Wini.Section main, Wini.Section supp) {
        String sectionName = main.getName();
        SectionModel mainSection = new SectionModel(sectionName, main.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (key, value) -> value)));
        SectionModel suppSection = new SectionModel(sectionName, supp.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (key, value) -> value)));
        return SectionModel.merge(mainSection, suppSection);
    }

}
