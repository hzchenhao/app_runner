package runner.pan.com.app_runner;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

/**
 * Created by bl02637 on 2014/7/14.
 */
public class MediaUtil {

    private MediaPlayer mediaPlayer;
    private Context mContext;
    private Vibrator vibrator;

    public MediaUtil(Context context) {
        mContext = context;
    }

    public void ring() {
        // 发出蜂鸣
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(mContext, R.raw.beeph);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alert() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(mContext, R.raw.alert);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ringOther() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(mContext, R.raw.other);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ringSuccess() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(mContext, R.raw.success);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void vibrate() {
        vibrate(500);
    }

    public void vibrate(long milliseconds) {
        if (vibrator == null) {
            vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        }
        vibrator.vibrate(milliseconds);

    }

    public void vibrate(long[] pattern, int repeat) {
        if (vibrator == null) {
            vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        }
        vibrator.vibrate(pattern, repeat);
    }
}
