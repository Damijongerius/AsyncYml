package com.dami;

import com.dami.objects.AutoSL;
import com.dami.objects.Savable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//automatic saving and loading enabled
@AutoSL
public class Pizza extends Savable {

    public int a = 10;
    public String b = "Hello, World!";

    public Map<String,Integer> things = new HashMap<>();

    public ArrayList<String> c = new ArrayList<String>() {{
        add("a");
        add("b");
        add("c");
        add("d");
    }};

    @Override
    public Map<String, Object> saveToYaml() {
        Map<String, Object> yamlData = new LinkedHashMap<>();
        yamlData.put("a", a);
        yamlData.put("b", b);
        yamlData.put("c", c);

        yamlData.put("things", things);

        return yamlData;
    }
}
