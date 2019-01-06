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
import com.who.helathy.fitplanner.helper.database.templates.*
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

    // TODO: should be fixed. Prevents getDatabase called recursively. Only to import sample data
    fun addSport(sport: Sport, db: SQLiteDatabase): Long {
        LOGGER.info("### Inserting ${sport.name} sport into db ###")
        return insertIntoAsSampleFirstData(DatabaseSportTemplateUtil.getSportContentValues(sport), DatabaseSportTemplateUtil.TABLE_NAME, db)
    }

    fun addSport(sport: Sport): Long {
        LOGGER.info("### Inserting ${sport.name} sport into db ###")
        return insertIntoDatabase(DatabaseSportTemplateUtil.getSportContentValues(sport), DatabaseSportTemplateUtil.TABLE_NAME)
    }

    fun addWeight(weight: Weight): Long {
        LOGGER.info("### Inserting weight ${weight.value}, ${weight.date} kg into db ###")
        return insertIntoDatabase(DatabaseWeightTemplateUtil.getWeightContentValues(weight), DatabaseWeightTemplateUtil.TABLE_NAME)
    }

    fun addWorkout(workout: Workout): Long {
        LOGGER.info("### Inserting workout from a date ${workout.dateStart} into db ###")
        return insertIntoDatabase(DatabaseWorkoutTemplateUtil.getWorkoutContentValues(workout), DatabaseWorkoutTemplateUtil.TABLE_NAME)
    }

    fun addUser(user: User): Long {
        LOGGER.info("### Inserting ${user.name} user into db ###")
        return insertIntoDatabase(DatabaseUserTemplateUtil.getUserContentValues(user), DatabaseUserTemplateUtil.TABLE_NAME)
    }

    fun updateUser(user: User) {
        LOGGER.info("### Updating ${user.name} user into db ###")
        updateRowInDatabase(DatabaseUserTemplateUtil.getUserContentValues(user),
                DatabaseUserTemplateUtil.getWhereClause(), Array(1) { user.name },
                DatabaseUserTemplateUtil.TABLE_NAME)
    }

    // TODO: this should be removed, it prevents recuirsive calback on onCreate method
    // cause IllegalStateException can be thrown taking writableDatabase it db file doesn't exists
    // it shoudn't be used no more.
    private fun insertIntoAsSampleFirstData(contentValues: ContentValues, tableName: String, db: SQLiteDatabase): Long {
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
            this.removeAnCreateDatabase(db)
        }
    }

    fun removeAnCreateDatabase(db: SQLiteDatabase) {
        this.dropDatabaseTables(db)
        this.onCreate(db)
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

    private fun insertIntoDatabase(contentValues: ContentValues, tableName: String): Long {
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

    private fun updateRowInDatabase(contentValues: ContentValues, whereClause: String, whereArg: Array<String?>, tableName: String) {
        var tDb = this.writableDatabase
        if (tDb == null) {
            tDb = this.writableDatabase
        }
        try {
            tDb.update(tableName, contentValues, whereClause, whereArg)
        } catch (e: Exception) {
            throw SQLException("!!! Row cannot be updated in a: $tableName table !!!", e)
        } finally {
            tDb.close()
        }
    }

    fun removeSport(sport: Sport) {
        this.removeRow(DatabaseSportTemplateUtil.getDeleteRowWhereClause(),
                Array(1) { sport.id.toString() }, DatabaseSportTemplateUtil.TABLE_NAME)
    }

    private fun removeRow(whereClause: String, whereArg: Array<String>, tableName: String) {
        var tDb = this.writableDatabase
        if (tDb == null) {
            tDb = this.writableDatabase
        }
        try {
            tDb.delete(tableName, whereClause, whereArg)
        } catch (e: Exception) {
            throw SQLException("!!! Row cannot be removed from $tableName table !!!", e)
        } finally {
            tDb.close()
        }
    }

    fun getAllSports(): ArrayList<Sport> {
        LOGGER.info("### Selecting rows from ${DatabaseSportTemplateUtil.TABLE_NAME} ###")
        var db: SQLiteDatabase = this.readableDatabase
        var list = ArrayList<Sport>()
        var sport: Sport
        try {
            val cursor: Cursor = db.rawQuery(DatabaseTemplateUtil.getSelectExecString(
                    DatabaseSportTemplateUtil.TABLE_NAME), null)
            try {
                if (cursor.moveToFirst()) {
                    do {
                        sport = DatabaseSportTemplateUtil.getSportFromCursor(cursor)
                        list.add(sport)
                    } while (cursor.moveToNext())
                }
            } finally {
                cursor.close()
            }
        } finally {
            db.close()
        }
        return list
    }

    fun getAllWeighs(): ArrayList<Weight> {
        LOGGER.info("### Selecting rows from ${DatabaseWeightTemplateUtil.TABLE_NAME} ###")
        var db: SQLiteDatabase = this.readableDatabase
        var list = ArrayList<Weight>()
        var weight: Weight
        try {
            val cursor: Cursor = db.rawQuery(DatabaseTemplateUtil.getSelectExecString(
                    DatabaseWeightTemplateUtil.TABLE_NAME), null)
            try {
                if (cursor.moveToFirst()) {
                    do {
                        weight = DatabaseWeightTemplateUtil.getWeightFromCursor(cursor)
                        list.add(weight)
                    } while (cursor.moveToNext())
                }
            } finally {
                cursor.close()
            }
        } finally {
            db.close()
        }
        return list
    }

    fun getAllWorkouts(): ArrayList<Workout> {
        LOGGER.info("### Selecting rows from ${DatabaseWorkoutTemplateUtil.TABLE_NAME} ###")
        var db: SQLiteDatabase = this.readableDatabase
        var list = ArrayList<Workout>()
        var workout: Workout
        try {
            val cursor: Cursor = db.rawQuery(DatabaseTemplateUtil.getSelectExecString(
                    DatabaseWorkoutTemplateUtil.TABLE_NAME), null)
            try {
                if (cursor.moveToFirst()) {
                    do {
                        workout = DatabaseWorkoutTemplateUtil.getWorkoutFromCursor(cursor)
                        list.add(workout)
                    } while (cursor.moveToNext())
                }
            } finally {
                cursor.close()
            }
        } finally {
            db.close()
        }
        return list
    }

}