package client;

import org.jsoup.nodes.Document;
import service.LinksParserServiceImpl;
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
        LinksParserServiceImpl parser = new LinksParserServiceImpl(document);
        parser.parse();
    }

}
