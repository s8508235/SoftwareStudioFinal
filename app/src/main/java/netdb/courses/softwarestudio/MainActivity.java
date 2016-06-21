package netdb.courses.softwarestudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//reference: http://huli.logdown.com/posts/260647-android-use-of-login-facebook-sdk-4
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;




import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

/*
    private RequestQueue mQueue;
    private final static String mUrl = "http://140.114.206.62/webapp/ServerSendingMsg.php";
    private TextView msg;
    private Button mGetMsgButton;
    private StringRequest getRequest;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //幫loginButton增加callback function

        //這邊為了方便 直接寫成inner class

/*
        msg = (TextView) findViewById(R.id.msg);
        mGetMsgButton = (Button) findViewById(R.id.get_msg);
        mQueue = Volley.newRequestQueue(this);
        getRequest = new StringRequest(Request.Method.POST,mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        msg.setText(s);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        msg.setText(volleyError.getMessage());
                    }
                });
        mGetMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(getRequest);
            }
        });*/
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
