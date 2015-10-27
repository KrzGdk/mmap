package mmap.xmind.content;

import javax.xml.bind.annotation.XmlAttribute;

public class Img {

    @XmlAttribute(name = "align")
    protected String align;

    @XmlAttribute(namespace = "http://www.w3.org/1999/xhtml", name = "src")
    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }
}
