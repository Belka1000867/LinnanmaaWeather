package fi.oulu.tol.linnanmaaweather;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.EActivity;

import java.util.Timer;
import java.util.TimerTask;

import fi.oulu.tol.linnanmaaweather.ui.LinnanmaaWeatherFragment;

@EActivity(R.layout.main)
public class LinnanmaaWeatherActivity extends FragmentActivity {

	private static final String TAG = "Debug_MainActivity";
	public static final long PERIOD = 60000;

	private Timer timer;

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			sendData();
		}
	};

	private LinnanmaaWeatherService mWeatherService;



    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

		if(savedInstanceState == null){
			Log.d(TAG, "savedInstanceState == null");

		}

		Intent intent = new Intent(this, LinnanmaaWeatherService.class);
		startService(intent);
    }

	@Override
	protected void onStart() {
		Log.d(TAG, "onStart()");
		super.onStart();
		Intent intent = new Intent(this, LinnanmaaWeatherService.class);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Log.d(TAG, "timerTask()");
				runOnUiThread(runnable);
			}
		}, 1000, PERIOD);
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop()");
		super.onStop();
		if(mWeatherService != null){
			unbindService(serviceConnection);
			mWeatherService = null;
		}
		if(timer != null)
			timer.cancel();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy()");
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			Log.d(TAG, "onServiceConnected()");
			mWeatherService = ((LinnanmaaWeatherService.LinnanmaaWeatherBinder) iBinder).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			Log.d(TAG, "onServiceDisconnected()");
			mWeatherService = null;
		}
	};

	public void onRefreshButtonClick(View view) {
		Log.d(TAG, "onRefreshButtonClick()");
		sendData();
    }

	public void sendData(){
		if(mWeatherService != null) {
			LinnanmaaWeatherFragment weatherFragment = (LinnanmaaWeatherFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_weather);
			weatherFragment.fetchData(mWeatherService);
		}
	}


}