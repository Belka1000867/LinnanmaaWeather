package fi.oulu.tol.linnanmaaweather;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import fi.oulu.tol.linnanmaaweather.module.GetDataInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Service
 */
public class LinnanmaaWeatherService extends Service {

    private final LinnanmaaWeatherBinder mWeatherBinder = new LinnanmaaWeatherBinder();

    private static final String WEATHER_URI_JSON = "http://weather.willab.fi/";

    private String tempNow, tempHi, tempLo,
            dewPoint, humidity, airPressure, windSpeed, windSpeedMax,
            windDir, precipitation1d, precipitation1h, timeStamp;

    private final static String TAG = "Debug_Service";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(WEATHER_URI_JSON)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() + flags : " + flags);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "TimerTask()");
                requestData();
            }
        }, 0, LinnanmaaWeatherActivity.PERIOD);

        //service will be run until specifically stopped
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return mWeatherBinder;
    }

    class LinnanmaaWeatherBinder extends Binder {

        LinnanmaaWeatherBinder(){
            //empty constructor
        }

        LinnanmaaWeatherService getService(){
            Log.d(TAG, "getService()");
            return LinnanmaaWeatherService.this;
        }
    }

    private void requestData(){
        GetDataInterface getDataInterface = retrofit.create(GetDataInterface.class);

        Call<WeatherData> call = getDataInterface.weatherData();
			/*
			* Make Retrofit to create call asynchronously and fill the data in text views
			* */
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherData weatherData = response.body();
                Log.d(TAG, "onResponse()");

                tempNow = weatherData.tempnow;
                tempHi = weatherData.temphi;
                tempLo = weatherData.templo;
                dewPoint = weatherData.dewpoint;
                humidity = weatherData.humidity;
                airPressure = weatherData.airpressure;
                windSpeed = weatherData.windspeed;
                windSpeedMax = weatherData.windspeedmax;
                windDir = weatherData.winddir;
                precipitation1d = weatherData.precipitation1d;
                precipitation1h = weatherData.precipitation1h;
                timeStamp = weatherData.timestamp;
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.d(TAG, "onFailure()" + t.getMessage());
            }
        });
    }

    public String getTempNow() {
        return tempNow;
    }

    public String getTempHi() {
        return tempHi;
    }

    public String getTempLo() {
        return tempLo;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getAirPressure() {
        return airPressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindSpeedMax() {
        return windSpeedMax;
    }

    public String getWindDir() {
        return windDir;
    }

    public String getPrecipitation1d() {
        return precipitation1d;
    }

    public String getPrecipitation1h() {
        return precipitation1h;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

}
