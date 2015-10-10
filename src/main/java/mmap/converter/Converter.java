package mmap.converter;

import mmap.xmind.XmindFile;
import mmap.xmind.content.XmindContent;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class Converter {

    public Converter() {
    }

    public String convert(File file) throws IOException, JAXBException {
        XmindFile xmindFile = new XmindFile(file);
        XmindContent xml = xmindFile.getContent();
        // TODO
        return null;
    }
}
