package com.example.temp.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.temp.Models.ProgrameData
import com.example.temp.Models.TopicData

class Database(context:Context):SQLiteOpenHelper(context,    DATABASE_NAME, null, DATABASE_VERSION)
{
    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_TABLE = "CREATE TABLE $TABLE_NAME($_ID INTEGER ,$TOPIC_NAME TEXT,$TOPIC_DESCRIPTION TEXT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun createTopic(topic: TopicData)
    {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()

        values.put(TOPIC_NAME, topic.topicName)
        values.put(TOPIC_DESCRIPTION, topic.topicDescription)

        db.insert(TABLE_NAME, null, values)
        Log.d("DATA INSERTED", "SUCCESS")
        db.close()
    }


    fun getAllTopics():ArrayList<TopicData>
    {
        var db:SQLiteDatabase = readableDatabase
        var list:ArrayList<TopicData> = ArrayList()

        var selectAll = "SELECT * FROM $TABLE_NAME"
        var cursor: Cursor = db.rawQuery(selectAll, null)

        if(cursor.moveToFirst()){
            do {
                var topicName = cursor.getString(cursor.getColumnIndex(TOPIC_NAME))
                var topicDescription = cursor.getString(cursor.getColumnIndex(TOPIC_DESCRIPTION))
                var topic = TopicData(topicName,topicDescription)
                list.add(topic)
            }while (cursor.moveToNext())
        }

        return list
    }

    fun getTopicCount():Int
    {
        val db : SQLiteDatabase = readableDatabase
        val CountQuery = "SELECT * FROM " + TABLE_NAME
        val cursor:Cursor = db.rawQuery(CountQuery, null)
        return if(cursor.count >0 ) cursor.count
        else 0
    }

    fun deleteTopic(topicName: String)
    {
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, "$TOPIC_NAME=?", arrayOf(topicName))
        db.close()
    }

    fun createProgrammeTable(tableName: String)
    {
        val db: SQLiteDatabase = writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        var CREATE_TABLE = "CREATE TABLE $tableName($PROGRAMME_ID INTEGER,$PROGRAMME_NAME TEXT,$PROGRAME_CODE TEXT)"
        db.execSQL(CREATE_TABLE)
    }

    fun createProgramme(programme:ProgrameData, tableName: String)
    {
        var db: SQLiteDatabase = writableDatabase
        var values: ContentValues = ContentValues()

        values.put(PROGRAMME_NAME, programme.programeName)
        values.put(PROGRAME_CODE, programme.programmeCode)

        db.insert(tableName, null, values)
        db.close()
    }

    fun getAllProgrammes(tableName: String):ArrayList<ProgrameData>
    {
        var db:SQLiteDatabase = readableDatabase
        var list:ArrayList<ProgrameData> = ArrayList()
        var selectAll = "SELECT * FROM $tableName"
        var cursor: Cursor = db.rawQuery(selectAll, null)
        if(cursor.moveToFirst()){
            do {
                var progName:String = cursor.getString(cursor.getColumnIndex(PROGRAMME_NAME)).toString()
                var progCode:String = cursor.getString(cursor.getColumnIndex(PROGRAME_CODE)).toString()
                var obj = ProgrameData(progName,progCode)
                list.add(obj)
            }while (cursor.moveToNext())
        }
        return list
    }


    fun getProgrammeCount(tableName: String):Int
    {
        if(tableName == null) return 0
        var db : SQLiteDatabase = readableDatabase
        var CountQuery = "SELECT * FROM " + tableName
        var cursor: Cursor = db.rawQuery(CountQuery, null)
        if(cursor.count >0 ) return cursor.count
        else return 0
    }


    fun deleteProgramme(programmeName: String, tableName: String)
    {
        var db: SQLiteDatabase = writableDatabase
        db.delete(tableName, "$PROGRAMME_NAME=?", arrayOf(programmeName) )
        db.close()
    }








}