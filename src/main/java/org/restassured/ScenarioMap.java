package org.restassured;

import java.util.HashMap;
import java.util.Map;

public class ScenarioMap {

    private Map<String, Object> map;
    public ScenarioMap() { map = new HashMap<>(); }
    public Object get(String key) { return map.get(key); }
    public void set(String key, Object value) { map.put(key, value); }
    public void clear(String key) { map.remove(key); }
}
