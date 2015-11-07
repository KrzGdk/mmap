package mmap.xmind.styles;

import javax.xml.bind.annotation.XmlAttribute;

public class StyleProperties {
    @XmlAttribute(namespace = "http://www.w3.org/2000/svg", name = "fill")
    private String fill;

    @XmlAttribute(name = "line-color")
    private String lineColor;

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }
}
