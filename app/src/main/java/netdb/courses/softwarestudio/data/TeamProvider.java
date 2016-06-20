package netdb.courses.softwarestudio.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class TeamProvider extends ContentProvider {
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TeamDBHelper mOpenHelper;

    static final int TEAM_PLAYER = 100;
    static final int PLAYER = 101;
    static final int GAME_TEAM_PLAYER = 102;
    static final int GAME = 300;
    static final int TEAM = 400;
    static final int TEAM_ONE = 401;

    private static final SQLiteQueryBuilder sTeamQueryBuilder;

    static{
        sTeamQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sTeamQueryBuilder.setTables(
                TeamContract.GameEntry.TABLE_NAME + " INNER JOIN " +
                        TeamContract.TeamEntry.TABLE_NAME +
                        " ON " + TeamContract.GameEntry.TABLE_NAME +
                        "." + TeamContract.GameEntry.COLUMN_TEAMA +
                        " = " + TeamContract.TeamEntry.TABLE_NAME +
                        "." + TeamContract.TeamEntry._ID);
        sTeamQueryBuilder.setTables(
                TeamContract.GameEntry.TABLE_NAME + " INNER JOIN " +
                        TeamContract.TeamEntry.TABLE_NAME +
                        " ON " + TeamContract.GameEntry.TABLE_NAME +
                        "." + TeamContract.GameEntry.COLUMN_TEAMB +
                        " = " + TeamContract.TeamEntry.TABLE_NAME +
                        "." + TeamContract.TeamEntry._ID);
    }

    //location.location_setting = ?
    private static final String sTeamSettingSelection =
            TeamContract.TeamEntry.TABLE_NAME+
                    "." + TeamContract.TeamEntry.COLUMN_TEAM_NAME + " = ? ";

    //location.location_setting = ? AND date >= ?
   /* private static final String sLocationSettingWithStartDateSelection =
            TeamContract.LocationEntry.TABLE_NAME+
                    "." + TeamContract.LocationEntry.COLUMN_LOCATION_SETTING + " = ? AND " +
                    TeamContract.WeatherEntry.COLUMN_DATE + " >= ? ";*/

    //location.location_setting = ? AND date = ?
    private static final String sTeamSettingAndNumberSelection =
            TeamContract.TeamEntry.TABLE_NAME +
                    "." + TeamContract.TeamEntry.COLUMN_TEAM_NAME + " = ? AND " +
                    TeamContract.PlayerEntry.COLUMN_PLAYER_ID + " = ? ";

    private Cursor getTeamAndPlayers(Uri uri, String[] projection, String sortOrder) {
        String teamSetting = TeamContract.PlayerEntry.getTeamSettingFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sTeamSettingSelection;
        selectionArgs = new String[]{teamSetting};

        return sTeamQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
    private Cursor getOneTeam(Uri uri, String[] projection, String sortOrder){

        String teamSetting = TeamContract.TeamEntry.getTeamSettingFromUri(uri);

        return sTeamQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sTeamSettingSelection,
                new String[]{teamSetting},
                null,
                null,
                sortOrder
        );
    }

    private Cursor getPlayer(
            Uri uri, String[] projection, String sortOrder) {
        String teamSetting = TeamContract.PlayerEntry.getTeamSettingFromUri(uri);
        long number = TeamContract.PlayerEntry.getNumberFromUri(uri);

        return sTeamQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sTeamSettingAndNumberSelection,
                new String[]{teamSetting, Long.toString(number)},
                null,
                null,
                sortOrder
        );
    }
    /*
        Students: Here is where you need to create the UriMatcher. This UriMatcher will
        match each URI to the WEATHER, WEATHER_WITH_LOCATION, WEATHER_WITH_LOCATION_AND_DATE,
        and LOCATION integer constants defined above.  You can test this by uncommenting the
        testUriMatcher test within TestUriMatcher.
     */
    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TeamContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, TeamContract.PATH_TEAM, TEAM);
        matcher.addURI(authority, TeamContract.PATH_TEAM + "/#", TEAM_ONE);
        matcher.addURI(authority, TeamContract.PATH_TEAM + "/*", TEAM_PLAYER);
        matcher.addURI(authority, TeamContract.PATH_GAME, GAME);

        matcher.addURI(authority, TeamContract.PATH_PLAYER + "/#" , PLAYER);
        matcher.addURI(authority, TeamContract.PATH_GAME + "/#", GAME_TEAM_PLAYER);

        return matcher;
    }

    /*
        Students: We've coded this for you.  We just create a new WeatherDbHelper for later use
        here.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new TeamDBHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.

     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case TEAM_ONE:
                return TeamContract.TeamEntry.CONTENT_ITEM_TYPE;
            case TEAM:
                return TeamContract.TeamEntry.CONTENT_TYPE;
            case TEAM_PLAYER:
                return TeamContract.TeamEntry.CONTENT_TYPE;
            case GAME:
                return TeamContract.GameEntry.CONTENT_TYPE;
            case GAME_TEAM_PLAYER:
                return TeamContract.GameEntry.CONTENT_ITEM_TYPE;
            case PLAYER:
                return TeamContract.PlayerEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "weather/*/*"
            case PLAYER:
            {
                retCursor = getPlayer(uri, projection, sortOrder);
                break;
            }
            // "weather/*"
            case TEAM_PLAYER: {
                retCursor = getTeamAndPlayers(uri, projection, sortOrder);
                break;
            }
            case TEAM_ONE:{
                retCursor = getOneTeam(uri, projection, sortOrder);
                break;
            }
            // "weather"
            case TEAM: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TeamContract.TeamEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case GAME:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TeamContract.GameEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TEAM: {
               // normalizeDate(values);
                long _id = db.insert(TeamContract.TeamEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = TeamContract.TeamEntry.buildTeamUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + values.toString() + uri);
                break;
            }
            case GAME:{
                long _id = db.insert(TeamContract.GameEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = TeamContract.GameEntry.buildGameUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case PLAYER:{
                long _id = db.insert(TeamContract.PlayerEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = TeamContract.PlayerEntry.buildPlayerUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case TEAM:
                rowsDeleted = db.delete(
                        TeamContract.TeamEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case GAME:
                rowsDeleted = db.delete(
                        TeamContract.GameEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PLAYER:
                rowsDeleted = db.delete(
                        TeamContract.PlayerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }
/*
    private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(WeatherContract.WeatherEntry.COLUMN_DATE)) {
            long dateValue = values.getAsLong(WeatherContract.WeatherEntry.COLUMN_DATE);
            values.put(WeatherContract.WeatherEntry.COLUMN_DATE, WeatherContract.normalizeDate(dateValue));
        }
    }*/

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case TEAM:
                //normalizeDate(values);
                rowsUpdated = db.update(TeamContract.TeamEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case GAME:
                rowsUpdated = db.update(TeamContract.GameEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case PLAYER:
                rowsUpdated = db.update(TeamContract.PlayerEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
/*
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TEAM:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        normalizeDate(value);
                        long _id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }*/

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
