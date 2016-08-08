package fi.oulu.tol.linnanmaaweather.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import fi.oulu.tol.linnanmaaweather.LinnanmaaWeatherService;
import fi.oulu.tol.linnanmaaweather.R;

@EFragment(R.layout.fragment_main)
public class LinnanmaaWeatherFragment extends Fragment {

    private static final String TAG = "Debug_UI_Fragment";

    @ViewById
    TextView tv_tempnow, tv_temphi, tv_templow,
            tv_dewpoint, tv_humidity, tv_airpressure, tv_windspeed, tv_windspeedmax,
            tv_winddir, tv_precipitation1d, tv_precipitation1h, tv_timestamp;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()" + "fragment creates its portion of the view hierarchy, which is added to its activity’s view hierarchy");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void fetchData(LinnanmaaWeatherService mWeatherService){
        Log.d(TAG, "fetchData()");
            tv_tempnow.setText(mWeatherService.getTempNow() + "\u2103");
            tv_temphi.setText("24 hour high: " + mWeatherService.getTempHi() + "\u2103");
            tv_templow.setText("low: "  + mWeatherService.getTempLo() + "\u2103");
            tv_dewpoint.setText("Dew point: " + mWeatherService.getDewPoint() + "\u2103");
            tv_humidity.setText("Humidity: " + mWeatherService.getHumidity() + "%");
            tv_airpressure.setText("Air pressure: " + mWeatherService.getAirPressure());
            tv_windspeed.setText("Wind speed: " + mWeatherService.getWindSpeed() + "m/s");
            tv_windspeedmax.setText("Wind max speed: "+mWeatherService.getWindSpeedMax()+ "m/s");
            tv_winddir.setText("Wind direction: " + mWeatherService.getWindDir());
            tv_precipitation1d.setText("past hour: "+mWeatherService.getPrecipitation1d() + " mm");
            tv_precipitation1h.setText("past 24 hours: " + mWeatherService.getPrecipitation1h() + " mm");
            tv_timestamp.setText(mWeatherService.getTimeStamp());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated()" + "fragment’s activity has finished its own onCreate event");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()" + "fragment is visible and cannot start until its activity starts");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()" + "fragment is visible and interactable and cannot resume until its activity resumes");


    }
}
