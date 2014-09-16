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
        public static final String SQL_CREATE_DBINFO_TABLE =
        	    "CREATE TABLE " + TABLE_NAME + " (" +
        	    _ID + " INTEGER PRIMARY KEY," +
        	    CN_KEY + " TEXT," +
        	    CN_VALUE + " TEXT" + 
        	    " )";
      //SQL for drop the table
        public static final String SQL_DELETE_DBINFO_TABLE =
        	    "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public static abstract class DAYS implements BaseColumns {
        public static final String TABLE_NAME = "DAYS";
        //public static final String CN_ID = "id";
        public static final String CN_YEAR = "year";
        public static final String CN_MONTH = "month";
        public static final String CN_DAY = "day";
        
        public static final String SQL_CREATE_DAYS_TABLE = 
        		"CREATE TABLE " + TABLE_NAME + " (" +
                	    _ID + " INTEGER PRIMARY KEY," +
                	    CN_YEAR + " INT," +
                	    CN_MONTH + " INT," +
                	    CN_DAY + " INT" +
                	    " )";
        
        public static final String SQL_DELETE_DAYS_TABLE =
        	    "DROP TABLE IF EXISTS " + TABLE_NAME;
        
        /*INSERT INTO table_name VALUES (value1,value2,value3,...);*/
        public static final String SQL_INSERT_DAY = 
        		"INSERT INTO "+TABLE_NAME+" ("+CN_YEAR+","+CN_MONTH+","+CN_DAY+") VALUES (?,?,?);";
        
        public static final String SQL_SELECT_DAY = 
        		"SELECT * FROM " + DAYS.TABLE_NAME + "WHERE " + DAYS.CN_YEAR + " =? AND " + DAYS.CN_MONTH + " =? AND " + DAYS.CN_DAY + " =?";
    }
    
    public static abstract class STAMPS implements BaseColumns {
        public static final String TABLE_NAME = "STAMPS";
        public static final String CN_DAY_ID = "day_id";
        public static final String CN_WAY = "way";
        public static final String CN_TIME = "time";
        
        public static final String WAY_IN = "IN";
        public static final String WAY_OUT = "OUT";
        
        public static final String SQL_CREATE_STAMPS_TABLE = 
        		"CREATE TABLE " + TABLE_NAME + " (" +
                	    _ID + " INTEGER PRIMARY KEY," +
                	    CN_WAY + " TEXT," +
                	    CN_TIME + " TEXT," +
                	    CN_DAY_ID + " INT," +
                	    "FOREIGN KEY (" +CN_DAY_ID+ ") REFERENCES "+ DAYS.TABLE_NAME +" ("+ DAYS._ID +") ON DELETE CASCADE" +
                	    " )";
        
        public static final String SQL_DELETE_STAMPS_TABLE =
        	    "DROP TABLE IF EXISTS " + TABLE_NAME;
        
        public static final String SQL_ADD_STAMP_IN = 
        		"INSERT INTO "+TABLE_NAME+" ("+CN_DAY_ID+","+CN_WAY+","+CN_TIME+") VALUES (?,?,?);";
        
        public static final String SQL_GET_STAMPS_OF_A_DAY =
        		"SELECT " + STAMPS.CN_TIME +","+STAMPS.CN_WAY+" FROM " + STAMPS.TABLE_NAME + 
        		" JOIN "+ DAYS.TABLE_NAME +
        		" ON "+STAMPS.TABLE_NAME+"."+STAMPS.CN_DAY_ID +"="+DAYS.TABLE_NAME+"."+ DAYS._ID +
        		" WHERE " + DAYS.CN_YEAR + " =? AND " + DAYS.CN_MONTH + " =? AND " + DAYS.CN_DAY + " =?"+
        		"ORDER BY " + CN_TIME ;
    }
    
}
