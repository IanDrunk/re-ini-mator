package com.taraskudelia.ini;

import java.util.List;
import java.util.Map;

/**
 * @author Taras Kudelia
 * @since 08 Oct 2019
 */
public class IniModel {

    private String name;
    private List<IniBlock> blocks;

    class IniBlock {
        private String header;
        private Map<String, String> keyValues;
    }
}
