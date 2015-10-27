package mmap.xmind.content;

import javax.xml.bind.annotation.XmlElement;

public class Sheet {

    @XmlElement
    private Topic topic;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
