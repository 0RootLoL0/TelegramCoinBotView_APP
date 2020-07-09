package io.github.rootlol.telegramcoinbot;

import org.json.simple.JSONObject;

import io.github.rootlol.jsonsimpleconverter.JsonSimpleConverterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class ApiCoingecko {
    public interface Api {
        @GET("/api/v3/coins/litecoin")
        Call<JSONObject> getCoinsLitecoin();
    }

    private static String urlBase = "https://api.coingecko.com/";
    private static Retrofit retrofit;
    private static Api api;

    public static Api getInstance(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JsonSimpleConverterFactory.create())
                    .baseUrl(urlBase)
                    .build();
        }
        api = retrofit.create(Api.class);
        return api;
    }
}
