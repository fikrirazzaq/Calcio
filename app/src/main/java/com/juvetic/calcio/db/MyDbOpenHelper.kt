package com.juvetic.calcio.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.juvetic.calcio.model.Favorite
import org.jetbrains.anko.db.*

class MyDbOpenHelper(context: Context) : ManagedSQLiteOpenHelper(
    context, "FavoriteEvent.db", null, 1
) {

    companion object {
        private var instance: MyDbOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): MyDbOpenHelper {
            if (instance == null) {
                instance = MyDbOpenHelper(context.applicationContext)
            }
            return instance as MyDbOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.TABLE_FAVORITE,
            true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.EVENT_ID to TEXT + UNIQUE,
            Favorite.EVENT_DATE to TEXT,
            Favorite.EVENT_HOME_TEAM to TEXT,
            Favorite.EVENT_AWAY_TEAM to TEXT,
            Favorite.EVENT_HOME_SCORE to TEXT,
            Favorite.EVENT_AWAY_SCORE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

val Context.database: MyDbOpenHelper
    get() = MyDbOpenHelper.getInstance(applicationContext)