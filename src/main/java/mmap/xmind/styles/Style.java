package mmap.xmind.styles;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Style {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "type")
    private String type;

    @XmlElement(name = "topic-properties")
    private StyleProperties topicProperties;

    @XmlElement(name = "map-properties")
    private StyleProperties mapProperties;

    public StyleProperties getTopicProperties() {
        return topicProperties;
    }

    public void setTopicProperties(StyleProperties topicProperties) {
        this.topicProperties = topicProperties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StyleProperties getMapProperties() {
        return mapProperties;
    }

    public void setMapProperties(StyleProperties mapProperties) {
        this.mapProperties = mapProperties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
