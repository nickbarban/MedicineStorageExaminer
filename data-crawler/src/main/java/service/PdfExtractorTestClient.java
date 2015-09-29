package service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import domain.Leftover;
import domain.Medical;

public class PdfExtractorTestClient {	
	
    public static void main(String[] args) {
    	// delete this line when SiteParserServiceBean.parse() will execute correctly
    	ClassLoader classLoader = PdfExtractorTestClient.class.getClassLoader(); 
    	List<Medical> medicals = new ArrayList<>();
    	List<URI> unParsableLinks = new ArrayList<>();
    	try {
			Document site = SiteDownloader.download();
			//System.out.println(site.baseUri());
			SiteParserServiceBean siteParserService = new SiteParserServiceBean(site);
			Map<Medical, String> links = siteParserService.parse();
			//for (Medical medical : links.keySet()) { // uncomment this
				//URI link = new URI(links.get(medical)); // uncomment this
				InputStream inputStream = new FileInputStream((classLoader.getResource("example.pdf").getFile()));
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
							//System.out.println("localID: " + localID);
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
						//medical.getLeftovers().add(leftover);
					}
				} else {
					//unParsableLinks.add(link);  // uncomment this
				}
				// medicals.add(medical); // uncomment this
			//} // uncomment this
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ catch (PDFExtractorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	/*List<String> unParsableFiles = new ArrayList<>();
    	final String[] fileNames = {"example.pdf""ananiev_cpsmd.pdf", "ananiev_crl.pdf", "arciz_cpsmd.pdf", "arciz_crl.pdf",
    								"balta_cpsmd.pdf", "balta_crl.pdf", "bdnistrov_bd.pdf", "bdnistrov_crl.pdf",
    								"bdnistrov_kvd.pdf", "bdnistrov_mbl.pdf", "bdnistrov_mu_25.08.15.pdf", 
    								"bdnistrov_skrl.pdf", "bdnistrov_td2.pdf", "berezivka_cpsmd.pdf",
    								"berezivka_crl.pdf", "bilyaivka_cpsmd.pdf", "bilyaivka_crl.pdf", 
    								"bolgrad_cpsmd.pdf", "bolgrad_crl.pdf", "cemd.pdf", "cpsmd28.pdf", "cpz.pdf",
    								"cz_25.08.15.pdf", "dkvl.pdf", "ed.pdf", "example.pdf", "fil.pdf", 
    								"frunzivka_cpsmd.pdf", "frunzivka_crl.pdf", "ggivvv.pdf", "givvv.pdf",
    								"illich_ibl.pdf", "ivanivka_cpsmd.pdf", "ivanivka_crl.pdf", 
    								"izmail_bd_25.08.15.pdf", "izmail_cpsmd.pdf", "izmail_crl.pdf",
    								"izmail_dbl.pdf", "izmail_il.pdf", "izmail_il_25.08.15.pdf", 
    								"izmail_ipc.pdf", "kd.pdf", "kdc_25.08.15.pdf", "kiliya_cpsmd.pdf",
    								"kiliya_crl.pdf", "kodima_cpsmd.pdf", "kodima_crl.pdf", "komintern_cpsmd.pdf",
    								"komintern_crl.pdf", "kotovsk_cpsmd.pdf", "kotovsk_ml.pdf", "krokn_cpsmd.pdf",
    								"ksp.pdf", "kvd.pdf", "lpu.xlsx", "lubashivka_cpsmd.pdf", "lubashivka_crl.pdf",
    								"mc.pdf", "mikolaivka_crl.pdf", "mkl3_1.9.pdf", "msec_01.09.15.pdf", "msp1.pdf",
    								"msp21.pdf", "od.pdf", "omshmd.pdf", "oodkl.pdf", "ookl.pdf", "ootkl.pdf",
    								"ovidiopol_cpsmd.pdf", "ovidiopol_crl.pdf", "pl2.pdf", "pl3.pdf", "pl4.pdf",
    								"reni_cpsmd.pdf", "reni_crl.pdf", "rozdilna_cpsmd.pdf", "rozdilna_crl_1.9.pdf", 
    								"sarata_cpsmd.pdf", "sarata_crl.pdf", "savran_cpsmd.pdf", "savran_crl.pdf",
    								"shiryaivo_cpsmd.pdf", "shiryaivo_crl.pdf", "sme.pdf", "snid.pdf", "spk.pdf",
    								"tarutino_cpsmd.pdf", "tarutino_crl.pdf", "tatarbunar_cpsmd.pdf", "teplodar_cml.pdf", 
    								"tub1.pdf", "ujniy_ml.pdf", "vmih_cpsmd.pdf", "vmih_crl.pdf"}; 
    	ClassLoader classLoader = PdfExtractorTestClient.class.getClassLoader();
    	InputStream inputStream = null;
    	DataAnalyzer analyzer = null;
    	for (int i = 0; i < fileNames.length; i++) {
    		analyzer = new DataAnalyzer();
    		System.out.println("--------------" + fileNames[i] + "--------------");
    		try {
				inputStream = new FileInputStream(new File(classLoader.getResource(fileNames[i]).getFile()));
				String textFromPDF = PdfExtractor.extract(inputStream);
				//System.out.println(textFromPDF);
				analyzer.analyze(textFromPDF);
				List<String> analyzedData = analyzer.getAnalyzedData();
				List<String> unAnalyzedData = analyzer.getUnAnalyzedData();
				
				if (analyzedData.size() > 0) {
					System.out.println(" analyzedData:" + analyzedData.size());
					System.out.println(" unAnalyzedData:" + unAnalyzedData.size());
				} else {
					unParsableFiles.add(fileNames[i]);
				}
				
				
			} catch (FileNotFoundException | PDFExtractorException e) {
				e.printStackTrace();
			}
		}
    	try {
			inputStream = new FileInputStream(new File(classLoader.getResource(unParsableFiles.get(0)).getFile()));
			String textFromPDF = PdfExtractor.extract(inputStream);
			System.out.println(unParsableFiles.get(0));
			System.out.println(textFromPDF);
		} catch (FileNotFoundException | PDFExtractorException e) {
			e.printStackTrace();
		}*/
		
    }
}
