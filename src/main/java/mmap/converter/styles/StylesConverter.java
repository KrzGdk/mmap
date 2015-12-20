package mmap.converter.styles;

import mmap.xmind.styles.Style;
import mmap.xmind.styles.XmapStyles;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StylesConverter {

    public List<CssSelector> createStyles(XmapStyles styles) throws IOException {
        List<CssSelector> cssSelectors = new ArrayList<>();
        List<Style> allStyles = new ArrayList<>();
        if (styles.getStyles() != null) {
            allStyles.addAll(styles.getStyles().getStyles());
        }
        if (styles.getAutomaticStyles() != null) {
            allStyles.addAll(styles.getAutomaticStyles().getStyles());
            createAutomaticLineStyles(styles.getAutomaticStyles().getStyles(), cssSelectors);
        }
        for (Style style : allStyles) {
            style.setId(style.getId().replaceAll("[^A-Za-z0-9]", ""));
            cssSelectors.addAll(createCssClasses(style));
        }
        CssSelector body = createBodySelector(allStyles);
        cssSelectors.add(body);
        StylesWriter stylesWriter = new StylesWriter();
        stylesWriter.write(cssSelectors);
        return cssSelectors;
    }

    public int countAutomaticLineStyles(List<Style> styles) {
        Optional<Style> topic = styles.stream().filter(style -> style.getTopicProperties() != null && style.getType().equals("topic")
                && style.getTopicProperties().getMultiLineColors() != null).findFirst();
        if (topic.isPresent()) {
            return topic.get().getTopicProperties().getMultiLineColors().split("\\s+").length;
        }
        return 0;
    }

    private void createAutomaticLineStyles(List<Style> styles, List<CssSelector> cssSelectors) {
        AtomicInteger index = new AtomicInteger();
        styles.stream().filter(style -> style.getTopicProperties() != null && style.getType().equals("topic")
                && style.getTopicProperties().getMultiLineColors() != null).forEach(s -> {
            List<String> automaticLineColors = Arrays.asList(s.getTopicProperties().getMultiLineColors().split("\\s+"));
            for (String automaticLineColor : automaticLineColors) {
                CssClass cssClass = new CssClass();
                cssClass.setId("autoLine" + index.incrementAndGet() + " .footer");
                cssClass.getProperties().put("background-color", automaticLineColor);

                CssClass cssTextClass = new CssClass();
                Color bgColor = Color.decode(automaticLineColor);
                cssTextClass.setId("autoLine" + index.get() + " .footer p");
                cssTextClass.getProperties().put("color", getFontColor(bgColor));
                cssSelectors.add(cssClass);
                cssSelectors.add(cssTextClass);
            }
        });
    }

    private CssSelector createBodySelector(List<Style> styles) {
        String bgColorHex = styles.stream()
                .filter(s -> s.getType().equals("map"))
                .findFirst().get().getMapProperties().getFill();

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
        cssTextClass.setId(style.getId() + " .footer p");
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
