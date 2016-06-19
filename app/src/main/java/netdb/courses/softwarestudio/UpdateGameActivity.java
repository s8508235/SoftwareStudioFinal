package netdb.courses.softwarestudio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Bill on 2016/6/19.
 */
public class UpdateGameActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_team_detail_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contain, new UpdateGameDetailFragment())
                    .commit();
        }
    }
}
