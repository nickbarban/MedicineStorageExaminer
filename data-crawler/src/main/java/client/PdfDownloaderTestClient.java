package client;

import java.io.IOException;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;

import domain.Medical;
import service.SiteDownloader;
import service.SiteParserServiceBean;

public class PdfDownloaderTestClient {

    public static void main(String[] args) {
        Document document;
        try {
            document = SiteDownloader.download();
        } catch (IOException e) {
            throw new RuntimeException("Cann't get document!");

        }
        SiteParserServiceBean parser = new SiteParserServiceBean(document);
        ;

        for (Entry<Medical, String> link : parser.parse().entrySet()) {
            System.out.println(link);
        }
    }

}
