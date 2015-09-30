package service;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import domain.Leftover;
import domain.Medical;

public class PdfExtractorTestClient {	
	
    public static void main(String[] args) {
    	/*// delete this line when SiteParserServiceBean.parse() will execute correctly
    	ClassLoader classLoader = PdfExtractorTestClient.class.getClassLoader(); */
    	List<Medical> medicals = new ArrayList<>();
    	List<URL> unParsableLinks = new ArrayList<>();
    	try {
			Document site = SiteDownloader.download();
			//System.out.println(site.baseUri());
			SiteParserServiceBean siteParserService = new SiteParserServiceBean(site);
			Map<Medical, String> links = siteParserService.parse();
			for (Medical medical : links.keySet()) { 
				URL link = new URL(links.get(medical)); 
				//InputStream inputStream = new FileInputStream(new File(link));
				InputStream inputStream = new BufferedInputStream(link.openStream());
				String textFromPDF = PdfExtractor.extract(inputStream);
				DataAnalyzer analyzer = new DataAnalyzer();
				analyzer.analyze(textFromPDF);
				List<String> analyzedData = analyzer.getAnalyzedData();
				List<String> unAnalyzedData = analyzer.getUnAnalyzedData();
				if (analyzedData.size() > 0) {
					for (String string : analyzedData) {
						int localId = 0;
						double quantity = -1;
						String measure = null;
						String[] splitted = string.split(" ");
												
						if (splitted[0].matches("(\\d+)(\\W*)")) {
							localId = Integer.parseInt(splitted[0].trim().split("\\W")[0]);
						} //else System.out.println("WRONG localID: " + splitted[0]);
						
						if (splitted[splitted.length - 1].matches("(\\d+)")) {
							
							quantity = Double.parseDouble(splitted[splitted.length - 1].trim());
						} else {
							if (splitted[splitted.length - 1].matches("(\\d+)(\\W)(\\d+)")) {
								String[] splittedQuantity = splitted[splitted.length - 1].trim().split(",");
								String dottedQuantity = splittedQuantity[0] + "." + splittedQuantity[1];
								quantity = Double.parseDouble(dottedQuantity.trim());
							}
						}
						
						StringBuilder name = new StringBuilder();
						for (int i = 1; i < splitted.length-2; i++) {
							if (!splitted[i].isEmpty() && !splitted[i].matches("(\\s+)")) {
								name.append(splitted[i].trim() + " ");
							}
						}
						
						measure = splitted[splitted.length - 2].trim();
						
						Leftover leftover = new Leftover(localId, name.toString().trim(), measure, quantity);
						System.out.println(leftover);
						medical.getLeftovers().add(leftover);
					}
				} else {
					unParsableLinks.add(link);  
				}
				medicals.add(medical);
			} 
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PDFExtractorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
