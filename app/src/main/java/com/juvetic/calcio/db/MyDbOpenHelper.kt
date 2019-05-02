package com.juvetic.calcio.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.juvetic.calcio.model.favorite.FavoriteEvent
import com.juvetic.calcio.model.favorite.FavoriteTeam
import org.jetbrains.anko.db.*

class MyDbOpenHelper(context: Context) : ManagedSQLiteOpenHelper(
    context, "Favorite.db", null, 1
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
            FavoriteEvent.TABLE_FAVORITE,
            true,
            FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteEvent.EVENT_ID to TEXT + UNIQUE,
            FavoriteEvent.EVENT_DATE to TEXT,
            FavoriteEvent.EVENT_HOME_TEAM to TEXT,
            FavoriteEvent.EVENT_AWAY_TEAM to TEXT,
            FavoriteEvent.EVENT_HOME_SCORE to TEXT,
            FavoriteEvent.EVENT_AWAY_SCORE to TEXT
        )

        db?.createTable(
            FavoriteTeam.TABLE_TEAM,
            true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteEvent.TABLE_FAVORITE, true)
        db?.dropTable(FavoriteTeam.TABLE_TEAM, true)
    }
}

val Context.database: MyDbOpenHelper
    get() = MyDbOpenHelper.getInstance(applicationContext)