package test;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sq.utils.PropertiesUtils;
import com.sq.whcl.transport.model.ChatLogFlow;

public class Tss {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		S
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dda = sdf.parse("2015-03-30 12:00:00");
			System.out.println(dda.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 public static DB db; 
		
	 protected DBCollection dao = null;
	 
	 public static DBCollection getInstance(String table){
		 return db.getCollection(table);
	 }
	
	public boolean start() {
		MongoClient mongoClient = null;
		try {
			String ip = "127.0.0.1";//PropertiesUtils.getPropertiesValue("jdbc.properties", "mongodb_ip");
			String port = PropertiesUtils.getPropertiesValue("jdbc.properties","mongodb_port");
			mongoClient= new MongoClient(ip, Integer.parseInt(port));	
			db = mongoClient.getDB("test");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean stop() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
