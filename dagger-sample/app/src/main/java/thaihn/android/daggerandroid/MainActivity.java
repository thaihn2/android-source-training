package thaihn.android.daggerandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import thaihn.android.daggerandroid.data.DataManager;
import thaihn.android.daggerandroid.data.model.User;
import thaihn.android.daggerandroid.di.component.ActivityComponent;
import thaihn.android.daggerandroid.di.component.DaggerActivityComponent;

public class MainActivity extends AppCompatActivity {

    @Inject
    DataManager mDataManager;

    private ActivityComponent mActivityComponent;


    private TextView mTvUserInfo;
    private TextView mTvAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        mTvUserInfo = (TextView) findViewById(R.id.tv_user_info);
        mTvAccessToken = (TextView) findViewById(R.id.tv_access_token);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        createUser();
        getUser();
        mDataManager.saveAccessToken("ASDR12443JFDJF43543J543H3K543");

        String token = mDataManager.getAccessToken();
        if (token != null) {
            mTvAccessToken.setText(token);
        }
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent
                    .builder()
                    .applicationComponent(MyApplication.get(this).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    private void createUser() {
        try {
            mDataManager.createUser(new User("Ali", "1367, Gurgaon, Haryana, India"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUser() {
        try {
            User user = mDataManager.getUser(1L);
            mTvUserInfo.setText(user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
