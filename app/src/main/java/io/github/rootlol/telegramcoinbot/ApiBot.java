package io.github.rootlol.telegramcoinbot;

import org.json.simple.JSONObject;

import io.github.rootlol.jsonsimpleconverter.JsonSimpleConverterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class ApiBot {
    public interface Api {
        @GET("/balance")
        Call<JSONObject> getBalance();
    }

    public static Api getInstance(String urlBase){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JsonSimpleConverterFactory.create())
                .baseUrl(urlBase)
                .build();
        Api api = retrofit.create(Api.class);
        return api;
    }
}
