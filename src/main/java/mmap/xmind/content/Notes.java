package mmap.xmind.content;


import javax.xml.bind.annotation.XmlElement;

public class Notes {

    @XmlElement(name = "html")
    private Html html;


    @XmlElement(name = "plain")
    private String plain;

    public Html getHtml() {
        return html;
    }

    public void setHtml(Html html) {
        this.html = html;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }
}
