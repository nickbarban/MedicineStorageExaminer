package service;

import com.google.common.base.Preconditions;
import domain.Medical;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class LinksParserServiceImpl implements LinksParserService {

    private Document htmlDocument;

    public LinksParserServiceImpl(Document htmlDocument) {
        this.htmlDocument = htmlDocument;
    }

    public Map<Medical, String> parse() {
        Preconditions.checkNotNull(htmlDocument, "HTML document is not specified!");
        Map<Medical, String> links = new HashMap<>();
        Elements rows = htmlDocument.select("#main > div > div > table > tbody > tr > td[width=19%] ");
        for (Element row : rows) {
            Element subRow = row.select("p > span > a").last();

            if (subRow == null) {
                continue;
            }
            Medical medical = new Medical();
            medical.setName(subRow.attr("href"));
            links.put(medical, subRow.attr("href"));
        }
        return links;
    }
}
