package mmap.xmind.styles;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

public class Styles {

    @XmlElements(value = @XmlElement(name = "style"))
    private List<Style> styles;

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

}
