package com.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "main.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "color";
    private static final List<String> TABLE_COLUMNS = new ArrayList<String>(){{
        add("r");
        add("g");
        add("b");
        add("a");
    }};

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder columns = new StringBuilder();
        for (String column : TABLE_COLUMNS) {
            columns.append("`").append(column).append("` INT NOT NULL,");
        }
        db.execSQL("CREATE TABLE `" + TABLE_NAME + "` (" + columns + "PRIMARY KEY (`r`,`g`,`b`,`a`)); ENGINE=InnoDB;");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean colorExist(int r, int g, int b, int a){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;

        Cursor cursor = null;
        if(db != null){
            cursor = db.query(TABLE_NAME,new String[]{"r","g","b","a"},"r=? AND g=? AND b=? AND a=?", new String[]{String.valueOf(r), String.valueOf(g), String.valueOf(b), String.valueOf(a)}, null, null, null);
        }
        System.out.println(cursor.getCount());
        return cursor.getCount() >= 1;
    }

    void addColor(int r, int g, int b, int a){
        SQLiteDatabase db = this.getWritableDatabase();
        if(!colorExist(r,g,b,a)){
            ContentValues cv = new ContentValues();
            cv.put("r", r);cv.put("g", g);cv.put("b", b);cv.put("a", a);
            long result = db.insert(TABLE_NAME,null, cv);
            if(result == -1){
                Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Couleur ajoutée !", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(context, "Cette couleur existe déjà", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(int old_r, int old_g, int old_b, int old_a, int r, int g, int b, int a){
        SQLiteDatabase db = this.getWritableDatabase();
        if(!colorExist(r,g,b,a)){
            ContentValues cv = new ContentValues();
            cv.put("r", r);cv.put("g", g);cv.put("b", b);cv.put("a", a);
            long result = db.update(TABLE_NAME, cv, "r=? AND g=? AND b=? AND a=?", new String[]{String.valueOf(old_r), String.valueOf(old_g), String.valueOf(old_b), String.valueOf(old_a)});
            if(result == -1){
                Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Couleur mise à jour !", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(context, "Cette couleur existe déjà", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(int r, int g, int b, int a){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "r=? AND g=? AND b=? AND a=?", new String[]{String.valueOf(r), String.valueOf(g), String.valueOf(b), String.valueOf(a)});
        if(result == -1){
            Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cuoleur supprimée !", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
