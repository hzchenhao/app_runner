package runner.pan.com.app_runner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

public class MainActivity extends Activity implements View.OnClickListener {
    static final String TAG = "MainActivity";
    Context mContext = this;

    TextView mTvStart;
    TextView mTvEnd;
    EditText mEtRunner;
    EditText mEtWalker;
    EditText mEtCycle;
    Button mBtnStart;
    Button mBtnEnd;
    Chronometer mTimer;

    long Runner_Minuter;
    long Walking_Minuter;
    int count;
    int record = 0;

    long mStartTime;
    boolean mIsRunning = true;
    MediaUtil mediaUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.runner_btnStart:
                start();
                break;
            case R.id.runner_btnEnd:
                end();
                break;
        }

    }

    private void start() {
        if (TextUtils.isEmpty(mEtRunner.getText())) {
            Toast.makeText(mContext, "请输入跑步时间！", Toast.LENGTH_LONG).show();
            return;
        }
        Runner_Minuter = Long.valueOf(mEtRunner.getText().toString()) * 60 * 1000;

        if (TextUtils.isEmpty(mEtWalker.getText())) {
            Toast.makeText(mContext, "请输入走路时间！", Toast.LENGTH_LONG).show();
            return;
        }
        Walking_Minuter = Long.valueOf(mEtWalker.getText().toString()) * 60 * 1000;

        if (TextUtils.isEmpty(mEtCycle.getText())) {
            Toast.makeText(mContext, "请输入循环次数！", Toast.LENGTH_LONG).show();
            return;
        }
        count = Integer.valueOf(mEtCycle.getText().toString());

        mTimer.setBase(SystemClock.elapsedRealtime());
        mTimer.start();

        mStartTime = mTimer.getBase();

        mTvStart.setText(String.format("开始时间：%s", DateTime.now()));
    }

    private void end() {
        mTimer.stop();
        mTvEnd.setText(String.format("结束时间：%s", DateTime.now()));
    }

    private void initView() {
        mTvStart  = (TextView) findViewById(R.id.runner_tvStart);
        mTvEnd    = (TextView) findViewById(R.id.runner_tvEnd);
        mEtRunner = (EditText) findViewById(R.id.runner_etRunner);
        mEtWalker = (EditText) findViewById(R.id.runner_etWalk);
        mEtCycle  = (EditText) findViewById(R.id.runner_etCycle);
        mBtnStart = (Button)   findViewById(R.id.runner_btnStart);
        mBtnEnd   = (Button)   findViewById(R.id.runner_btnEnd);
        mTimer    = (Chronometer) findViewById(R.id.runner_timer);

        mBtnStart.setOnClickListener(this);
        mBtnEnd.setOnClickListener(this);

    }

    private void initData() {
        mediaUtil = new MediaUtil(mContext);

        mTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (record == count) {
                    mTvEnd.setText("时间到");
                    return;
                }
                Log.d(TAG, "enter onChronometerTick");
                if (mIsRunning &&
                    SystemClock.elapsedRealtime() - mStartTime >= Runner_Minuter) {
                    mStartTime = SystemClock.elapsedRealtime();
                    mediaUtil.alert();
                    mediaUtil.vibrate();

                    Log.d(TAG, "change to walk");
                    return;
                }

                if (!mIsRunning &&
                    SystemClock.elapsedRealtime() - mStartTime >= Walking_Minuter) {
                    mStartTime = SystemClock.elapsedRealtime();
                    mediaUtil.alert();
                    mediaUtil.vibrate();
                    record++;

                    Log.d(TAG, "change to run");
                    return;
                }

            }
        });
    }

}
