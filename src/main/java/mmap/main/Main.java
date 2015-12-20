package mmap.main;


import mmap.converter.Converter;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        boolean revisitParent = false;
        List<String> params = Arrays.asList(args).stream().filter(s -> s.startsWith("-")).collect(Collectors.toList());
        if (params.contains("--revisit-parent") || params.contains("-R")) {
            revisitParent = true;
        }
        String path = Arrays.asList(args).stream().filter(s -> !s.startsWith("-")).findFirst().orElseThrow(() -> new IllegalArgumentException("No input file"));
        Converter converter = new Converter(revisitParent);
        converter.convert(new File(path));
    }

}
