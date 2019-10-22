package com.taraskudelia.ini.model;

import java.util.Map;

/**
 * @author Taras Kudelia
 * @since 18 Oct 2019
 */
public class SectionModel extends AbstractSectionModel {

    public SectionModel(String name) {
        super(name);
    }

    public SectionModel(String name, Map<String, String> keyValueMap) {
        super(name, keyValueMap);
    }

    /**
     * Merges two SectionModels. Takes primary key-value pairs from the primary SectionModel
     * and updates overlapping pairs with the values from the override SectionModel, also adds pairs from the override
     * model if they are not found in the primary model.
     *
     * @param main - SectionModel to take as a base
     * @param supp - SectionModel that would be used for the colliding value override and adding ones that missing.
     * @return new merged SectionModel.
     */
    public static SectionModel merge(SectionModel main, SectionModel supp) {
        Map<String, String> values = supp.getKeyValueMap();
        // Here we add/override supp values with the main ones
        main.getKeyValueMap().forEach(values::put);
        return new SectionModel(main.getName(), values);
    }

}
