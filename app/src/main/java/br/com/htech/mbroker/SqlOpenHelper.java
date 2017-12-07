package br.com.htech.mbroker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlOpenHelper extends SQLiteOpenHelper{
	public static final String  DBNAME = "mbdb.sqlite";
	public static final int VERSION = 1;
	public static final String HOME = "home";
	public static final String CLIENTS = "clients";
	public static final String IMAGES = "home_images";
	
	//imoveis
	public static final String HOME_ID = "homeId";
	public static final String HOME_MAIN_IMAGE = "mainImage";
	public static final String HOME_ADDRESS = "address";
	public static final String HOME_OWNER = "owner";
	public static final String HOME_SALE = "sale";
	public static final String HOME_RENT = "rent";
	public static final String HOME_TYPE = "type";
	public static final String HOME_ROOMS = "rooms";
	public static final String HOME_EMAIL = "email";
	public static final String HOME_PHONE = "home_phone";
	public static final String HOME_DESCRIPTION = "description";
	public static final String HOME_SOLD_RENTED = "sold_rented";
	
	//clientes
	public static final String CLIENT_ID = "clientId";
	public static final String CLIENT_NAME = "clientName";
	public static final String CLIENT_PHONE = "clientPhone";
	public static final String CLIENT_EMAIL = "client_email";
	public static final String CLIENT_SOLICIT = "clientSolicit";
	public static final String CLIENT_BUY = "clientBuy";
	public static final String CLIENT_RENT = "clientRent";
	public static final String CLIENT_ACCOMP = "clientAcc";
	
	//imagens	
	public static final String IMAGE_ID = "imageId";
	public static final String IMAGE_PATH = "imagePath";
	public static final String IMAGE_HOME_ID = "imageHomeId";
	
	private static SqlOpenHelper mInstance = null;
	

	private static Context mCtx;

    

    public static SqlOpenHelper getInstance() {
        /** 
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information: 
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new SqlOpenHelper(mCtx.getApplicationContext());
        }
        return mInstance;
    }
	
	
	public SqlOpenHelper(Context context){
		super(context, DBNAME, null, VERSION);
		mCtx = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createDatabase(db);
		
	}

	private void createDatabase(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table "+ HOME + " (" +
				HOME_ID +" integer primary key autoincrement not null, "+
				HOME_MAIN_IMAGE +" text, "+
				HOME_ADDRESS +" text, "+
				HOME_OWNER +" text, "+
				HOME_SALE +" text, "+
				HOME_RENT +" text, "+
				HOME_TYPE +" text, "+
				HOME_ROOMS +" integer, "+
				HOME_EMAIL +" text, "+
				HOME_PHONE +" text, "+
				HOME_DESCRIPTION +" text, "+
				HOME_SOLD_RENTED +" text "+
				");"
				);
		
		db.execSQL("create table "+ CLIENTS + "(" +
				CLIENT_ID +" integer primary key autoincrement not null, "+
				CLIENT_NAME +" text, "+
				CLIENT_PHONE +" text, "+
				CLIENT_EMAIL +" integer, "+
				CLIENT_BUY +" integer, "+
				CLIENT_RENT +" text, "+
				CLIENT_SOLICIT +" text, "+
				CLIENT_ACCOMP +" integer "+
				");"
				);
		
		db.execSQL("create table "+ IMAGES  + "(" +
				IMAGE_ID+" integer primary key autoincrement not null, "+
				IMAGE_PATH +" text, "+
				IMAGE_HOME_ID +" integer "+
				");"
				);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
    public void insertHome(String imagePath, String address, String owner, String sale, 
    		String rent, String type, int rooms, String email, String phone, String description, String soldRented){
    	
        SQLiteDatabase db = this.getWritableDatabase();
         
        ContentValues values = new ContentValues();
        values.put(HOME_MAIN_IMAGE, imagePath);
        values.put(HOME_ADDRESS, address);
        values.put(HOME_OWNER, owner);
        values.put(HOME_PHONE, phone);
        values.put(HOME_SALE, sale); 
        values.put(HOME_RENT, rent);   
        values.put(HOME_ROOMS, rooms);
        values.put(HOME_EMAIL, email);
        values.put(HOME_DESCRIPTION, description);
        values.put(HOME_SOLD_RENTED, soldRented);  
        values.put(HOME_TYPE, type); 
          
        // Inserting Row
        db.insert(HOME, null, values);
        db.close(); // Closing database connection
    }
	
	
	
    public void insertClient(String name, String phone, String email, int buy,  int rent, String solicit, int accomplished){
        SQLiteDatabase db = this.getWritableDatabase();
         
        ContentValues values = new ContentValues();
        values.put(CLIENT_NAME, name);
        values.put(CLIENT_PHONE, phone);
        values.put(CLIENT_EMAIL, email);
        values.put(CLIENT_BUY, buy);
        values.put(CLIENT_RENT, rent);
        values.put(CLIENT_SOLICIT, solicit);       
        values.put(CLIENT_ACCOMP, accomplished);
        		   
        // Inserting Row
        db.insert(CLIENTS, null, values);
        db.close(); // Closing database connection
    }
    
    public Boolean updateClient(long id, String name, String phone, String email, int buy,  int rent, String solicit, int accomplished){
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();    		
            
            String updateQuery = "UPDATE " + CLIENTS + " SET "+
            		CLIENT_NAME +" = '" + name +"', "+
            		CLIENT_PHONE +" = '" + phone +"', "+
            		CLIENT_EMAIL +" = '" + email +"', "+
            		CLIENT_BUY +" = " + buy +", "+
            		CLIENT_RENT +" = " + rent +", "+
            		CLIENT_SOLICIT +" = '" + solicit +"', "+
            		CLIENT_ACCOMP +" = " + accomplished +" "+
            		" WHERE "+ CLIENT_ID +" = "+ id;
    		
    		
    		db.execSQL(updateQuery);
    		db.close();
    	}
    	catch(Exception e){
    		return false;
    	}
    	
    	return true;
    }
    
    public Boolean clientAcc(long id, int accomplished){
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();    		
            
            String updateQuery = "UPDATE " + CLIENTS + " SET "+
            		CLIENT_ACCOMP +" = " + accomplished +" "+
            		" WHERE "+ CLIENT_ID +" = "+ id;    		
    		
    		db.execSQL(updateQuery);
    		db.close();
    	}
    	catch(Exception e){
    		return false;
    	}
    	
    	return true;
    }
    
    public Boolean deleteClient(long id){
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();    		
            
            String deleteeQuery = "DELETE FROM " + CLIENTS + 
            		" WHERE "+ CLIENT_ID +" = "+ id;
    		    		
    		db.execSQL(deleteeQuery);
    		db.close();
    	}
    	catch(Exception e){
    		return false;
    	}
    	
    	return true;
    }
    
    public Boolean deleteImages(long id){
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();    		
            
            String deleteeQuery = "DELETE FROM " + IMAGES + 
            		" WHERE "+ IMAGE_HOME_ID +" = "+ id;
    		    		
    		db.execSQL(deleteeQuery);
    		db.close();
    	}
    	catch(Exception e){
    		return false;
    	}
    	
    	return true;
    }
    
    
    
    public Boolean deleteHome(long id){
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();    		
            
            String deleteeQuery = "DELETE FROM " + HOME + 
            		" WHERE "+ HOME_ID +" = "+ id;
    		    		
    		db.execSQL(deleteeQuery);
    		db.close();
    	}
    	catch(Exception e){
    		return false;
    	}
    	
    	return true;
    }
    
    public Boolean updateHome(String imagePath, String address, String owner, String sale, 
    		String rent, String type, int rooms, String email, String phone, String description, String soldRented, long id){
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();    		
            
            String updateQuery = "UPDATE " + HOME + " SET "+
            		HOME_MAIN_IMAGE +" = '" + imagePath +"', "+
            		HOME_ADDRESS +" = '" + address +"', "+
            		HOME_OWNER +" = '" + owner +"', "+
            		HOME_SALE +" = '" + sale +"', "+
            		HOME_RENT +" = '" + rent +"', "+
            		HOME_ROOMS +" = " + rooms +", "+
            		HOME_EMAIL +" = '" + email +"', "+
            		HOME_PHONE +" = '" + phone +"', "+
            		HOME_DESCRIPTION +" = '" + description +"', "+
            		HOME_SOLD_RENTED +" = '" + soldRented +"', "+
            		HOME_TYPE +" = '" + type +"' "+            		
            		" WHERE "+ HOME_ID +" = "+ id;
    		
    		
    		db.execSQL(updateQuery);
    		db.close();
    	}
    	catch(Exception e){
    		return false;
    	}
    	
    	return true;
    }
    
    public Boolean homeSoldRented(String soldRented, long id){
    	
    	try{
    		SQLiteDatabase db = this.getReadableDatabase();    		
            
            String updateQuery = "UPDATE " + HOME + " SET "+
            		HOME_SOLD_RENTED +" = '" + soldRented +"' "+            		           		
            		" WHERE "+ HOME_ID +" = "+ id;
    		    		
    		db.execSQL(updateQuery);
    		db.close();
    	}
    	catch(Exception e){
    		return false;
    	}
    	
    	return true;
    }
    
    public void insertImage(String path, String homeId){
        SQLiteDatabase db = this.getWritableDatabase();
         
        ContentValues values = new ContentValues();
        values.put(IMAGE_PATH, path);
        values.put(IMAGE_HOME_ID, homeId);
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date date = new Date();
        values.put(KEY_DTALTERACAO, dateFormat.format(date));*/
          
        // Inserting Row
        db.insert(IMAGES, null, values);
        db.close(); // Closing database connection
    }
    
 
    
    
    public List<String[]> getAllClients(){
    	List<String[]> labels = new ArrayList<String[]>();
    	String[] registro = new String[4];
  
        // Select All Query
        String selectQuery = "SELECT " +
        		CLIENT_ID +", " +
        		CLIENT_NAME+", " +
        		CLIENT_BUY +", " +
        		CLIENT_RENT +" " +
        		"FROM " + CLIENTS + 
        		" WHERE "+ CLIENT_ACCOMP +" = "+" 0 "+
        		" ORDER BY " +
        		CLIENT_NAME +" asc" ;
        
        try{
            
        	SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	      
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	registro[0] = cursor.getString(0);
	            	registro[1] = cursor.getString(1);
	            	registro[2] = cursor.getString(2);
	            	registro[3] = cursor.getString(3);
	                labels.add(registro);
	                registro = new String[4];
	            } while (cursor.moveToNext());
	        }
	        cursor.close();
	        db.close();
        }
        catch(Exception e){
        	String me = e.getMessage();
        }
         
        return labels;
        
        
    }
    
    public List<String[]> getAllHomes(){
    	List<String[]> labels = new ArrayList<String[]>();
    	String[] registro = new String[5];
  
        // Select All Query
        String selectQuery = "SELECT " +
        		HOME_ID +", " +
        		HOME_MAIN_IMAGE +", " +
        		HOME_OWNER +",  " +
        		HOME_RENT +",  " +
        		HOME_SALE +"  " +
        		"FROM " + HOME + 
        		" WHERE "+ HOME_SOLD_RENTED +" = "+" '0' "+
        		" ORDER BY "+ HOME_OWNER +" asc" ;
        
        try{
            
        	SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	      
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	registro[0] = cursor.getString(0);
	            	registro[1] = cursor.getString(1);
	            	registro[2] = cursor.getString(2);
	            	registro[3] = cursor.getString(3);
	            	registro[4] = cursor.getString(4);
	                labels.add(registro);
	                registro = new String[5];
	            } while (cursor.moveToNext());
	        }
	        cursor.close();
	        db.close();
        }
        catch(Exception e){
        	String me = e.getMessage();
        }
         
        return labels;
        
        
    }
    
    public String[] getHome(long homeId){
    	
    	String[] registro = new String[12];
  
        // Select All Query
        String selectQuery = "SELECT " +
        		HOME_ID +", " +
        		HOME_MAIN_IMAGE +", " +        		
        		HOME_OWNER +",  " +
        		HOME_SOLD_RENTED +",  " +
        		HOME_ADDRESS +", " +
        		HOME_EMAIL +", " +
        		HOME_PHONE +", " +
        		HOME_SALE +", " +
        		HOME_RENT +", " +
        		HOME_TYPE +", " +
        		HOME_DESCRIPTION +", " +
        		HOME_ROOMS +"  " +
        		"FROM " + HOME + " WHERE " + homeId + " = " + HOME_ID ;
        
        try{
            
        	SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	      
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            //do {
	            	registro[0] = cursor.getString(0);
	            	registro[1] = cursor.getString(1);
	            	registro[2] = cursor.getString(2);
	            	registro[3] = cursor.getString(3);
	            	registro[4] = cursor.getString(4);
	            	registro[5] = cursor.getString(5);
	            	registro[6] = cursor.getString(6);
	            	registro[7] = cursor.getString(7);
	            	registro[8] = cursor.getString(8);
	            	registro[9] = cursor.getString(9);	
	            	registro[10] = cursor.getString(10);
	            	registro[11] = cursor.getString(11);
	               // registro = new String[4];
	           //} while (cursor.moveToNext());
	        }
	        cursor.close();
	        db.close();
        }
        catch(Exception e){
        	String me = e.getMessage();
        }
         
        return registro;
        
        
    }
    
    public String[] getClient(long clientId){
    	
    	String[] registro = new String[8];
  
        // Select All Query
        String selectQuery = "SELECT " +
        		CLIENT_ID +", " +
        		CLIENT_ACCOMP +", " +        		
        		CLIENT_BUY +",  " +
        		CLIENT_RENT +",  " +
        		CLIENT_EMAIL +",  " +
        		CLIENT_NAME +", " +
        		CLIENT_PHONE  +", " +
        		CLIENT_SOLICIT +" " +
        		"FROM " + CLIENTS + " WHERE " + CLIENT_ID + " = " + clientId ;
        
        try{
            
        	SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	      
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            //do {
	            	registro[0] = cursor.getString(0);
	            	registro[1] = cursor.getString(1);
	            	registro[2] = cursor.getString(2);
	            	registro[3] = cursor.getString(3);
	            	registro[4] = cursor.getString(4);
	            	registro[5] = cursor.getString(5);
	            	registro[6] = cursor.getString(6);
	            	registro[7] = cursor.getString(7);
	               // registro = new String[4];
	           //} while (cursor.moveToNext());
	        }
	        cursor.close();
	        db.close();
        }
        catch(Exception e){
        	String me = e.getMessage();
        }
         
        return registro;
        
        
    }
    
    public ArrayList<String> getAllImages(long idHome){
    	ArrayList<String> labels = new ArrayList<String>();
         
        // Select All Query
        // Select All Query
        String selectQuery = "SELECT " +
        		IMAGE_PATH +"  " +
        		"FROM " + IMAGES +
        		" WHERE '" +idHome +
        		"' = " + IMAGE_HOME_ID; //+ " ORDER BY HOME_OWNER DESC" ;
        try{
        
        	SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        
	        //labels.add("");
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	                labels.add(cursor.getString(0));
	            } while (cursor.moveToNext());
	        }
	        cursor.close();
	        db.close();
        }
        catch(Exception e){
        	String me = e.getMessage();
        }
         
        // closing connection

         
        // returning lables
        return labels;
    }
    
    public String getMaxIdHome(){        
         
    	String max = "0";
        // Select All Query
        // Select All Query
        String selectQuery = "SELECT " +
        		HOME_ID +"  " +
        		"FROM " + HOME +
        		" ORDER BY " + HOME_ID +" DESC"; //+ " ORDER BY HOME_OWNER DESC" ;
        try{
        
        	SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        
	        //labels.add("");
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	        	max = cursor.getString(0);
	        }
	        cursor.close();
	        db.close();
        }
        catch(Exception e){
        	String me = e.getMessage();
        }
         
        // closing connection

         
        // returning lables
        return max;
    }

			
	

}
