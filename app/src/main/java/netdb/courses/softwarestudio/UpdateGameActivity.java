package netdb.courses.softwarestudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import netdb.courses.softwarestudio.fragment.UpdateGameDetailFragment;

/**
 * Created by Bill on 2016/6/19.
 */
public class UpdateGameActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_team_detail_main);
        Intent intent = getIntent();

        String game = intent.getStringExtra("game");
        UpdateGameDetailFragment mFragment = new UpdateGameDetailFragment();

        Bundle tmp = new Bundle();
        tmp.putString("game", game);
        mFragment.setArguments(tmp);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contain, mFragment)
                    .commit();
        }
    }



}
