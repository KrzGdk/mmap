package mmap.xmind.content;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Topic {

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "children")
    private Children children;

    @XmlElement(name = "notes")
    private Notes notes;

    @XmlElement(namespace = "http://www.w3.org/1999/xhtml", name = "img")
    private Img img;

    @XmlAttribute(name = "style-id")
    private String styleId;

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

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
        return hasChildren() ? children.getTopics().getTopics().size() : 0;
    }

    public boolean hasGrandChildren() {
        return hasChildren() && getChildren().getTopics().getTopics().stream().anyMatch(Topic::hasChildren);
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }
}
