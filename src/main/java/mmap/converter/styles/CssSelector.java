package mmap.converter.styles;

import java.util.HashMap;
import java.util.Map;

public class CssSelector {

    protected String name;
    protected Map<String, String> properties = new HashMap<>();

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String cssClass = getName() + " {\n";
        for (Map.Entry<String, String> property : properties.entrySet()) {
            cssClass = cssClass + "\t" + property.getKey() + ": " + property.getValue() + ";\n";
        }
        return cssClass + "}";
    }
}
