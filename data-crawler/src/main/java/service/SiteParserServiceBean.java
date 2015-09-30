package service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import domain.Medical;

public class SiteParserServiceBean implements SiteParserService {

    private Document htmlDocument;

    public SiteParserServiceBean(Document htmlDocument) {
        this.htmlDocument = htmlDocument;
    }

    public Map<Medical, String> parse() {
        Preconditions.checkNotNull(htmlDocument, "HTML document is not specified!");
        Map<Medical, String> links = new LinkedHashMap<>();
        Elements rows = htmlDocument.select("#main > div > div > table > tbody > tr ");
        for (Element row : rows) {
            Element linkElem = getElementFromRow(row, "19");
            Element nameElem = getElementFromRow(row, "48");
            Element cityElem = getElementFromRow(row, "31");

            if (linkElem == null || nameElem == null || cityElem == null) {
                continue;
            }

            String link = linkElem.select("a").attr("href");

            if (Strings.isNullOrEmpty(link)) {
                continue;
            }

            Medical medical = new Medical();
            medical.setCity(cityElem.text());
            medical.setName(nameElem.text());

            links.put(medical, link);
        }
        return links;
    }

    private Element getElementFromRow(Element row, String attrPatrametr) {
        Element element = row.select("td[width=" + attrPatrametr + "%] > p > span").last();

        return element;
    }
}
