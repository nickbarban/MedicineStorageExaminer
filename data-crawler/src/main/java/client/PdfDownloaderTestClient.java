package client;

import org.jsoup.nodes.Document;
import service.SiteParserServiceBean;
import service.SiteDownloader;

import java.io.IOException;

public class PdfDownloaderTestClient {

    public static void main(String[] args) {
        Document document;
        try {
            document = SiteDownloader.download();
        } catch (IOException e) {
            throw new RuntimeException("Cann't get document!");

        }
        SiteParserServiceBean parser = new SiteParserServiceBean(document);
        parser.parse();
    }

}
