package service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SiteDownloader {

    private final static String SITE_ADRESS = "http://mednet.odessa.gov.ua/nformacya-wodo-zalishkv-lkv-zakuplenih-za-koshti-mscevih-byudzhetv/";

    public static Document download() throws IOException {
        return Jsoup.connect(SITE_ADRESS).get();
    }
}
