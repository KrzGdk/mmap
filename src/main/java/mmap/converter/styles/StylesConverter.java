package mmap.converter.styles;

import mmap.xmind.styles.Style;
import mmap.xmind.styles.XmapStyles;

import java.io.IOException;
import java.util.ArrayList;
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
        String bgColor = styles.getAutomaticStyles().getStyles().stream()
                .filter(s -> s.getType().equals("map"))
                .findFirst().get().getMapProperties().getFill();
        CssSelector body = new CssSelector();
        body.setName("body");
        body.getProperties().put("background-color", bgColor);
        return body;
    }

    private Collection<CssClass> createCssClasses(Style style) {
        Collection<CssClass> classes = new ArrayList<>();

        if(style.getTopicProperties().getFill() != null) {
            classes.add(createMainCssClass(style));
        }
        if(style.getTopicProperties().getLineColor() != null) {
            classes.add(createSecondaryClass(style));
        }
        return classes;
    }

    private CssClass createMainCssClass(Style style) {
        CssClass cssClass = new CssClass();
        cssClass.setId(style.getId());
        cssClass.getProperties().put("background-color", style.getTopicProperties().getFill());
        return cssClass;
    }

    private CssClass createSecondaryClass(Style style) {
        CssClass cssSecondaryClass = new CssClass();
        cssSecondaryClass.setId(style.getId() + " .footer");
        cssSecondaryClass.getProperties().put("background-color", style.getTopicProperties().getLineColor());
        return cssSecondaryClass;
    }
}
