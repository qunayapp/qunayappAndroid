package com.pe.mascotapp.modelos
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pe.mascotapp.interfaces.Modelo
import com.pe.mascotapp.utils.Utils
import java.io.File

class SQLiteDB(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    Modelo.Start  {

    override fun onCreate(db: SQLiteDatabase?) {
        Utils.dump("Oncreate database")
        db?.execSQL("create table usuario(id integer PRIMARY KEY AUTOINCREMENT , name varchar(100), birthdate varchar(12), email varchar(100),pass varchar(100), numPhone varchar(50), img varchar(255), sex varchar(50), fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")
        db?.execSQL("create table mascota(id integer PRIMARY KEY AUTOINCREMENT, name varchar(100), tipo varchar(100), raza varchar(100),weight varchar(100), birthdate varchar(12), img varchar(255), sex varchar(50), fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")
        db?.execSQL("create table mascotaUsuario(id integer PRIMARY KEY AUTOINCREMENT, idUsuario integer, idMascota integer, fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")
        db?.execSQL("create table especies(id integer PRIMARY KEY AUTOINCREMENT, name varchar(200), description varchar(200))")
        db?.execSQL("create table razas(id integer PRIMARY KEY AUTOINCREMENT, idEspecie integer, name varchar(200), description varchar(200))")

        //db?.execSQL("create table provincias(idProvincia integer ,provincia varchar(50), iddepartamento int, codprovincia varchar(2))")
        //db?.execSQL("create table distritos(idDistrito integer ,distrito varchar(100), idProvincia int, codDistrito varchar(2))")

        //db?.execSQL(inserDepartamento())
        //db?.execSQL(insertProvincia())
        //db?.execSQL(insertDistrito())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "mascotapp.db"
    }

    override fun startDatabe(context: Context):Boolean {
        var dbHelper = SQLiteDB(context)
        val db = dbHelper.readableDatabase

        return doesDatabaseExist(context, DATABASE_NAME)
    }

    private fun doesDatabaseExist(context: Context, dbName: String): Boolean {
        val dbFile: File = context.getDatabasePath(dbName)
        return dbFile.exists()
    }

    @SuppressLint("Range")
    override fun insertar(query:String):Int {
        val db = this.writableDatabase
        db.execSQL(query)

        val queryLastRowInserted = "select last_insert_rowid()"

        val cursor = db.rawQuery(queryLastRowInserted, null);

        var _idLastInsertedRow = 0
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    _idLastInsertedRow = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        return _idLastInsertedRow


        /*db = this.readableDatabase
        var rs = db.rawQuery("SELECT * FROM usuario", null)

        if (rs.moveToFirst()){

            do {
                Utils.dump("nombre: " + rs.getString(rs.getColumnIndex("nombre")))
                Utils.dump("dni: " + rs.getString(rs.getColumnIndex("dni")))
            }while (rs.moveToNext())

        }*/

    }

    @SuppressLint("Range")
    override fun delete(query:String) {
        val db = this.writableDatabase
        db.execSQL(query)

    }

    override fun leer(query: String):Cursor {

        val db = this.readableDatabase
        val rs = db.rawQuery(query, null)

        return rs
        /*if (rs.moveToFirst()){
            do {
            }while (rs.moveToNext())

        }*/
    }

}