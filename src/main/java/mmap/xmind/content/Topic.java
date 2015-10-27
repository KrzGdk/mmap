package mmap.xmind.content;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "topic")
public class Topic {

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "children")
    private Children children;

    public String getTitle() {
        return title;
    }

    public Children getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return children != null && children.getTopics() != null &&
                children.getTopics().getTopics() != null && children.getTopics().getTopics().size() > 0;
    }

    public int getChildCount() {
        return hasChildren()? children.getTopics().getTopics().size() : 0;
    }

    public boolean hasGrandChildren() {
        return hasChildren() && getChildren().getTopics().getTopics().stream().anyMatch(Topic::hasChildren);
    }
}
