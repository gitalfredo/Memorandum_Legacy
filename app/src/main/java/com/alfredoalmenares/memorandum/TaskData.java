package com.alfredoalmenares.memorandum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

// SQLite helper
public class TaskData extends SQLiteOpenHelper {
    public static final String TASKS_TABLE = "TASKS_TABLE";
    public static final String TASK_ID = "ID";
    public static final String TASK_DESCRIPTION = "DESCRIPTION";
    public static final String TASK_TITLE = "TITLE";


    public TaskData(@Nullable Context context) {
        super(context, "tasks.db", null, 1);
    }

    // Runs the first time the database, and tables are created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TASKS_TABLE + " " +
                "(" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TASK_DESCRIPTION + " TEXT)";

        db.execSQL(createTableStatement);
    }

    // Runs on app upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void updateTask(int id, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_ID, id);
        cv.put(TASK_DESCRIPTION, desc);

        db.update(TASKS_TABLE, cv, TaskData.TASK_ID +"=?",new String[] {Integer.toString(id)});

        db.close();
    }

    public boolean addOne(String desc){
        // Guard against preconditions
        if(desc.isEmpty())
            return false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_DESCRIPTION, desc);
        long res = db.insert(TASKS_TABLE, null, cv);
        db.close();
        if(res>0)
            return true;
        else
            return false;
    }

    public ArrayList<CustomTask> getAllTasks(){
        ArrayList<CustomTask> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Check if table is empty

        String queryAll = "SELECT * FROM "+TASKS_TABLE;
        Cursor cursor = db.rawQuery(queryAll, null);
        if(cursor.getCount()==0) return tasks;
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast())  {
                int i = cursor.getColumnIndex(TASK_ID);
                int j = cursor.getColumnIndex(TASK_DESCRIPTION);
                int id = cursor.getInt(i);
                String data = cursor.getString(j);
                tasks.add(new CustomTask(id,data));

                cursor.moveToNext();
            };
        }
        cursor.close();
        db.close();

        return tasks;
    }

    public CustomTask getSingletask(int id){
        CustomTask task = null;
        SQLiteDatabase db = this.getReadableDatabase();
        // Check if table is empty

        String querySingle = "SELECT * FROM "+TASKS_TABLE+" WHERE "+TASK_ID+" = "+id;
        Cursor cursor = db.rawQuery(querySingle, null);
        if(cursor.getCount()==0) return task;
        if(cursor.moveToFirst()) {
            int i0 = cursor.getColumnIndex(TASK_ID);
            int i1 = cursor.getColumnIndex(TASK_DESCRIPTION);

            int dataId = cursor.getInt(i0);
            String dataDesc = cursor.getString(i1);
            task = new CustomTask(dataId, dataDesc);
        }
        cursor.close();
        db.close();

        return task;
    }

    public void clearALl(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASKS_TABLE, "", null);
        db.close();
    }

    public void removeOneTask(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        int r = db.delete(TASKS_TABLE, "ID="+i, null);
        Log.d("DEBUG3", "index removed? "+r);
        db.close();
    }
}
