package netdb.courses.softwarestudio.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the weather database.
 */
public class TeamContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "netdb.courses.softwarestudio";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_TEAM = "team";
    public static final String PATH_PLAYER = "player";
    public static final String PATH_GAME = "game";
    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.

    public static final class  GameEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GAME).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GAME;

        public static final String TABLE_NAME = "game";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_TEAMA = "teamA";
        public static final String COLUMN_TEAMB = "teamB";

        public static final String COLUMN_TEAMA_SCORE = "scoreA";
        public static final String COLUMN_TEAMB_SCORE = "scoreB";

        public static final String COLUMN_WINNER = "winner";

        public static Uri buildGameUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the location table */
    public static final class TeamEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEAM).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAM;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TEAM;

        // Table name
        public static final String TABLE_NAME = "team";

        // Human readable location string, provided by the API.  Because for styling,
        // "Mountain View" is more recognizable than 94043.
        public static final String COLUMN_TEAM_NAME = "team_name";

        public static final String COLUMN_TEAM_NUM = "team_number";


        public static Uri buildTeamUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getTeamSettingFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class PlayerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLAYER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER;

        public static final String TABLE_NAME = "player";

        public static final String COLUMN_PLAYER_NAME = "name";
        public static final String COLUMN_PLAYER_NUMBER = "player_number";
        public static final String COLUMN_PLAYER_ID = "team_id";
        public static final String COLUMN_TEAM = "team";

        public static final String COLUMN_GAME = "game";

        public static final String COLUMN_GAME_NUM = "total_game";
        // Column with the foreign key into the location table.
        public static final String COLUMN_GAME_TIME = "time";
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_STEAL = "steal";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_BLOCK = "block";

        public static final String COLUMN_AST = "assist";

        public static final String COLUMN_TOTAL_SHOT = "total_shot";

        public static final String COLUMN_SHOT = "shot";

        public static final String COLUMN_TOTAL_THREE = "total_three";

        public static final String COLUMN_THREE = "three";

        public static final String COLUMN_OFF = "off_rebound";
        public static final String COLUMN_DEF = "def_rebound";

        public static final String COLUMN_TURNOVER = "turnover";

        public static Uri buildPlayerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getTeamSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getNumberFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

    }
}
