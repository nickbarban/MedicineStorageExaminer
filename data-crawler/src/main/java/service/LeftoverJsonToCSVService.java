package service;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.commons.io.FileUtils;
import org.json.CDL;

public class LeftoverJsonToCSVService {

	public static void main(String[] args) {
		String jsonString = "{\"leftovers\":[{\"localId\":1,\"name\":\"Папаверіна г/хлорид 20мг/ мл 2.0\",\"measure\":\"амп\",\"quantity\":100.0},"
									+ "{\"localId\":2,\"name\":\"Еуфіллін 2%- 5.0\",\"measure\":\"амп\",\"quantity\":130.0},"
											+ "{\"localId\":4,\"name\":\"Но - х - ша\",\"measure\":\"амп\",\"quantity\":220.0},"
											+ "{\"localId\":5,\"name\":\"Адреналін 0,18%-1.0\",\"measure\":\"амп\",\"quantity\":35.0},"
											+ "{\"localId\":111,\"name\":\"Пентоксифілін 2% 5.0\",\"measure\":\"амп\",\"quantity\":100.0}],"
											+ "\"city\":\"Ананьївський район\",\"name\":\"Центральна районна лікарня\"}";
		Map<String, String> map = new LinkedHashMap<>();
		JSONObject output = new JSONObject(map);
		JSONArray docs = response.getJSONArray("leftovers");
		File file=new File("yourpath/fromJSON.csv");
		String csv = CDL.toString(docs);
		FileUtils.writeStringToFile(file, csv);
				

	}

}
