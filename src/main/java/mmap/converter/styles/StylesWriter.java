package mmap.converter.styles;

import mmap.config.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StylesWriter {

    public void write(List<CssSelector> selectors) throws IOException {
        File stylesFile = new File(Configuration.CSS_DIR + File.separator + "styles.css");

        try(FileWriter fileWriter = new FileWriter(stylesFile)) {
            fileWriter.write(selectors.stream().map(Object::toString).collect(Collectors.joining("\n")));
        }
    }
}
