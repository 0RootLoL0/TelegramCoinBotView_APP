package io.github.rootlol.telegramcoinbot;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.rootlol.telegramcoinbot.adapter.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class TelegramBotCoin extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.telegram_bot_coin);

        SharedPreferences sPref = context.getSharedPreferences("settings", MODE_PRIVATE);
        String savedText = sPref.getString("settings", "http://192.168.1.29/");

        ApiBot.getInstance(savedText).getBalance().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject result = response.body();
                Double dbalance = (Double) result.get("all");
                String ebalance = Double.toString(dbalance);
                views.setTextViewText(R.id.textView6, ebalance);

                ApiCoingecko.getInstance().getCoinsLitecoin().enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        views.setTextViewText(R.id.textView9, Double.toString(dbalance* ((Double) ((JSONObject) ((JSONObject) response.body().get("market_data")).get("current_price")).get("rub")) ));
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        views.setTextViewText(R.id.textView9, "no net");
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                });
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                views.setTextViewText(R.id.textView6, "no net");
                views.setTextViewText(R.id.textView9, "no net");
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        });

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

