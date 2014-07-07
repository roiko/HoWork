package com.ioroiko.howork;

import android.provider.BaseColumns;

public final class HoWorkContract {
	/*
	 * Questa classe serve per gestire il database SQLite
	 * */

	public HoWorkContract(){}
	
	/* One inner class for each table
	 * Implement BaseColumns allows to manage the automatically 
	 * created column _ID (as needed by the framework) 
	 * */
    public static abstract class DBINFO implements BaseColumns {
        public static final String TABLE_NAME = "DBINFO";
        public static final String CN_KEY = "key";
        public static final String CN_VALUE = "value";
        
        //SQL for create the table
        public static final String SQL_CREATE_DBINFO =
        	    "CREATE TABLE " + TABLE_NAME + " (" +
        	    _ID + " INTEGER PRIMARY KEY," +
        	    CN_KEY + " TEXT," +
        	    CN_VALUE + " TEXT" + 
        	    " )";
      //SQL for drop the table
        public static final String SQL_DELETE_ENTRIES =
        	    "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public static abstract class DAYS implements BaseColumns {
        public static final String TABLE_NAME = "DAYS";
        //public static final String CN_ID = "id";
        public static final String CN_YEAR = "year";
        public static final String CN_MONTH = "month";
        public static final String CN_DAY = "day";
        
        public static final String SQL_CREATE_DAYS = 
        		"CREATE TABLE " + TABLE_NAME + " (" +
                	    _ID + " INTEGER PRIMARY KEY," +
                	    CN_YEAR + " INTEGER" +
                	    CN_MONTH + " INT" +
                	    CN_DAY + " INT" +
                	    " )";
        
        public static final String SQL_DELETE_DAYS =
        	    "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public static abstract class STAMPS implements BaseColumns {
        public static final String TABLE_NAME = "STAMPS";
        public static final String CN_DAY_ID = "day_id";
        public static final String CN_WAY = "way";
        public static final String CN_TIME = "time";
        
        public static final String WAY_IN = "IN";
        public static final String WAY_OUT = "OUT";
        
        public static final String SQL_CREATE_DAYS = 
        		"CREATE TABLE " + TABLE_NAME + " (" +
                	    _ID + " INTEGER PRIMARY KEY," +
                	    CN_WAY + " TEXT" +
                	    CN_TIME + " TEXT" +
                	    CN_DAY_ID + " INTEGER" +
                	    "CONSTRAINT" +CN_DAY_ID+ "REFERENCES "+ DAYS.TABLE_NAME +"("+ DAYS._ID +") ON DELETE CASCADE)" +
                	    " )";
        
        public static final String SQL_DELETE_STAMPS =
        	    "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
}
