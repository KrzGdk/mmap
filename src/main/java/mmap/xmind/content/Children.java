package mmap.xmind.content;

import javax.xml.bind.annotation.XmlElement;

public class Children {

    public Topics getTopics() {
        return topics;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
    }

    @XmlElement(name = "topics")
    private Topics topics;
}
