package netdb.courses.softwarestudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;


import java.util.Arrays;

/**
 * Created by Bill on 2016/6/18.
 */
public class MainFragment extends Fragment {
    private TextView mtextView;
    private ProfilePictureView profilePictureView;
    CallbackManager callbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;
    Button btn1,btn2;
    private FacebookCallback<LoginResult> mcallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            mtextView.setText(constructWelcomeMessage(profile));
            constructProfileImage(profile);
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException error) {
        }
    };
    public MainFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager =CallbackManager.Factory.create();
        // Add this line in order for this fragment to handle menu events.
        setupTokenTracker();
        setupProfileTracker();
        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        btn1 = (Button) view.findViewById(R.id.team_button);
        btn2 = (Button) view.findViewById(R.id.solo_button);
        return  view;
    }

    public void btnlistener()
    {
        if(isLoggedIn() == true) {
            btn1.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TeamActivity.class);
                    startActivity(intent);
                }
            });
            btn2.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SoloActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            btn1.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),R.string.remind_login,Toast.LENGTH_SHORT).show();
                }
            });
            btn2.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),R.string.remind_login,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        setupTextDetails(view);
        setupLoginButton(view);
    }
    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        mtextView.setText(constructWelcomeMessage(profile));
        constructProfileImage(profile);
    }

    @Override
    public void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setupTextDetails(View view) {
        mtextView = (TextView) view.findViewById(R.id.text_details);
        profilePictureView = (ProfilePictureView) view.findViewById(R.id.profilePicture);
    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("VIVZ", "" + currentAccessToken);
            }
        };
    }
    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("VIVZ", "" + currentProfile);
                mtextView.setText(constructWelcomeMessage(currentProfile));
                constructProfileImage(currentProfile);
                onResume();
            }
        };
    }

    private void setupLoginButton(View view) {
        LoginButton mButtonLogin = (LoginButton) view.findViewById(R.id.login_button);
        mButtonLogin.setFragment(this);
        mButtonLogin.setReadPermissions(Arrays.asList("public_profile", "user_friends"));
        mButtonLogin.registerCallback(callbackManager, mcallback);
    }
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append("Welcome " + profile.getName());
        }

        return stringBuffer.toString();
    }
    private void constructProfileImage(Profile profile)
    {
        if(profile == null)
        {
            profilePictureView.setProfileId(null);;
            return ;
        }
        profilePictureView.setProfileId(profile.getId());
    }
}
