package com.example.finalmadproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.finalmadproject.Settings.SongList;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.finalmadproject.Settings.RingingTones.song.SONG_STATUS;
import static com.example.finalmadproject.Settings.RingingTones.song.TABLE_SONG_NAME;
import static com.example.finalmadproject.TaskManagement.Subject.SubjectEntry.SUBJECT_ID;
import static com.example.finalmadproject.TaskManagement.Subject.SubjectEntry.SUBJECT_NAME;
import static com.example.finalmadproject.TaskManagement.Subject.SubjectEntry.TABLE_NAME;
import static com.example.finalmadproject.TaskManagement.Task.TaskEntry.TABLE2_NAME;
import static com.example.finalmadproject.TaskManagement.Task.TaskEntry.TASK_DATE;
import static com.example.finalmadproject.TaskManagement.Task.TaskEntry.TASK_DESCRIPTION;
import static com.example.finalmadproject.TaskManagement.Task.TaskEntry.TASK_ID;
import static com.example.finalmadproject.TaskManagement.Task.TaskEntry.TASK_NAME;
import static com.example.finalmadproject.TaskManagement.Task.TaskEntry.TASK_SUBJECT;

//Salitas DB imports
import static com.example.finalmadproject.Settings.RingingTones.song.SONG_NAME;
import static com.example.finalmadproject.Settings.RingingTones.song.SONG_ID;
import static com.example.finalmadproject.Settings.RingingTones.song.SONG_PATH;
import static com.example.finalmadproject.Settings.RingingTones.song.TABLE_SONG_NAME;

//Table - 1 - Subject Table Information.
//Table - 2 - Task Table Information.

public class DatabaseHelper extends SQLiteOpenHelper {

    //Variables
    public static final String DATABASE_NAME = "homework_db";
    public static final int DATABASE_VERSION = 1;
    public static final String notstatus = "Not Selected";

    //TODO: SQL Queries
    //Creating the query - Table - 1
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            SUBJECT_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + SUBJECT_NAME+ " text);"; //Made a change to the key attribute

    //Creating the query - Table - 1
    public static final String DROP_TABLE = "drop table if exists "+ TABLE_NAME;

    //Creating the query - Table - 2
    public static final String CREATE_TABLE2 = "create table "+ TABLE2_NAME + "(" +
            TASK_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + TASK_NAME+ " text," + TASK_DESCRIPTION+ " text," + TASK_SUBJECT+ " text,"
            + TASK_DATE+ " text);";

    //Creating the query - Table - 2
    public static final String DROP_TABLE2 = "drop table if exists "+ TABLE2_NAME;

    //Salitha's Final String declarations
    //changed----------------------
    public static final String CREATE_SONG_TABLE = "create table " + TABLE_SONG_NAME + " (SONG_ID INTEGER PRIMARY KEY AUTOINCREMENT, SONG_NAME BLOB , SONG_STATUS BOOLEAN , SONG_PATH String)";

    //"INSERT INTO  " + TABLE3_NAME +  " ( " + COL2_3 +"  )  VALUES (  " + name  + " )" ;
    //public static final ;

    //Creating the query - Table - 1
    public static final String DROP_TABLE_SL = "drop table if exists "+ TABLE_SONG_NAME;
    private Context context;


    //Taneesha
    private static final String TABLE_NAME_T = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "list_title";
    private static final String COLUMN_DES = "list_description";

    private static final String query = "CREATE TABLE " + TABLE_NAME_T +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_DES + " TEXT);";

    //tan's DB part

    public static final String CREATE_TABLE_Login = "create table user (UID INTEGER PRIMARY KEY AUTOINCREMENT, FN text, UN text, PW text)";
    public static final String DROP_TABLE_Login = "drop table if exists user";

    //Create the constructor
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //Creating a Massage
        Log.e("Database Operations", "Database Created");
    }

    //Create new tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //tan's Table creation
        sqLiteDatabase.execSQL(CREATE_TABLE_Login);


        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE2); //Testing

        //Salitha's Table Creation.
        sqLiteDatabase.execSQL(CREATE_SONG_TABLE);


        //Taneesha table creation
        sqLiteDatabase.execSQL(query);
        Log.d("Database Operation", "Table is created");
    }

    //Update tables
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //tan's Data
        sqLiteDatabase.execSQL(DROP_TABLE_Login);


        sqLiteDatabase.execSQL(DROP_TABLE);
        sqLiteDatabase.execSQL(DROP_TABLE2);
        onCreate(sqLiteDatabase);

        //Salithas Database Related codes.
        sqLiteDatabase.execSQL(DROP_TABLE_SL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(sqLiteDatabase);


    }

    //Create a method to put information to the table - Attributes are the columns.
    public void addSubject(String name, SQLiteDatabase database)
    {
        //First we have to create a object from ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBJECT_NAME, name);

        //Creating the database inserting method.
        database.insert(TABLE_NAME, null, contentValues);
        Log.d("Database Operations", "One Subject inserted");
    }

    //Add Task Method to put task to the database.
    public void addTasks(String name, String description, String subject, String date, SQLiteDatabase database){

        //Creating a Object from the ContentValues
        ContentValues contentValues = new ContentValues();
        //contentValues.put(TASK_ID,id);
        contentValues.put(TASK_NAME,name);
        contentValues.put(TASK_DESCRIPTION,description);
        contentValues.put(TASK_SUBJECT,subject);
        contentValues.put(TASK_DATE,date);

        //Creating the database inserting method.
        database.insert(TABLE2_NAME, null, contentValues);
        Log.d("Database Operations", "One Task inserted");
    }

    //Adding Another method to populate the spinner
    public ArrayList<String> getAllSubjects() {

        //Creating the ArrayList and start the database connection
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        //Create the select query to get all the subject names from the database
        try {
            String select = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(select, null);
            if (cursor.getCount() > 0) { //Check Items are available.
                //Loop to fill the ArrayList
                while (cursor.moveToNext()) {
                    String subject_name = cursor.getString(cursor.getColumnIndex(SUBJECT_NAME));
                    list.add(subject_name);
                }
                db.setTransactionSuccessful();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    //Date creating function
    public String createDate(String Date)  {
        if(Date == null){
            return null;
        }
        //ParsePosition parsePosition = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date Corrected_date = (java.sql.Date) simpleDateFormat.parse(Date);
            simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            String newdate = simpleDateFormat.format(Corrected_date);
            System.out.println("Corrected date - " + newdate);
            return newdate;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //Create a query to display task details from the database
    public Cursor readTasks(SQLiteDatabase database)
    {
        //String Array with all the attribute names
        String [] tasks = {TASK_ID,TASK_NAME,TASK_DESCRIPTION,TASK_SUBJECT,TASK_DATE,};

        //Creating a Cursor Object
        Cursor cursor = database.query(TABLE2_NAME,tasks,null,null,null,null,null);
        return cursor;
    }

    //Crate a query to display subject details from the database
    public Cursor readSubjects(SQLiteDatabase database){

        //String Array
        String [] subject = {SUBJECT_ID,SUBJECT_NAME};

        //Creating a Cursor object
        Cursor cursor = database.query(TABLE_NAME,subject,null,null,null,null,null);
        return cursor;
    }

    //Method to read single data in the database.
    public Cursor readSubjectsName(String name, SQLiteDatabase database){
        //String formatting
        name = " '"+name+"' ";

        //String Array
        String SQL1  = "SELECT * FROM " + TABLE_NAME;
        String SQL2  = "SELECT * FROM " + TABLE_NAME + " WHERE " + SUBJECT_NAME + " = " + name ;

        //Declare the cursor object
        Cursor data = database.rawQuery(SQL2, null);
        return data;
    }


    //Creating a Method to get the ID when a String name is Given.
    public Cursor getFromItemID(String name){
        //Getting writable database
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a SQL query
        String SQL = "SELECT * " + " FROM " + TABLE2_NAME +
                " WHERE " + TASK_NAME + " = " + " '"+name +"' "; //if Not working then format the String.
        //this was not work because I was only asking for ID, Try to fetch all

        //Declare the cursor object
        Cursor data = db.rawQuery(SQL, null);
        return data;
    }

    //Create a Update method
    public boolean UpdateDetails(String old_name, String name , String Description, String subject, String date){
        //Getting Writable database.
        SQLiteDatabase db = getReadableDatabase();
//
//        //Embed new values to ContentValue
//        ContentValues values = new ContentValues();
//        values.put(TASK_NAME, name);
//        values.put(TASK_SUBJECT, subject);
//        values.put(TASK_DESCRIPTION, Description);
//        values.put(TASK_DATE, date);
//
//        //Select the row, should be updated.
//        String selection = TASK_ID

        //String formatting.
        old_name = " '"+old_name+"' ";
        name = " '"+name+"' ";
        subject = " '"+subject+"' ";
        date = " '"+date+"' ";
        Description = " '"+Description+"' ";


        //Creating the Query
        String SQL = "UPDATE " + TABLE2_NAME +
                " SET " +  TASK_NAME + " = " + name +" , " +
                TASK_DESCRIPTION + " = " + Description +" , " +
                TASK_SUBJECT + " = " + subject +" , " +
                TASK_DATE + " = " + date + " WHERE " + TASK_NAME + " = " + old_name;
        try{
            db.execSQL(SQL);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    //Create a Method to update the Subject Details.

    //Create a Method to delete the data
    public boolean deleteData(String name){
        //Getting Writable database.
        SQLiteDatabase db = getReadableDatabase();
        //String formatting
        name = " '"+name+"' ";
        //SQL
//        String SQL = "DELETE FROM " + TABLE2_NAME + " WHERE " + TASK_NAME + " = " + name;
//
//        try {
//            db.execSQL(SQL);
//            return true;
//        } catch (SQLException e){
//            e.printStackTrace();
//            return false;
//        }
        //Executing the delete method.
        return db.delete(TABLE2_NAME, TASK_NAME + "=" + name, null) > 0;
    }

    //Subject related update method and Delete method
    public boolean UpdateSubject(String new_name, String old_name){
        //Get a readable database
        SQLiteDatabase db = getReadableDatabase();
        //String formatting
        new_name = " '"+new_name+"' ";
        old_name = " '"+old_name+"' ";
        //SQL
        String SQL = "UPDATE " + TABLE_NAME +
                " SET " + SUBJECT_NAME + " = " + new_name +
                " WHERE " + SUBJECT_NAME + " = " + old_name;

        //Excute the statementsqLiteDatabase.execSQL(CREATE_TABLE_Login);
        try{
            db.execSQL(SQL);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Create a Method to delete the subject
    public boolean DeleteSubject(String name){
        //Getting Writable database.
        SQLiteDatabase db = getReadableDatabase();
        //String formatting
        name = " '"+name+"' ";
        //Create a Delete Statement and Execute.
        return db.delete(TABLE_NAME, SUBJECT_NAME + "=" + name, null) > 0;

    }

    //Saliths DB handling Methods ------------------------------------------------------------------
    public boolean insertData(String audio , String path){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SONG_NAME, null);

        while (cursor.moveToNext()) {
            if (audio.equals(cursor.getString(1))) {
                return false;
            }
            if (path.equals(cursor.getString(2))) {
                return false;
            }
        }
        //String INSERT_TABLE = "INSERT INTO " + TABLE_NAME + " (" +SONG_NAME+") VALUES ( " + audio + ")";
        ContentValues contentValues = new ContentValues();
        contentValues.put(SONG_NAME , audio);
        contentValues.put(SONG_PATH , path);

        //added
        contentValues.put(SONG_STATUS , notstatus);
        Long result = db.insert(TABLE_SONG_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public ArrayList<SongList> getAllAudios() {

        //Creating the ArrayList and start the database connection
        ArrayList<SongList> list = new ArrayList<SongList>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SONG_NAME, null);

        while (cursor.moveToNext()) {
            //added
            String song_id = cursor.getString(0);
            String song_name = cursor.getString(1);
            //added changed
            String song_status = cursor.getString(2);
            String song_path = cursor.getString(3);
            //added
            SongList songList = new SongList(song_id , song_name , song_status ,  song_path);
            list.add(songList);
        }

        return list;
    }
    public boolean deleteAudios(String id){
        SQLiteDatabase db = getReadableDatabase();
        id = " '"+id+"' ";
        long result = db.delete(TABLE_SONG_NAME, SONG_ID + "=" + id, null) ;
        if(result == -1){
            return false;
        }
        else
            return true;
    }
    //---------------newly added by salitha----------------------------
    //added
    public void updateSelectedStatus(String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ContentValues cv1 = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SONG_NAME, null);

        while (cursor.moveToNext()) {
            System.out.println("1");
            if (cursor.getString(2).equals(status)) {
                System.out.println("2");
                String oldstatusid = cursor.getString(0);
                System.out.println("3");
                cv.put(SONG_STATUS, notstatus);
                try {
                    db.update(TABLE_SONG_NAME, cv, "SONG_ID=?", new String[]{oldstatusid});
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //added
    public void updateStatus(String songID, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ContentValues cv1 = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SONG_NAME, null);

        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(songID)){
                cv1.put(SONG_STATUS, status);
                try{
                    db.update(TABLE_SONG_NAME,cv1, "song_id=?",new String[]{songID});
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //taneesha - DB methods ------------------------------------------------------------------------
    public void addList(String title, String des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DES, des);
        
        if(title.length() == 0){
            Toast.makeText(context, "Please Enter Title", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(des.length() == 0){
            Toast.makeText(context, "Please Enter Description", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = db.insert(TABLE_NAME_T, null,cv);

        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME_T;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateData(String row_id,String title, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DES,description);

        long result = db.update(TABLE_NAME_T,cv, "_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to update.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successfully Updated!",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_T, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }


    //tandin's DB
    public boolean insert(String FN, String UN, String PW){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FN",FN );
        contentValues.put("UN",UN );
        contentValues.put("PW",PW );
        long ins = db.insert("user", null,contentValues);

        if(ins == -1) return false;
        else return true;
    }
    //checking if UN exists
    public Boolean chkUN(String UN){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where UN = ?",new String[]{UN});
        if(cursor.getCount()>0) return false;
        else return true;
    }

    //checking the Username and password
    public Boolean UNPW(String UN, String PW){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where UN =?  and PW =? ", new String[] {UN,PW});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    public Cursor getName(SQLiteDatabase database, String name){

        //SQL
        String sql = "SELECT * FROM user where UN  = "+ " '" +name+"' ";
        Cursor data = database.rawQuery(sql, null);
        return data;
    }
    //Read Task Taneesha
    public Cursor readAlltasks(){
        String query = "SELECT " + TASK_NAME + " FROM " + TABLE2_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //Tasks Adding part Taneesha.



}
