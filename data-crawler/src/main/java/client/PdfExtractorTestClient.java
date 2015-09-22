package client;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;

public class PdfExtractorTestClient {
    public static void main(String[] args) {
        try {

            BodyContentHandler handler = new BodyContentHandler();
//            ToXMLContentHandler handler = new ToXMLContentHandler();
            Metadata metadata = new Metadata();
            ClassLoader classLoader = PdfExtractorTestClient.class.getClassLoader();
            String[] metadataNames;
            try (FileInputStream inputstream = new FileInputStream(new File(classLoader.getResource("example.pdf").getFile()))) {
                ParseContext pcontext = new ParseContext();

                //parsing the document using PDF parser
                PDFParser pdfparser = new PDFParser();
                pdfparser.parse(inputstream, handler, metadata, pcontext);
            }

//            AttributeMatcher

            //getting the content of the document
            System.out.println("Contents of the PDF :" + handler.toString());


            //getting metadata of the document
            System.out.println("Metadata of the PDF:");
            metadataNames = metadata.names();

            for (String name : metadataNames) {
                System.out.println(name + " : " + metadata.get(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
