package mmap.xmind.content;


import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Html {

    @XmlElement(namespace = "http://www.w3.org/1999/xhtml", name = "p")
    private List<String> paragraphs;

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
