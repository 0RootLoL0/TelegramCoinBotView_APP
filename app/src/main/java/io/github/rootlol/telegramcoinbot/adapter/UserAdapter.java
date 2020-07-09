package io.github.rootlol.telegramcoinbot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.rootlol.telegramcoinbot.R;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{
    private List<User> tweetList = new ArrayList<>();
    public void setItems(List<User> tweets) {
        tweetList.addAll(tweets);
        notifyDataSetChanged();
    }
    public void clearItems() {
        tweetList.clear();
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(tweetList.get(position));

    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }
}
