package service;

import org.bson.BSON;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOptions;
import com.mongodb.util.JSON;

import static java.util.Arrays.asList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class MongoDBDAO {

	public final static String HOST_NAME = "localhost";
	public final static int PORT_NUMBER = 27017;
	public final static String DB_NAME = "medical-leftovers";
	public final static String COLLECTION_NAME = "medicals";

	public static void addMedical(String medicalJson) {
		MongoClient mongoClient = new MongoClient(HOST_NAME, PORT_NUMBER);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

		BasicDBObject dbObject = (BasicDBObject) JSON.parse(medicalJson);
		collection.insertOne(new Document(dbObject));
		
		mongoClient.close();
	}

	public static String getMedical(String location, String medicalName) {
		MongoClient mongoClient = new MongoClient(HOST_NAME, PORT_NUMBER);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
		
		Document foundDocument = collection.find(new BasicDBObject("location", location).append("name", medicalName)).first();
		mongoClient.close();
		
		return foundDocument.toJson();
	}

	public static void addLeftover(String location, String medicalName, String leftoverJson) {
		MongoClient mongoClient = new MongoClient(HOST_NAME, PORT_NUMBER);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
		
		System.out.println((BasicDBObject) JSON.parse(leftoverJson));
		Document foundDBMedical = collection.find(new BasicDBObject("location", location).append("name", medicalName)).first();
		String pushJson = "{$push:{leftovers:" + leftoverJson + "}}";
		BasicDBObject push = (BasicDBObject) JSON.parse(pushJson);
		System.out.println(foundDBMedical);
		//collection.findOneAndUpdate(foundDBMedical, push);
		collection.updateOne(foundDBMedical, push);
		System.out.println("DOC: " + collection.find().first());
		mongoClient.close();
	}

	public static void getLeftover(String location, String medicalName, String leftoverName) {
		MongoClient mongoClient = new MongoClient(HOST_NAME, PORT_NUMBER);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
		
		//Document foundDBMedical = collection.find(new BasicDBObject("location", location).append("name", medicalName)).first();
	}

	

}
