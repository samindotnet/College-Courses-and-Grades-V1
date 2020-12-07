package com.example.collegecourses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DatabaseName="ProjectDB";
    public static final String TableName="Courses";
    public static final String Column1="CourseID";
    public static final String Column2="Course";
    public static final String Column3="Grade";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TableName+"(CourseID Integer Primary Key Autoincrement, Course text, Grade Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TableName);
        onCreate(db);
    }
    public boolean InsertCourse(String Course,String Grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column2,Course);
        contentValues.put(Column3,Grade);
        long result = db.insert(TableName,null,contentValues);
        if(result ==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public Integer DeleteCourse(String CourseID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TableName,"CourseID=?",new String[] {CourseID});
    }
    public boolean UpdateCourse(String CourseID,String Course,String Grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column1,CourseID);
        contentValues.put(Column2,Course);
        contentValues.put(Column3,Grade);
        db.update(TableName,contentValues,"CourseID=?",new String[] {CourseID});
        return true;
    }
    public Cursor ViewCourses(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor acura = db.rawQuery("select * from "+TableName,null);
        return acura;
    }
    public Cursor ShowGPA(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor acura = db.rawQuery("select avg(Grade) as GPA from "+TableName,null);
        return acura;
    }
}
