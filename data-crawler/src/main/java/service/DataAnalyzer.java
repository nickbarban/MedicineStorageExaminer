package service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public final class DataAnalyzer {
	
	private final String[] tableHeadRegex = {"(\\s*) (.*) [ ](.*) (.*)", 
									"(\\s*) (.*) [ ](.*) (.*)" };
	private final String[] measures = {"амп", "шт", "фл", "табл", "уп", "кг", "пари"};
	
	private List<String> analyzedData = new ArrayList<>();
	private List<String> unAnalyzedData = new ArrayList<>();

	public void analyze(String textFromPDF) {
		StringTokenizer tokenizer = new StringTokenizer(textFromPDF, "\r\n");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (isHead(token)) {
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (isTableLine(token)) {
						analyzedData.add(token);
					} else {
						if (token.matches("(\\s*)(\\d+)(\\W?)(\\s+)(.+)")) {
							unAnalyzedData.add(token);
						}
					}
						
				}
				
			}
		}
	}

	private boolean isHead(String token) {
		for (int i = 0; i < tableHeadRegex.length; i++) {
			Pattern pattern = Pattern.compile(tableHeadRegex[i]);
			Matcher matcher = pattern.matcher(token);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	private boolean isTableLine(String token) {
		for (int i = 0; i < measures.length; i++) {
			if (token.matches("(\\d+)(.+)(\\s+)" + measures[i] + "(\\W?)(\\s+)(.*)(\\d+)(\\s*)")) {
				return true;
			}
		}
		return false;
	}

	public List<String> getAnalyzedData() {
		return analyzedData;
	}

	public List<String> getUnAnalyzedData() {
		return unAnalyzedData;
	}
	
	
	

}
