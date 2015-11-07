package mmap.xmind.content;


import javax.xml.bind.annotation.XmlElement;

public class Notes {

    @XmlElement(name = "html")
    private Html html;

    public Html getHtml() {
        return html;
    }

    public void setHtml(Html html) {
        this.html = html;
    }
}
