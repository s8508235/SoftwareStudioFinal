package netdb.courses.softwarestudio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import netdb.courses.softwarestudio.data.TeamContract.GameEntry;
import netdb.courses.softwarestudio.data.TeamContract.PlayerEntry;
import netdb.courses.softwarestudio.data.TeamContract.TeamEntry;

/**
 * Manages a local database for weather data.
 */
public class TeamDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "team.db";

    public TeamDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_GAME_TABLE = "CREATE TABLE " + GameEntry.TABLE_NAME + " (" +
                GameEntry._ID + " INTEGER PRIMARY KEY," +
                GameEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                GameEntry.COLUMN_TEAMA + " INTEGER NOT NULL, " +
                GameEntry.COLUMN_TEAMB + " INTEGER NOT NULL, " +
                GameEntry.COLUMN_TEAMA_SCORE + " INTEGER NOT NULL, " +
                GameEntry.COLUMN_TEAMB_SCORE + " INTEGER NOT NULL, " +
                GameEntry.COLUMN_WINNER + " INTEGER NOT NULL, " +

                " UNIQUE (" + GameEntry.COLUMN_NAME + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_TEAM_TABLE = "CREATE TABLE " + TeamEntry.TABLE_NAME + " (" +
                TeamEntry._ID + " INTEGER PRIMARY KEY," +
                TeamEntry.COLUMN_TEAM_NAME + " TEXT UNIQUE NOT NULL, " +
                TeamEntry.COLUMN_TEAM_NUM + " INTEGER NOT NULL, " +

                " UNIQUE (" + TeamEntry.COLUMN_TEAM_NAME + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_GAME_PLAYER_TABLE = "CREATE TABLE " + TeamContract.GamePlayerEntry.TABLE_NAME + " (" +
                TeamContract.GamePlayerEntry._ID + " INTEGER PRIMARY KEY," +
                TeamContract.GamePlayerEntry.COLUMN_GAME_ID + " INTEGER NOT NULL, " +
                TeamContract.GamePlayerEntry.COLUMN_PLAYER_DATA_ID + " INTEGER NOT NULL, " +
                TeamContract.GamePlayerEntry.COLUMN_PLAYER_NAME + " TEXT NOT NULL, " +
                TeamContract.GamePlayerEntry.COLUMN_TEAM_NAME + " TEXT NOT NULL, " +
                TeamContract.GamePlayerEntry.COLUMN_PLAYER_ID + " INTEGER NOT NULL);";

        final String SQL_CREATE_GAME_DATA_TABLE = "CREATE TABLE " + TeamContract.GameDataEntry.TABLE_NAME + " (" +
                TeamContract.GameDataEntry._ID + " INTEGER PRIMARY KEY," +
                // the ID of the location entry associated with this weather data
                TeamContract.GameDataEntry.COLUMN_AST + " INTEGER NOT NULL, " +
                TeamContract.GameDataEntry.COLUMN_BLOCK + " INTEGER NOT NULL," +
                TeamContract.GameDataEntry.COLUMN_DEF + " INTEGER NOT NULL," +
                TeamContract.GameDataEntry.COLUMN_OFF + " INTEGER NOT NULL," +
                TeamContract.GameDataEntry.COLUMN_STEAL + " INTEGER NOT NULL," +

                TeamContract.GameDataEntry.COLUMN_SHOT + " INTEGER NOT NULL," +
                TeamContract.GameDataEntry.COLUMN_TOTAL_SHOT + " INTEGER NOT NULL," +

                TeamContract.GameDataEntry.COLUMN_THREE + " INTEGER NOT NULL," +
                TeamContract.GameDataEntry.COLUMN_TOTAL_THREE + " INTEGER NOT NULL," +

                TeamContract.GameDataEntry.COLUMN_TURNOVER + " INTEGER NOT NULL," +
                TeamContract.GameDataEntry.COLUMN_FREE_THROW + " INTEGER NOT NULL," +
                TeamContract.GameDataEntry.COLUMN_TOTAL_FREE_THROW + " INTEGER NOT NULL);";

        final String SQL_CREATE_PLAYER_TABLE = "CREATE TABLE " + PlayerEntry.TABLE_NAME + " (" +

                PlayerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                PlayerEntry.COLUMN_PLAYER_NAME + " TEXT NOT NULL, " +
                PlayerEntry.COLUMN_PLAYER_DATA_ID + " INTEGER NOT NULL, " +
                PlayerEntry.COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
                PlayerEntry.COLUMN_TEAM_ID + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_TEAM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PLAYER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_PLAYER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TeamEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TeamContract.GameDataEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TeamContract.GamePlayerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
