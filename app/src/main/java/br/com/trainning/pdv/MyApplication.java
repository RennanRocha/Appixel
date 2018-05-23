package br.com.trainning.pdv;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.Sprinkles;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;

/**
 * Created by elcio on 23/11/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Sprinkles sprinkles = Sprinkles.init(getApplicationContext());

        sprinkles.addMigration(new Migration() {
            @Override
            protected void onPreMigrate() {
                // do nothing
            }

            @Override
            protected void doMigration(SQLiteDatabase db) {
                db.execSQL(
                        "CREATE TABLE Produto (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "descricao TEXT,"+
                                "unidade TEXT,"+
                                "codigo_barras TEXT,"+
                                "preco REAL,"+
                                "foto64 TEXT,"+
                                "latitude REAL," +
                                "longitude REAL,"+
                                "ativo INTEGER"+
                                ")"
                );
                db.execSQL(
                        "CREATE TABLE Carrinho (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "id_compra TEXT,"+
                                "encerrada INTEGER,"+
                                "enviada INTEGER)"

                );
                db.execSQL(
                        "CREATE TABLE Item (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                "id_compra TEXT,"+
                                "id_produto TEXT,"+
                                "quantidade INTEGER)"

                );
            }


            @Override
            protected void onPostMigrate() {
                // do nothing
            }
        });
    }

}