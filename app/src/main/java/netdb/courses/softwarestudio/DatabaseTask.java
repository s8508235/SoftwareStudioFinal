package netdb.courses.softwarestudio;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by SeanKang on 2016/6/22.
 */
public class DatabaseTask extends AsyncTask<String, Void, Void> {


    private final Context mContext;

    public DatabaseTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        return null;
    }
}
