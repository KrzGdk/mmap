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
        try {
            boolean revisitParent = false;
            List<String> params = Arrays.asList(args).stream().filter(s -> s.startsWith("-")).collect(Collectors.toList());
            if (params.contains("--revisit-parent") || params.contains("-R")) {
                revisitParent = true;
            }
            String path = Arrays.asList(args).stream().filter(s -> !s.startsWith("-")).findFirst().orElseThrow(() -> new IllegalArgumentException("No input file"));
            Converter converter = new Converter(revisitParent);
            converter.convert(new File(path));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: No input file supplied.");
        } catch (IOException e) {
            System.out.println("Error: Cannot read input file.");
        } catch (JAXBException e) {
            System.out.println("Error: Ill-formed mind map file structure.");
        }
        System.out.println("Success.");
        System.out.println("Created directory 'out' containing presentation.");
    }

}
