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

                " FOREIGN KEY (" + GameEntry.COLUMN_TEAMA +   ") REFERENCES " +
                TeamEntry.TABLE_NAME + " (" + TeamEntry._ID + "), " +

                " FOREIGN KEY (" + GameEntry.COLUMN_TEAMB +  ") REFERENCES " +
                TeamEntry.TABLE_NAME + " (" + TeamEntry._ID + "), " +

                " UNIQUE (" + GameEntry.COLUMN_NAME + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_TEAM_TABLE = "CREATE TABLE " + TeamEntry.TABLE_NAME + " (" +
                TeamEntry._ID + " INTEGER PRIMARY KEY," +
                TeamEntry.COLUMN_TEAM_NAME + " TEXT UNIQUE NOT NULL, " +
                TeamEntry.COLUMN_TEAM_NUM + " INTEGER NOT NULL, " +

                " UNIQUE (" + TeamEntry.COLUMN_TEAM_NAME + ") ON CONFLICT REPLACE);";


        final String SQL_CREATE_PLAYER_TABLE = "CREATE TABLE " + PlayerEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                PlayerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                PlayerEntry.COLUMN_PLAYER_NAME + " TEXT UNIQUE NOT NULL, " +
                PlayerEntry.COLUMN_PLAYER_NUMBER + " INTEGER NOT NULL, " +
                PlayerEntry.COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
                PlayerEntry.COLUMN_TEAM + " INTEGER NOT NULL, " +

                PlayerEntry.COLUMN_GAME + " INTEGER NOT NULL, " +
                // the ID of the location entry associated with this weather data
                PlayerEntry.COLUMN_GAME_NUM + " INTEGER NOT NULL, " +
                PlayerEntry.COLUMN_AST + " INTEGER NOT NULL, " +
                PlayerEntry.COLUMN_BLOCK + " INTEGER NOT NULL," +
                PlayerEntry.COLUMN_DEF + " INTEGER NOT NULL," +
                PlayerEntry.COLUMN_OFF + " INTEGER NOT NULL," +
                PlayerEntry.COLUMN_STEAL + " INTEGER NOT NULL," +

                PlayerEntry.COLUMN_SHOT + " INTEGER NOT NULL," +
                PlayerEntry.COLUMN_TOTAL_SHOT + " INTEGER NOT NULL," +

                PlayerEntry.COLUMN_THREE + " INTEGER NOT NULL," +
                PlayerEntry.COLUMN_TOTAL_THREE + " INTEGER NOT NULL," +

                PlayerEntry.COLUMN_TURNOVER + " INTEGER NOT NULL," +
                PlayerEntry.COLUMN_GAME_TIME + " REAL NOT NULL, " +

                " FOREIGN KEY (" + PlayerEntry.COLUMN_GAME  + ") REFERENCES " +
                GameEntry.TABLE_NAME + " (" + GameEntry._ID + ")," +

                " FOREIGN KEY (" + PlayerEntry.COLUMN_TEAM  + ") REFERENCES " +
                GameEntry.TABLE_NAME + " (" + TeamEntry._ID + "));";

        sqLiteDatabase.execSQL(SQL_CREATE_TEAM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PLAYER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAME_TABLE);
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
        onCreate(sqLiteDatabase);
    }
}
