package com.who.helathy.fitplanner.helper.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.who.helathy.fitplanner.domain.Sport
import com.who.helathy.fitplanner.domain.User
import com.who.helathy.fitplanner.domain.Weight
import com.who.helathy.fitplanner.domain.Workout
import com.who.helathy.fitplanner.helper.database.templates.DatabaseSportTemplateUtil
import com.who.helathy.fitplanner.helper.database.templates.DatabaseTemplateUtil
import com.who.helathy.fitplanner.helper.database.templates.DatabaseUserTemplateUtil
import com.who.helathy.fitplanner.helper.database.templates.DatabaseWeightTemplateUtil
import com.who.helathy.fitplanner.helper.database.templates.DatabaseWorkoutTemplateUtil
import java.lang.Exception
import java.sql.SQLException
import java.util.logging.Logger

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DatabaseTemplateUtil.DB_NAME, null, DatabaseTemplateUtil.DB_VERSION) {

    companion object {
        private var context: Context? = null
        private var sInstance : DatabaseHelper? = null
        private val LOGGER = Logger.getLogger(DatabaseHelper::class.java.name)

        fun getInstance(context: Context): DatabaseHelper? {
            if (sInstance == null) {
                LOGGER.info("### Creating instance of DatabaseHelper. ###")
                this.sInstance = DatabaseHelper(context.applicationContext)
                this.context = context
            }
            return sInstance
        }

        fun getContext(): Context {
            return this.context!!
        }
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    // called when database is created for the first time
    override fun onCreate(db: SQLiteDatabase) {
        this.createDatabaseTables(db)
        SampleDataInitializer.importSampleData(db)
    }

    fun addSport(db: SQLiteDatabase, sport: Sport): Long {
        LOGGER.info("### Inserting ${sport.name} sport into db ###")
        return insertInto(DatabaseSportTemplateUtil.getSportContentValues(sport), DatabaseSportTemplateUtil.TABLE_NAME, db)
    }

    // TODO: remove this db, can be done as reference to just writeable database
    fun addUser(db: SQLiteDatabase, user: User): Long {
        LOGGER.info("### Inserting ${user.name} user into db ###")
        return insertInto(DatabaseUserTemplateUtil.getUserContentValues(user), DatabaseUserTemplateUtil.TABLE_NAME, db)
    }

    fun getUser(): User? {
        LOGGER.info("### Selecting user from db ###")

        var db: SQLiteDatabase = this.readableDatabase
        var user: User? = null
        val cursor: Cursor = db.rawQuery(DatabaseUserTemplateUtil.getSelectUserExecString(), null)

        try{
            if(cursor != null && cursor.count > 0 && cursor.moveToFirst()) {
                user = DatabaseUserTemplateUtil.getUserFromCursor(cursor)
            }
        } catch (e: Exception) {
            LOGGER.warning("!!! Exception thrown during getting user data from database !!!")
            throw IllegalStateException(e.message)
        } finally {
            cursor.close()
        }
        return user
    }

    fun addWeight(db: SQLiteDatabase, weight: Weight): Long {
        LOGGER.info("### Inserting weight ${weight.value}, ${weight.date} kg into db ###")
        return insertInto(DatabaseWeightTemplateUtil.getWeightContentValues(weight), DatabaseWeightTemplateUtil.TABLE_NAME, db)
    }

    fun addWorkout(db: SQLiteDatabase, workout: Workout): Long {
        LOGGER.info("### Inserting workout from a date ${workout.date} into db ###")
        return insertInto(DatabaseWorkoutTemplateUtil.getWorkoutContentValues(workout), DatabaseWorkoutTemplateUtil.TABLE_NAME, db)

    }

    //TO REMOVE
    private fun insertInto(contentValues: ContentValues, tableName: String, db: SQLiteDatabase): Long {
        var tDb = db
        if(tDb == null) {
            tDb = this.writableDatabase
        }
        var id = -1L

        db.beginTransaction()
        try {
            id = tDb.insertOrThrow(tableName, null, contentValues)
            tDb.setTransactionSuccessful()
        } catch (e: Exception) {
            throw SQLException("!!! Row cannot be inserted into $tableName table !!!", e)
        } finally {
            tDb.endTransaction()
        }
        return id
    }

    // TODO("agataguli: data migration between versions")
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            this.dropDatabaseTables(db)
            this.onCreate(db)
        }
    }

    private fun createDatabaseTables(db: SQLiteDatabase) {
        db.execSQL(DatabaseUserTemplateUtil.getCreateTableExecString())
        db.execSQL(DatabaseWeightTemplateUtil.getCreateTableExecString())
        db.execSQL(DatabaseSportTemplateUtil.getCreateTableExecString())
        db.execSQL(DatabaseWorkoutTemplateUtil.getCreateTableExecString())
        this.setDefaultLabel(db)
    }

    private fun setDefaultLabel(db: SQLiteDatabase) {
    }

    private fun dropDatabaseTables(db: SQLiteDatabase) {
        db.execSQL(DatabaseWorkoutTemplateUtil.getDropTableExecString())
        db.execSQL(DatabaseSportTemplateUtil.getDropTableExecString())
        db.execSQL(DatabaseWeightTemplateUtil.getDropTableExecString())
        db.execSQL(DatabaseUserTemplateUtil.getDropTableExecString())
    }

    fun addUserTest(user: User): Long {
        LOGGER.info("### Inserting ${user.name} user into db ###")
        return insertIntoTest(DatabaseUserTemplateUtil.getUserContentValues(user), DatabaseUserTemplateUtil.TABLE_NAME)
    }

    private fun insertIntoTest(contentValues: ContentValues, tableName: String): Long {
        var tDb = this.writableDatabase
        if(tDb == null) {
            tDb = this.writableDatabase
        }
        var id = -1L

        tDb.beginTransaction()
        try {
            id = tDb.insertOrThrow(tableName, null, contentValues)
            tDb.setTransactionSuccessful()
        } catch (e: Exception) {
            throw SQLException("!!! Row cannot be inserted into $tableName table !!!", e)
        } finally {
            tDb.endTransaction()
            tDb.close()
        }
        return id
    }

}