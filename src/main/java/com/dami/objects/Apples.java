package com.dami.objects;

import java.util.LinkedHashMap;
import java.util.Map;

@AutoSL
public class Apples extends Savable {

    public String a = "appels";
    public String b = "zijn";

    public String c = "lekker";
    @Override
    public Map<String, Object> saveToYaml() {
        Map<String, Object> yamlData = new LinkedHashMap<>();
        yamlData.put("a", a);
        yamlData.put("b", b);
        yamlData.put("c", c);

        return yamlData;
    }
}
