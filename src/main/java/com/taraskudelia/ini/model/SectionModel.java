package com.taraskudelia.ini.model;

import java.util.Map;

public class SectionModel extends AbstractSectionModel {

    public SectionModel(String name) {
        super(name);
    }

    public SectionModel(String name, Map<String, String> keyValueMap) {
        super(name, keyValueMap);
    }

    /**
     * Looks for the entry with the given key and if present - overrides its value with a new one.
     * @param key      - field key
     * @param newValue - new field value
     * @return true if update was performed; false - otherwise.
     */
    public boolean updateField(String key, String newValue) {
        if (containsKey(key)) {
            put(key, newValue);
            return true;
        }
        return false;
    }

    /**
     * Merges two SectionModels. Takes primary key-value pairs from the primary SectionModel
     * and updates overlapping pairs with the values from the override SectionModel, also adds pairs from the override
     * model if they are not found in the primary model.
     *
     * @param primary  - SectionModel to take as a base
     * @param override - SectionModel that would be used for the colliding value override and adding ones that missing.
     * @return new merged SectionModel.
     */
    public static SectionModel merge(SectionModel primary, SectionModel override) {
        Map<String, String> values = primary.getKeyValueMap();
        override.getKeyValueMap().forEach(values::put);
        return new SectionModel(primary.getName(), values);
    }

}
