package fi.oulu.tol.linnanmaaweather.module;

import java.util.List;

import fi.oulu.tol.linnanmaaweather.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bel on 26.04.16.
 */
public interface GetDataInterface {

    @GET("weather.json")
    Call<WeatherData> weatherData();
}
