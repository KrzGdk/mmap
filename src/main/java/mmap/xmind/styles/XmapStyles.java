package mmap.xmind.styles;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xmap-styles")
public class XmapStyles {

    @XmlElement(name = "automatic-styles")
    private Styles automaticStyles;

    @XmlElement(name = "styles")
    private Styles styles;

    public Styles getStyles() {
        return styles;
    }

    public void setStyles(Styles styles) {
        this.styles = styles;
    }

    public Styles getAutomaticStyles() {
        return automaticStyles;
    }

    public void setAutomaticStyles(Styles automaticStyles) {
        this.automaticStyles = automaticStyles;
    }
}
