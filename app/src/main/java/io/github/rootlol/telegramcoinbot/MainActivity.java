package io.github.rootlol.telegramcoinbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.rootlol.telegramcoinbot.adapter.User;
import io.github.rootlol.telegramcoinbot.adapter.UserAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView balance;
    private TextView balanceRub;
    private RecyclerView RecyclerView;
    final String SAVED_TEXT = "settings";
    private EditText hdkjhd;
    private ImageButton buttonC;
    UserAdapter adapter = new UserAdapter();
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        balance = findViewById(R.id.textView3);
        balanceRub = findViewById(R.id.textView5);

        hdkjhd = findViewById(R.id.hdkjhd);
        sPref = getSharedPreferences("settings", MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "http://192.168.1.29/");
        hdkjhd.setText(savedText);

        RecyclerView = findViewById(R.id.recycler);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter);
        ApiBot.getInstance(savedText).getBalance().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject result = response.body();
                Double dbalance = (Double) result.get("all");
                String ebalance = Double.toString(dbalance);
                balance.setText(ebalance);

                ApiCoingecko.getInstance().getCoinsLitecoin().enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        balanceRub.setText(Double.toString(dbalance* ((Double) ((JSONObject) ((JSONObject) response.body().get("market_data")).get("current_price")).get("rub")) ));
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        balanceRub.setText("no net");
                    }
                });

                JSONArray users = (JSONArray) result.get("users");
                List<User> temp = new ArrayList<>();
                for (int i = 0; i < users.size(); i++) {
                    JSONObject user = (JSONObject) users.get(i);
                    temp.add(new User(
                            (String) user.get("user"),
                            (Double) user.get("balance")
                    ));
                }
                adapter.clearItems();
                adapter.setItems(temp);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                balance.setText("no net");
                balanceRub.setText("no net");
                adapter.clearItems();
            }
        });
        buttonC = findViewById(R.id.imageButton);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPref = getSharedPreferences("settings", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SAVED_TEXT, hdkjhd.getText().toString());
                ed.commit();

                ApiBot.getInstance(hdkjhd.getText().toString()).getBalance().enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        JSONObject result = response.body();
                        Double dbalance = (Double) result.get("all");
                        String ebalance = Double.toString(dbalance);
                        balance.setText(ebalance);

                        ApiCoingecko.getInstance().getCoinsLitecoin().enqueue(new Callback<JSONObject>() {
                            @Override
                            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                                balanceRub.setText(Double.toString(dbalance* ((Double) ((JSONObject) ((JSONObject) response.body().get("market_data")).get("current_price")).get("rub")) ));
                            }

                            @Override
                            public void onFailure(Call<JSONObject> call, Throwable t) {
                                balanceRub.setText("no net");
                            }
                        });


                        JSONArray users = (JSONArray) result.get("users");
                        List<User> temp = new ArrayList<>();
                        for (int i = 0; i < users.size(); i++) {
                            JSONObject user = (JSONObject) users.get(i);
                            temp.add(new User(
                                    (String) user.get("user"),
                                    (Double) user.get("balance")
                            ));
                        }
                        adapter.clearItems();
                        adapter.setItems(temp);
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        balance.setText("no net");
                        balanceRub.setText("no net");
                        adapter.clearItems();
                    }
                });
            }
        });

    }
}