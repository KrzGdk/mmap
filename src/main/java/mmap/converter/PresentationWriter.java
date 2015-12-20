package mmap.converter;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import mmap.config.Configuration;
import mmap.presentation.Slide;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class PresentationWriter {

    public void write(String baseName, List<Slide> slideList) throws IOException {
        InputStream templateStream = getClass().getResourceAsStream("/template.html");
        Template template = Mustache.compiler().compile(new InputStreamReader(templateStream));

        try (Writer out = new OutputStreamWriter(new FileOutputStream(
                Configuration.OUTPUT_ROOT_DIR + File.separator + baseName + ".html"), Charset.forName("UTF-8").newEncoder())) {
            template.execute(new Object() {
                Object slides = slideList;
                Object slidesCount = slideList.size();
                Object mainTitle = slideList.get(0).getTitle();
            }, out);
        }
    }
}
