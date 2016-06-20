package netdb.courses.softwarestudio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import netdb.courses.softwarestudio.fragment.GameDataFragment;

/**
 * Created by Bill on 2016/6/19.
 */
public class GameDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_data_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contain, new GameDataFragment())
                    .commit();
        }

    }
}
