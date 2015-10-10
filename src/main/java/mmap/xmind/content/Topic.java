package mmap.xmind.content;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "topic")
public class Topic {

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "children")
    private Children children;
}
