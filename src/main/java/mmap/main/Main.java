package mmap.main;


import mmap.converter.Converter;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        String path = args[0];
        Converter converter = new Converter();
        converter.convert(new File(path));
    }

}
