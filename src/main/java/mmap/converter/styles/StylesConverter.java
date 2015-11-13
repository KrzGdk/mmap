package mmap.converter.styles;

import mmap.xmind.styles.Style;
import mmap.xmind.styles.XmapStyles;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StylesConverter {

    public List<CssSelector> createStyles(XmapStyles styles) throws IOException {
        List<CssSelector> cssSelectors = new ArrayList<>();
        for (Style style : styles.getStyles().getStyles()) {
            cssSelectors.addAll(createCssClasses(style));
        }
        CssSelector body = createBodySelector(styles);
        cssSelectors.add(body);
        StylesWriter stylesWriter = new StylesWriter();
        stylesWriter.write(cssSelectors);
        return cssSelectors;
    }

    private CssSelector createBodySelector(XmapStyles styles) {
        Style automaticMapStyle = styles.getAutomaticStyles().getStyles().stream()
                .filter(s -> s.getType().equals("map"))
                .findFirst().get();
        String bgColorHex = styles.getStyles().getStyles().stream()
                .filter(s -> s.getType().equals("map"))
                .findFirst().orElse(automaticMapStyle).getMapProperties().getFill();
        CssSelector body = new CssSelector();
        body.setName("body");
        body.getProperties().put("background-color", bgColorHex);
        return body;
    }

    private Collection<CssClass> createCssClasses(Style style) {
        Collection<CssClass> classes = new ArrayList<>();

        if (style.getTopicProperties() != null && style.getTopicProperties().getFill() != null) {
            classes.addAll(createMainCssClass(style));
        }
        if (style.getTopicProperties() != null && style.getTopicProperties().getLineColor() != null) {
            classes.addAll(createSecondaryClass(style));
        }
        return classes;
    }

    private List<CssClass> createMainCssClass(Style style) {
        CssClass cssClass = new CssClass();
        cssClass.setId(style.getId());
        cssClass.getProperties().put("background-color", style.getTopicProperties().getFill());
        CssClass cssTextClass = new CssClass();
        cssTextClass.setId(style.getId() + " p");
        Color bgColor = Color.decode(style.getTopicProperties().getFill());
        cssTextClass.getProperties().put("color", getFontColor(bgColor));
        CssClass cssHeaderClass = new CssClass();
        cssHeaderClass.setId(style.getId() + " h1");
        cssHeaderClass.getProperties().put("color", getFontColor(bgColor));
        return Arrays.asList(cssClass, cssTextClass, cssHeaderClass);
    }

    private List<CssClass> createSecondaryClass(Style style) {
        CssClass cssSecondaryClass = new CssClass();
        cssSecondaryClass.setId(style.getId() + " .footer");
        cssSecondaryClass.getProperties().put("background-color", style.getTopicProperties().getLineColor());
        CssClass cssTextClass = new CssClass();
        cssTextClass.setId(style.getId() + " p");
        Color bgColor = Color.decode(style.getTopicProperties().getLineColor());
        cssTextClass.getProperties().put("color", getFontColor(bgColor));
        return Arrays.asList(cssSecondaryClass, cssTextClass);
    }

    private String getFontColor(Color bgColor) {
        double a = 1 - (0.299 * bgColor.getRed() + 0.587 * bgColor.getGreen() + 0.114 * bgColor.getBlue()) / 255;
        if (a < 0.5) {
            return "#000000";
        } else {
            return "#FFFFFF";
        }
    }
}
